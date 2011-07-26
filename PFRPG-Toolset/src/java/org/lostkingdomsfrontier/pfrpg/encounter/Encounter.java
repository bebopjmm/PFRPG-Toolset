/**
 * Project: PFRPG-Toolset
 * Created: Aug 13, 2006 by bebopJMM
 *------------------------------------------------------------------------------
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *   http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lostkingdomsfrontier.pfrpg.encounter;

import java.util.TreeSet;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lostkingdomsfrontier.pfrpg.entity.Actor;

/**
 * The Encounter class manages combatants participating in an encounter. Not even remotely
 * thread-safe at the moment.
 * 

 * 
 * @author bebopjmm
 */
@XmlRootElement(name = "Encounter", namespace = "java:org.rollinitiative.d20.encounter")
@XmlType(name = "EncounterType", namespace = "java:org.rollinitiative.d20.encounter")
public class Encounter
{
   static final Log LOG = LogFactory.getLog(Encounter.class);

   /**
    * Optional identifying name for this encounter.
    */
   String name = null;


   /**
    * The roster of combatants participating in this encounter
    */
   Roster roster;

   /**
    * Initiative order (highest to lowest) of the combatants currently aware of encounter.
    */
   TreeSet<Combatant> initiativeOrder = new TreeSet<Combatant>();


   public Encounter()
   {
      this.name = "NOT SET";
   }


   /**
    * @param name
    */
   public Encounter(String name)
   {
      this.name = name;
      this.roster = new Roster();
   }


   /**
    * @return the name
    */
   public String getName()
   {
      return name;
   }


   /**
    * @param name the name to set for this encounter.
    */
   @XmlAttribute(required = true)
   public void setName(String name)
   {
      this.name = name;
   }


   /**
    * Adds the specified actor to the encounter under the designated allegiance
    * 
    * @param actor
    * @param allegiance
    * @since incr-0.1
    */
   public Combatant joinEncounter(Actor actor, Faction allegiance, boolean isAutonomous)
   {
      LOG.info("Adding Actor to encounter, name = " + actor.getName());
      Combatant newCombatant = null;
      // Verify the Actor is not already part of the encounter
      if (!roster.isParticipating(actor)) {
         newCombatant = new Combatant(actor, allegiance, isAutonomous);
         roster.add(newCombatant);
         // combatants_.put(actor, new Combatant(actor, allegiance));
         LOG.debug("Total combatants = " + roster.getNumberCombatants());
      }
      else {
         LOG.warn("Actor already part of encounter. Combatant NOT added");
      }
      return newCombatant;
   }


   /**
    * Removes the specified actor from the encounter.
    * 
    * @param actor
    * @since incr-0.1
    */
   public void leaveEncounter(Actor actor)
   {
      LOG.info("Removing Actor from encounter, name = " + actor.getName());
      Combatant combatant = roster.getCombatant(actor);
      roster.remove(combatant);
   }


   /**
    * Generates initiative roll and places the combatant with actorID into initiative order.
    * 
    * @param actorID
    * 
    * @since incr-0.2
    */
   public void makeCombatantAware(UUID actorID)
   {
      Combatant combatant = roster.getCombatant(actorID);
      combatant.makeAware();
      initiativeOrder.add(combatant);
   }


   public void makeCombatantAware(UUID actorID, int initiativeBaseRoll)
   {
      Combatant combatant = roster.getCombatant(actorID);
      combatant.makeAware(initiativeBaseRoll);
      initiativeOrder.add(combatant);
   }


   public Combatant getCombatant(Actor actor)
   {
      return roster.getCombatant(actor);
   }


   public Combatant getCombatant(UUID actorID)
   {
      return roster.getCombatant(actorID);
   }


   /**
    * @return
    */
   public Combatant[] getInitiativeOrder()
   {
      LOG.debug("Total combatants in roster vs tree = " + roster.getCombatants().size() + "/"
            + initiativeOrder.size());
      return initiativeOrder.toArray(new Combatant[initiativeOrder.size()]);
   }

}
