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
package org.lostkingdomsfrontier.pfrpg.entity.classes;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.lostkingdomsfrontier.pfrpg.entity.SavingThrow.SaveGroup;

/**
 * @author jmccormi
 *
 */
@XmlType(name = "ClassLevelType", namespace = "java:org.rollinitiative.d20.entity")
public class ClassLevel
{
   // TODO Add Talents

   private int level;
   
   private int baseAttackBonus;
   
   private SaveGroup saves;

   /**
    * @return the level
    */
   public int getLevel()
   {
      return level;
   }

   /**
    * @param level the level to set
    */
   @XmlAttribute(required = true)
   public void setLevel(int level)
   {
      this.level = level;
   }

   /**
    * @return the baseAttackBonus
    */
   public int getBaseAttackBonus()
   {
      return baseAttackBonus;
   }

   /**
    * @param baseAttackBonus the baseAttackBonus to set
    */
   @XmlElement(name = "BaseAttackBonus", required = true)
   public void setBaseAttackBonus(int baseAttackBonus)
   {
      this.baseAttackBonus = baseAttackBonus;
   }

   /**
    * @return the saves
    */
   public SaveGroup getSaves()
   {
      return saves;
   }

   /**
    * @param saves the saves to set
    */
   @XmlElement(name = "Saves", required = true)
   public void setSaves(SaveGroup saves)
   {
      this.saves = saves;
   }
   
   
   
}