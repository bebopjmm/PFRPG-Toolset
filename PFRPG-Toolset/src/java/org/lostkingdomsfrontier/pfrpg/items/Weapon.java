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

/**
 * @author bebopjmm
 * 
 */
@XmlType(name = "WeaponType", namespace = "java:org.rollinitiative.d20.item")
public class Weapon extends Item
{
   @XmlType(name = "WeaponCategoryEnum", namespace = "java:org.rollinitiative.d20.item")
   public enum Category {
      SIMPLE, MARTIAL, EXOTIC;
   }

   private Category category;

   private int damageNumDice;

   private int damageDie;

   private int damageMod;
   

   /**
    * @return the category
    */
   public Category getCategory()
   {
      return category;
   }

   /**
    * @param category the category to set
    */
   @XmlAttribute(required = true)
   public void setCategory(Category category)
   {
      this.category = category;
   }

   /**
    * @return the damageNumDice
    */
   public int getDamageNumDice()
   {
      return damageNumDice;
   }

   /**
    * @param damageNumDice the damageNumDice to set
    */
   @XmlAttribute(required = true)
   public void setDamageNumDice(int damageNumDice)
   {
      this.damageNumDice = damageNumDice;
   }

   /**
    * @return the damageDie
    */
   public int getDamageDie()
   {
      return damageDie;
   }

   /**
    * @param damageDie the damageDie to set
    */
   @XmlAttribute(required = true)
   public void setDamageDie(int damageDie)
   {
      this.damageDie = damageDie;
   }

   /**
    * @return the damageMod
    */
   public int getDamageMod()
   {
      return damageMod;
   }

   /**
    * @param damageMod the damageMod to set
    */
   @XmlAttribute(required = true)
   public void setDamageMod(int damageMod)
   {
      this.damageMod = damageMod;
   }

   @Override
   protected void configure()
   {
      // TODO Auto-generated method stub
      
   }
}
