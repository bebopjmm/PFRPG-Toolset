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

import java.util.EnumMap;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.xml.bind.annotation.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lostkingdomsfrontier.pfrpg.AdjustableValue;

/**
 * @author bebopjmm
 * 
 */
@Embeddable
@XmlType(name = "MovementType", namespace = "java:org.rollinitiative.d20.entity")
public class Movement
{
   static final Log LOG_ = LogFactory.getLog(Movement.class);

   public enum EncumberanceType {
      LIGHT, MEDIUM, HEAVY;
   }

   private int speedLight;
   
   private int speedMedium;
   
   private int speedHeavy;
   
   @Transient
   EnumMap<EncumberanceType, AdjustableValue> moveMap = new EnumMap<EncumberanceType, AdjustableValue>(
         EncumberanceType.class);


   public Movement()
   {
      this(0, 0, 0);
   }


   public Movement(int speedLight, int speedMedium, int speedHeavy)
   {
       this.speedLight = speedLight;
      AdjustableValue speedValue = new AdjustableValue(speedLight);
      moveMap.put(EncumberanceType.LIGHT, speedValue);
      this.speedMedium = speedMedium;
      speedValue = new AdjustableValue(speedMedium);
      moveMap.put(EncumberanceType.MEDIUM, speedValue);
      this.speedHeavy = speedHeavy;
      speedValue = new AdjustableValue(speedHeavy);
      moveMap.put(EncumberanceType.HEAVY, speedValue);
   }


   public int getSpeed(EncumberanceType encumberance)
   {
      return moveMap.get(encumberance).getCurrent();
   }


   public AdjustableValue getSpeedValue(EncumberanceType encumberance)
   {
      return moveMap.get(encumberance);
   }


   @XmlAttribute(name = "light", required = true)
   public void setLightEncumberanceSpeed(int speed)
   {
      AdjustableValue speedValue = moveMap.get(EncumberanceType.LIGHT);
      speedValue.setBase(speed);
      this.speedLight = speed;
   }


   @XmlAttribute(name = "medium", required = true)
   public void setMediumEncumberanceSpeed(int speed)
   {
      AdjustableValue speedValue = moveMap.get(EncumberanceType.MEDIUM);
      speedValue.setBase(speed);
      this.speedMedium = speed;
   }


   @XmlAttribute(name = "heavy", required = true)
   public void setHeavyEncumberanceSpeed(int speed)
   {
      AdjustableValue speedValue = moveMap.get(EncumberanceType.HEAVY);
      speedValue.setBase(speed);
      this.speedHeavy = speed;
   }

}
