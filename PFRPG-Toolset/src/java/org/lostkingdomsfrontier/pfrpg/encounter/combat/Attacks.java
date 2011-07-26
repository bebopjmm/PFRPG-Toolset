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
package org.lostkingdomsfrontier.pfrpg.encounter.combat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lostkingdomsfrontier.pfrpg.AdjustableValue;
import org.lostkingdomsfrontier.pfrpg.Adjustment;
import org.lostkingdomsfrontier.pfrpg.entity.AbilityListenerValue;
import org.lostkingdomsfrontier.pfrpg.entity.AbilityValue;
import org.lostkingdomsfrontier.pfrpg.entity.Size;

/**
 * This class manages attack modifiers.
 * 
 * @author bebopjmm
 * 
 */
public class Attacks
{
   static final Log LOG = LogFactory.getLog(Attacks.class);

   int maxBAB;

   int nAttacks;

   int currentAttack;

   Adjustment sizeAdj;

   Adjustment grappleSize;

   AbilityListenerValue melee;

   AbilityListenerValue ranged;

   AbilityListenerValue grapple;


   /**
    * @param size
    * @param meleeAbility
    * @param rangedAbility
    * @param grappleAbility
    */
   public Attacks(Size size, AbilityValue meleeAbility, AbilityValue rangedAbility,
         AbilityValue grappleAbility)
   {
      maxBAB = 0;
      nAttacks = 0;
//      sizeAdj = Size.generateCombatAdjustment(size);
      melee = new AbilityListenerValue("melee", 0, meleeAbility);
      melee.addAdjustment(sizeAdj);
      ranged = new AbilityListenerValue("ranged", 0, rangedAbility);
      ranged.addAdjustment(sizeAdj);
      grapple = new AbilityListenerValue("grapple", 0, grappleAbility);
//      grappleSize = Size.generateGrappleAdjustment(size);
      grapple.addAdjustment(grappleSize);
   }


   /**
    * @return
    */
   public int getMaxBaseAttackBonus()
   {
      return this.maxBAB;
   }


   /**
    * @param maxBaseAttackBonus
    */
   public void setBaseAttackBonus(int maxBaseAttackBonus)
   {
      LOG.info("Setting maxBAB to: " + maxBaseAttackBonus);
      this.maxBAB = maxBaseAttackBonus;
      melee.setBase(maxBAB);
      ranged.setBase(maxBAB);
      grapple.setBase(maxBAB);
      nAttacks = 1 + (maxBAB / 5);
      LOG.debug("Number of attacks =" + nAttacks);
   }

   
   public void modifyBaseAttackBonus(int modification)
   {
      int newBAB = this.maxBAB + modification;
      LOG.debug("Modifying maxBAB by: " + modification);
      setBaseAttackBonus(newBAB);
   }

   /**
    * @param newSize
    */
   public void setSize(Size newSize)
   {
      sizeAdj.setValue(newSize.getCombatModifier());
      grappleSize.setValue(newSize.getGrappleModifier());
   }


   /**
    * @return
    */
   public AdjustableValue getMeleeModifier()
   {
      return melee;
   }


   /**
    * @return
    */
   public AdjustableValue getRangedModifer()
   {
      return ranged;
   }


   /**
    * @return
    */
   public AdjustableValue getGrapple()
   {
      return grapple;
   }


   /**
     * 
     */
   public void consume()
   {
      int newBAB = melee.getBase() - 5;
      melee.setBase(newBAB);
      ranged.setBase(newBAB);
      grapple.setBase(newBAB);
   }


   /**
     * 
     */
   public void reset()
   {
      melee.setBase(maxBAB);
      ranged.setBase(maxBAB);
      grapple.setBase(maxBAB);
   }
}
