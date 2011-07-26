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

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

import org.lostkingdomsfrontier.pfrpg.entity.Alignment;

/**
 * @author jmccormi
 * 
 */
@XmlRootElement(name = "Class", namespace = "java:org.rollinitiative.d20.entity")
@XmlType(name = "ClassType", namespace = "java:org.rollinitiative.d20.entity")
public class CharacterClass
{
   private static final String NOT_SET = "NOT SET";

   private QName id = new QName(NOT_SET);

   private String shortName = NOT_SET;

   private String longName = NOT_SET;

   private int hitDie = 0;

   private int skillPoints = 0;

   @XmlElement(name = "AlignRestriction")
   ArrayList<Alignment> alignRestrict = new ArrayList<Alignment>();

   @XmlElement(name = "ClassSkill")
   ArrayList<QName> classSkills = new ArrayList<QName>();
   
   @XmlElement(name="Advancement", required = true)
   Advancement advancement = new Advancement();


   public CharacterClass()
   {

   }


   /**
    * @return the id
    */
   public QName getId()
   {
      return id;
   }


   /**
    * @param id the id to set
    */
   @XmlAttribute(required = true)
   public void setId(QName id)
   {
      this.id = id;
   }


   /**
    * @return the shortName
    */
   public String getShortName()
   {
      return shortName;
   }


   /**
    * @param shortName the shortName to set
    */
   @XmlAttribute(required = true)
   public void setShortName(String shortName)
   {
      this.shortName = shortName;
   }


   /**
    * @return the longName
    */
   public String getLongName()
   {
      return longName;
   }


   /**
    * @param longName the longName to set
    */
   @XmlAttribute(required = true)
   public void setLongName(String longName)
   {
      this.longName = longName;
   }


   /**
    * @return the hitDie
    */
   public int getHitDie()
   {
      return hitDie;
   }


   /**
    * @param hitDie the hitDie to set
    */
   @XmlAttribute(required = true)
   public void setHitDie(int hitDie)
   {
      this.hitDie = hitDie;
   }


   /**
    * @return the skillPoints
    */
   public int getSkillPoints()
   {
      return skillPoints;
   }


   /**
    * @param skillPoints the skillPoints to set
    */
   @XmlAttribute(required = true)
   public void setSkillPoints(int skillPoints)
   {
      this.skillPoints = skillPoints;
   }


   public boolean isClassSkill(QName skillID)
   {
      return (classSkills.contains(skillID));

   }
   
   public ClassLevel getAdvancementLevel(int level)
   {
      return this.advancement.levels.get(level);
   }
   
   @XmlType(name ="ClassLevelListType", namespace = "java:org.rollinitiative.d20.entity")
   public static class Advancement
   {
      @XmlElement(name="LevelEntry", required = true)
      ArrayList<ClassLevel> levels = new ArrayList<ClassLevel>();
   }
 
}
