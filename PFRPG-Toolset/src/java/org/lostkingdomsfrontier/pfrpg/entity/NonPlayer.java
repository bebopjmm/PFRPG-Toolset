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

import javax.persistence.Entity;

import org.lostkingdomsfrontier.pfrpg.entity.talents.SkillsCollection;

/**
 * @author bebopjmm
 * 
 * @since sprint-0.2
 */
@SuppressWarnings("serial")
@Entity
public class NonPlayer extends Actor
{
   
   public NonPlayer()
   {
      super();
   }

   public NonPlayer(String name, SkillsCollection rulesetSkills)
   {
      super(name, rulesetSkills);
   }


   public void setHitPoints(HitPoints hitPoints)
   {
//      this.hp = hitPoints;
   }

}
