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
package org.lostkingdomsfrontier.pfrpg.encounter.combat.actions;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * @author jmccormi
 *
 */
@XmlType(name = "RangedAttackType", namespace = "java:org.rollinitiative.d20.combat")
public class RangedAttack extends Attack
{

   int rangedIncrement;

   /**
    * @return the rangedIncrement
    */
   public int getRangedIncrement()
   {
      return rangedIncrement;
   }

   /**
    * @param rangedIncrement the rangedIncrement to set
    */
   @XmlAttribute(required = true)
   public void setRangedIncrement(int rangedIncrement)
   {
      this.rangedIncrement = rangedIncrement;
   }
   
   
}
