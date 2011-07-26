/**
 * Project: PFRGP-Toolset
 * Created: Aug 20, 2006 by bebopJMM
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

import org.lostkingdomsfrontier.pfrpg.entity.AbilityListenerValue;

/**
 * This class represents an initiative value for a single combatant over the course of an encounter.
 * 
 * TODO Add support for impact of 1/20 base roll. TODO Add support for adjustment during encounter
 * (delay, ready, etc.)
 * 
 * @author bebopJMM
 * 
 */
public class Initiative
{
   /**
    * Initiative value, initial value is derived from baseRoll + modifier
    */
   private int value = 0;

   /**
    * Base number of initiative (1d20)
    */
   private int baseRoll = 0;

   /**
    * Modifier to be applied to baseRoll
    */
   private AbilityListenerValue abilityMod;

   private int fixedMod = 0;

   /**
    * Tie-breaking roll when baseRoll and modifier match another (1d100)
    */
   private int tieRoll = 0;


   public Initiative(AbilityListenerValue actorModifier)
   {
      this.abilityMod = actorModifier;
   }


   /**
    * @return the baseRoll input into the initiative value.
    */
   public int getBaseRoll()
   {
      return this.baseRoll;
   }


   /**
    * Sets the baseRoll value (typically 1..20)
    * 
    * @param baseRoll the baseRoll value.
    */
   public void setBaseRoll(int baseRoll)
   {
      this.baseRoll = baseRoll;
      this.value = baseRoll + getModifier();
      this.tieRoll = 0; // reset
   }


   /**
    * @return the current initiative value.
    */
   public int getValue()
   {
      return value;
   }


   /**
    * @return the modification to the baseRoll due to actor's abilities and talents.
    */
   public int getModifier()
   {
      return abilityMod.getCurrent() + fixedMod;
   }


   /**
    * @return the fixedMod
    */
   public int getFixedMod()
   {
      return fixedMod;
   }


   /**
    * @param fixedMod the fixedMod to set
    */
   public void setFixedMod(int fixedMod)
   {
      this.fixedMod = fixedMod;
   }


   /**
    * @return the tieRoll
    */
   public int getTieRoll()
   {
      return tieRoll;
   }


   /**
    * @param tieRoll new tieRoll value.
    */
   public void setTieRoll(int tieRoll)
   {
      this.tieRoll = tieRoll;
   }

}
