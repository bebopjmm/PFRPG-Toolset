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
package org.lostkingdomsfrontier.pfrpg.entity;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


/**
 * @author jmccormi
 *
 */
@XmlType(name = "CharacterLevelType", namespace = "java:org.rollinitiative.d20.entity")
public class CharacterLevel
{

   private int level = 0;
   
   private int hitPoints = 0;
   
   private QName classID = new QName("NOT SET");
   
//   @XmlElement(name="SkillImprove")
//   ArrayList<SkillTraining> skills = new ArrayList<SkillTraining>();

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
    * @return the hitPoints
    */
   public int getHitPoints()
   {
      return hitPoints;
   }

   /**
    * @param hitPoints the hitPoints to set
    */
   @XmlAttribute(required = true)
   public void setHitPoints(int hitPoints)
   {
      this.hitPoints = hitPoints;
   }

   /**
    * @return the classID
    */
   public QName getClassID()
   {
      return classID;
   }

   /**
    * @param classID the classID to set
    */
   @XmlAttribute(required = true)
   public void setClassID(QName classID)
   {
      this.classID = classID;
   }
   
   
}
