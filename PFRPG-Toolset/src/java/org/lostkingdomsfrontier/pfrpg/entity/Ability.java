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

import javax.xml.bind.annotation.XmlType;

/**
 * Enumeration of an Actor's characteristic abilities, as per the D20 SRD.
 * 
 * @author bebopjmm
 * @since sprint-0.1
 */
@XmlType (name = "AbilityEnum", namespace = "java:org.rollinitiative.d20.entity")
public enum Ability {
   STR, DEX, CON, INT, WIS, CHA;

   /**
    * Returns the ability modifier for the provided value
    * 
    * @param value
    * @return modifier
    * @since sprint-0.1
    */
   public static short getModifier(short value)
   {
      return (short)(value / 2 - 5);
   }
}
