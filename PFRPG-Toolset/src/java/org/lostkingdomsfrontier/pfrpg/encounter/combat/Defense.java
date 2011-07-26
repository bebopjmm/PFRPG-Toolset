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

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lostkingdomsfrontier.pfrpg.AdjustableValue;
import org.lostkingdomsfrontier.pfrpg.Adjustment;
import org.lostkingdomsfrontier.pfrpg.dice.Dice;
import org.lostkingdomsfrontier.pfrpg.entity.Ability;
import org.lostkingdomsfrontier.pfrpg.entity.AbilityListenerValue;
import org.lostkingdomsfrontier.pfrpg.entity.AbilityValue;
import org.lostkingdomsfrontier.pfrpg.entity.Size;

/**
 * @author bebopjmm
 * 
 */
public class Defense
{
   static final Log LOG = LogFactory.getLog(Defense.class);

   public enum AC_Type {
      AC_Reg, AC_Touch;
   }

   EnumMap<AC_Type, AbilityListenerValue> armorClass;

   ArrayList<Adjustment> naturalArmor = new ArrayList<Adjustment>();

   Adjustment bestNaturalArmor;

   Adjustment sizeAdj;

   boolean rollDefense = true;

   boolean isFlatFooted = true;

   Dice dice = Dice.getInstance();


   public Defense(Size size, AbilityValue dexterityAbility)
   {
//      sizeAdj = Size.generateCombatAdjustment(size);
      armorClass = new EnumMap<AC_Type, AbilityListenerValue>(AC_Type.class);

      AbilityListenerValue acEntry = null;
      for (AC_Type ac : EnumSet.range(AC_Type.AC_Reg, AC_Type.AC_Touch)) {
         acEntry = new AbilityListenerValue(ac.toString(), 0, dexterityAbility);
         acEntry.addAdjustment(sizeAdj);
         armorClass.put(ac, acEntry);
      }

   }


   public void setBaseDefenseBonus(int baseDefenseBonus)
   {
      LOG.info("Setting baseDefenseBonus to: " + baseDefenseBonus);
      AbilityListenerValue acEntry = null;
      for (AC_Type ac : armorClass.keySet()) {
         acEntry = armorClass.get(ac);
         acEntry.setBase(baseDefenseBonus);
      }
   }


   /**
    * @param newSize
    */
   public void setSize(Size newSize)
   {
      sizeAdj.setValue(newSize.getCombatModifier());
   }


   public boolean isFlatFooted()
   {
      return this.isFlatFooted;
   }


   public void setFlatFooted(boolean isFlatFooted)
   {
      this.isFlatFooted = isFlatFooted;
      AbilityListenerValue acEntry = null;
      if (isFlatFooted) {
         LOG.info("Limiting DEX bonus for AC to 0");
         for (AC_Type ac : EnumSet.range(AC_Type.AC_Reg, AC_Type.AC_Touch)) {
            acEntry = armorClass.get(ac);
            acEntry.setLimiter(Ability.DEX, (short)0);
         }
      }
      else {
         LOG.info("Removing DEX bonus limits for AC");
         for (AC_Type ac : EnumSet.range(AC_Type.AC_Reg, AC_Type.AC_Touch)) {
            acEntry = armorClass.get(ac);
            acEntry.removeLimiter(Ability.DEX);
         }
      }
   }


   public int defend(boolean touchAttack)
   {
      // NO Dex bonus if flatFooted
      // NO AC bonus if touch
      int defense = 0;
      if (rollDefense) {
         int baseRoll = dice.roll(1, 20, 0);
         defense = baseRoll;
      }
      defense += armorClass.get(AC_Type.AC_Reg).getCurrent();
      return defense;
   }


   public int defend(int playerRoll, boolean touchAttack)
   {
      return playerRoll + armorClass.get(AC_Type.AC_Reg).getCurrent();
   }


   public AdjustableValue getArmorClass(AC_Type selection)
   {
      return armorClass.get(selection);
   }


   public synchronized void addNaturalArmor(Adjustment adjustment)
   {
      LOG.info("Old regular AC = " + getArmorClass(AC_Type.AC_Reg).getCurrent());
      naturalArmor.add(adjustment);
      // find and apply the largest natural armor adjustment to AC.
      if (bestNaturalArmor == null) {
         bestNaturalArmor = adjustment;
         AbilityListenerValue acEntry = armorClass.get(AC_Type.AC_Reg);
         acEntry.addAdjustment(bestNaturalArmor);
         LOG.debug("New natural armor is best, value = " + adjustment.getValue());
      }
      else if (adjustment.getValue() > bestNaturalArmor.getValue()) {
         AbilityListenerValue acEntry = armorClass.get(AC_Type.AC_Reg);
         acEntry.removeAdjustment(bestNaturalArmor);
         bestNaturalArmor = adjustment;
         acEntry.addAdjustment(bestNaturalArmor);
         LOG.debug("New natural armor is best, value = " + adjustment.getValue());
      }
      LOG.info("New regular AC = " + getArmorClass(AC_Type.AC_Reg).getCurrent());
   }


   public synchronized void removeNaturalArmor(Adjustment adjustment)
   {
      if (!naturalArmor.contains(adjustment)) {
         return;
      }
      naturalArmor.remove(adjustment);
      if (bestNaturalArmor == adjustment) {
         AbilityListenerValue acEntry = armorClass.get(AC_Type.AC_Reg);
         acEntry.removeAdjustment(bestNaturalArmor);
         bestNaturalArmor = null;
         int bestVal = -1;
         for (Adjustment armorOption : naturalArmor) {
            if (armorOption.getValue() > bestVal) {
               bestNaturalArmor = armorOption;
               bestVal = armorOption.getValue();
            }
         }
         if (bestNaturalArmor != null) {
            acEntry.addAdjustment(bestNaturalArmor);
         }
      }
   }
}
