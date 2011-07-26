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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lostkingdomsfrontier.pfrpg.dice.Dice;
import org.lostkingdomsfrontier.pfrpg.entity.Ability;
import org.lostkingdomsfrontier.pfrpg.entity.Actor;

/**
 * Combatant is an association class, linking an actor with an allegiance for purposes of an
 * encounter.
 * 
 * @author bebopJMM
 * 
 */
public class Combatant implements Comparable<Combatant>
{
   static final Log LOG = LogFactory.getLog(Combatant.class);

   private Actor actor;

   private Faction allegiance;

   private Initiative initiative;

   private boolean isAware = false;

   private boolean isAutonomous = true;

   private ActionSet actionSet = null;

   private Encounter encounter;

   static Dice dice = Dice.getInstance();


   /**
    * @param allegiance
    * @param actor
    */
   public Combatant(Actor actor, Faction allegiance)
   {
      this.allegiance = allegiance;
      this.actor = actor;
//      this.initiative = new Initiative(actor.getInitiativeMod());
   }


   /**
    * @param actor
    * @param allegiance
    * @param autonomous
    */
   public Combatant(Actor actor, Faction allegiance, boolean autonomous)
   {
      this(actor, allegiance);
      this.isAutonomous = autonomous;
   }


   /**
    * @return the actor
    */
   public Actor getActor()
   {
      return actor;
   }


   /**
    * @param actor the actor_ to set
    */
   public void setActor(Actor actor)
   {
      this.actor = actor;
   }


   /**
    * @return the allegiance
    */
   public Faction getAllegiance()
   {
      return allegiance;
   }


   /**
    * @param allegiance the allegiance to set
    */
   public void setAllegiance(Faction allegiance)
   {
      this.allegiance = allegiance;
   }


   /**
    * @return the combatant's current initiative
    * @since incr-0.1
    */
   public Initiative getInitiative()
   {
      return initiative;
   }


   /**
    * @return true if the combatant has awareness of the encounter, otherwise false.
    * @since incr-0.1
    */
   public boolean isAware()
   {
      return isAware;
   }


   public void setEncounter(Encounter encounter)
   {
      this.encounter = encounter;
   }


   /**
    * Auto-roll an initiative.
    * 
    * @return
    * @since incr-0.1
    */
   public Initiative rollInitiative()
   {
      initiative.setBaseRoll(dice.roll(1, 20, 0));
      return this.initiative;
   }


   public Initiative setInitiative(int baseRoll)
   {
      initiative.setBaseRoll(baseRoll);
      return this.initiative;
   }


   /**
    * TODO: Add support for hand-rolled combatants
    * 
    * @return
    * @since incr-0.1
    */
   public Initiative rollInitiativeTiebreak()
   {
      initiative.setTieRoll(dice.roll(1, 1000, 0));
      return this.initiative;
   }


   public void setActionSet(ActionSet set)
   {
      actionSet = set;
   }


   public ActionSet getActionSet()
   {
      // return new ActionSet();
      return actionSet;
   }


   /**
    * 
    * @see http://www.d20srd.org/srd/combat/combatStatistics.htm TODO ensure attack is 'consumed'
    */
   public int meleeAttack(int attackNum)
   {
      int baseRoll = dice.roll(1, 20, 0);
//      int bab = actor.getAttacks().getMeleeModifier().getCurrent();
//      int mods = bab + +Ability.getModifier(actor.getAbility(Ability.STR).getCurrent())
//            + actor.getCurrentSize().getCombatModifier();
//      LOG.info("Attack roll = " + baseRoll + " + " + mods + " = " + (baseRoll + mods));
//      return baseRoll + mods;
      return baseRoll;
   }


   public int meleeAttack()
   {
      int baseRoll = dice.roll(1, 20, 0);
//      int mods = actor.getAttacks().getMeleeModifier().getCurrent();
//      LOG.debug("Attack roll (base + mods) = " + (baseRoll + mods) + "(" + baseRoll + " + " + mods
//            + " )");
//      return baseRoll + mods;
      return baseRoll;
   }


   public int rollDefense(boolean touchAttack)
   {
//      return actor.getDefense().defend(touchAttack);
       return 10;
   }


   /**
    * The initiative value is used to evaluate the comparison. Order runs highest to lowest. Basis
    * of comparison is Initiative VALUE, Initiative MODIFIER, Initiative TIE ROLL.
    * 
    * @see java.lang.Comparable#compareTo(java.lang.Object)
    */
   public int compareTo(Combatant o)
   {
      if (o.initiative.getValue() == this.initiative.getValue()) {
         if (o.initiative.getModifier() == this.initiative.getModifier()) {
            if (o.initiative.getTieRoll() == this.initiative.getTieRoll()) {
               return 0;
            }
            else {
               return o.initiative.getTieRoll() - this.initiative.getTieRoll();
            }
         }
         else {
            return o.initiative.getModifier() - this.initiative.getModifier();
         }
      }
      else {
         return o.initiative.getValue() - this.initiative.getValue();
      }
   }


   /**
    * (non-Javadoc)
    * 
    * @see java.lang.Object#equals(java.lang.Object)
    */
   @Override
   public boolean equals(Object arg0)
   {
      if (arg0 instanceof Combatant) {
         Combatant c = (Combatant) arg0;
         return this.actor.equals(c.actor);
      }
      else
         return false;
   }


   protected void makeAware()
   {
      this.isAware = true;
      rollInitiative();
      rollInitiativeTiebreak();
   }


   protected void makeAware(int initiativeBaseRoll)
   {
      this.isAware = true;
      this.initiative.setBaseRoll(initiativeBaseRoll);
      rollInitiativeTiebreak();
   }

}
