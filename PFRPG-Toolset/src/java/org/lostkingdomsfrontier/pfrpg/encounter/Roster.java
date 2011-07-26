/**
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lostkingdomsfrontier.pfrpg.entity.Actor;

/**
 * The Roster class maintains the collection of combatants participating in and encounter and how
 * they are organized into groups.
 * 
 * @author bebopjmm
 * 
 */
public class Roster
{
   static final Log LOG = LogFactory.getLog(Roster.class);

   /**
    * This table map provides quick lookup of combatants by their UUID.
    */
   Hashtable<UUID, Combatant> combatants = new Hashtable<UUID, Combatant>();

   /**
    * This table organizes combatants by the Faction that they belong to with respect to the
    * encounter
    */
   Hashtable<Faction, ArrayList<Combatant>> allegianceMap = new Hashtable<Faction, ArrayList<Combatant>>();


   public Roster()
   {
      allegianceMap.put(Faction.PARTY, new ArrayList<Combatant>());
      allegianceMap.put(Faction.NEUTRAL, new ArrayList<Combatant>());
      allegianceMap.put(Faction.HOSTILE, new ArrayList<Combatant>());
      allegianceMap.put(Faction.ALLY, new ArrayList<Combatant>());
   }


   /**
    * This method adds the supplied combatant to the Roster.
    * 
    * @param combatant new combatant to be added to the roster
    */
   public synchronized void add(Combatant combatant)
   {
      combatants.put(combatant.getActor().getActorID(), combatant);
      ArrayList<Combatant> allies = allegianceMap.get(combatant.getAllegiance());
      if (allies == null) {
         LOG.warn("Combatant list NULL for allegiance: " + combatant.getAllegiance()
               + ", initializing to empty list.");
         allies = new ArrayList<Combatant>();
         allegianceMap.put(combatant.getAllegiance(), allies);
      }
      allies.add(combatant);
   }


   public synchronized void remove(Combatant combatant)
   {
      combatants.remove(combatant.getActor().getActorID());
      ArrayList<Combatant> allies = allegianceMap.get(combatant.getAllegiance());
      if (allies == null) {
         LOG.error("Combatant list NULL for allegiance: " + combatant.getAllegiance());
         return;
      }
      allies.remove(combatant);
   }


   public boolean isParticipating(Actor actor)
   {
      return combatants.containsKey(actor.getActorID());
   }


   public boolean isParticipating(UUID actorID)
   {
      return combatants.containsKey(actorID);
   }


   public int getNumberCombatants()
   {
      return combatants.size();
   }


   public Collection<Combatant> getCombatants()
   {
      return combatants.values();
   }


   public Combatant getCombatant(Actor actor)
   {
      return combatants.get(actor.getActorID());
   }


   public Combatant getCombatant(UUID actorID)
   {
      return combatants.get(actorID);
   }


   public Collection<Combatant> getFactionCombatants(Faction faction)
   {
      return allegianceMap.get(faction);
   }
   
}
