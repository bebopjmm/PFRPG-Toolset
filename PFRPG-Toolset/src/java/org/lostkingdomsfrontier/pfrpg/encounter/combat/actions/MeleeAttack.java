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

import javax.xml.bind.annotation.XmlType;


/**
 * @author jmccormi
 *
 */
@XmlType(name = "MeleeAttackType", namespace = "java:org.rollinitiative.d20.combat")
public class MeleeAttack extends Attack
{

   int reach;

   /**
    * @return the reach
    */
   public int getReach()
   {
      return reach;
   }

   /**
    * @param reach the reach to set
    */
   public void setReach(int reach)
   {
      this.reach = reach;
   }
   
}
