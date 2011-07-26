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
package org.lostkingdomsfrontier.pfrpg.items;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.lostkingdomsfrontier.pfrpg.Adjustment;
import org.lostkingdomsfrontier.pfrpg.AdjustmentCategory;

/**
 * @author bebopjmm
 * 
 */
@XmlType(name = "ArmorType", namespace = "java:org.rollinitiative.d20.item")
public class Armor extends Item
{
   @XmlType(name = "EncumberanceEnum", namespace = "java:org.rollinitiative.d20.item")
   public enum EncumberanceCategory {
      LIGHT, MEDIUM, HEAVY;
   }
   
   private int defenseBonus;
   
   private int maxDexBonus;
   
   private int arcaneFailRisk;

   private Adjustment armorAdjust;
   
   private int skillPenalty;

   private Adjustment skillAdjust;

   private EncumberanceCategory encumberance;


   public Armor()
   {
      super();
   }
   
   
   
   /**
    * @return the maxDexBonus
    */
   public int getMaxDexBonus()
   {
      return maxDexBonus;
   }


   /**
    * @param maxDexBonus the maxDexBonus to set
    */
   @XmlAttribute(required=true)
   public void setMaxDexBonus(int maxDexBonus)
   {
      this.maxDexBonus = maxDexBonus;
   }


   /**
    * @return the arcaneFailRisk
    */
   public int getArcaneFailRisk()
   {
      return arcaneFailRisk;
   }


   /**
    * @param arcaneFailRisk the arcaneFailRisk to set
    */
   @XmlAttribute(required=true)
   public void setArcaneFailRisk(int arcaneFailRisk)
   {
      this.arcaneFailRisk = arcaneFailRisk;
   }


   /**
    * @return this item's defensive Armor Adjustment
    */
   public Adjustment getArmorAdjust()
   {
      return armorAdjust;
   }

   
   public int getDefenseBonus()
   {
      return this.defenseBonus;
   }

   /**
    * @param bonus the armorBonus to set
    */
   @XmlAttribute(required=true)
   public void setDefenseBonus(int bonus)
   {
      this.defenseBonus = bonus;
   }


   /**
    * @return the skillPenalty
    */
   public Adjustment getSkillAdjust()
   {
      return skillAdjust;
   }
   
   public int getSkillPenalty()
   {
      return skillPenalty;
   }


   /**
    * @param skillPenalty the skillPenalty to set
    */
   @XmlAttribute(required=true)
   public void setSkillPenalty(int penalty)
   {
      this.skillPenalty = penalty;
   }


   /**
    * @return the encumberance
    */
   public EncumberanceCategory getEncumberance()
   {
      return encumberance;
   }


   /**
    * @param encumberance the encumberance to set
    */
   @XmlAttribute(required=true)
   public void setEncumberance(EncumberanceCategory encumberance)
   {
      this.encumberance = encumberance;
   }

   @Override
   protected void configure()
   {
      // Note that this may have to be configured AFTER
      this.armorAdjust = new Adjustment(AdjustmentCategory.EQUIPMENT, (short)getDefenseBonus(), getTypeID());
      this.skillAdjust = new Adjustment(AdjustmentCategory.EQUIPMENT, (short)getSkillPenalty(), getTypeID());
   }
}
