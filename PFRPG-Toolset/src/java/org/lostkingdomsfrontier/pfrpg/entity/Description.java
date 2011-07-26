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

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author jmccormi
 *
 */
@SuppressWarnings("serial")
@Embeddable
@XmlType(name = "DescriptonType", namespace = "java:org.rollinitiative.d20.entity")
public class Description implements Serializable
{  
   private Gender gender;
   
   private int height = 0;
   
   private int weight = 0;
   
   private int age = 0;
   
   private String eyes = "";
   
   private String hair = "";
   
   private String skin = "";

   /**
    * @return the gender
    */
   public Gender getGender()
   {
      return gender;
   }

   /**
    * @param gender the gender to set
    */
   @XmlElement (name = "Gender", required = true)
   public void setGender(Gender gender)
   {
      this.gender = gender;
   }

   /**
    * @return the height
    */
   public int getHeight()
   {
      return height;
   }

   /**
    * @param height the height to set
    */
   @XmlElement (name = "Height")
   public void setHeight(int height)
   {
      this.height = height;
   }

   /**
    * @return the weight
    */
   public int getWeight()
   {
      return weight;
   }

   /**
    * @param weight the weight to set
    */
   @XmlElement (name = "Weight")
   public void setWeight(int weight)
   {
      this.weight = weight;
   }

   /**
    * @return the age
    */
   public int getAge()
   {
      return age;
   }

   /**
    * @param age the age to set
    */
   @XmlElement (name = "Age")
   public void setAge(int age)
   {
      this.age = age;
   }

   /**
    * @return the eyes
    */
   public String getEyes()
   {
      return eyes;
   }

   /**
    * @param eyes the eyes to set
    */
   @XmlElement (name = "Eyes")
   public void setEyes(String eyes)
   {
      this.eyes = eyes;
   }

   /**
    * @return the hair
    */
   public String getHair()
   {
      return hair;
   }

   /**
    * @param hair the hair to set
    */
   @XmlElement (name = "Hair")
   public void setHair(String hair)
   {
      this.hair = hair;
   }

   /**
    * @return the skin
    */
   public String getSkin()
   {
      return skin;
   }

   /**
    * @param skin the skin to set
    */
   @XmlElement (name = "Skin")
   public void setSkin(String skin)
   {
      this.skin = skin;
   }
 
}
