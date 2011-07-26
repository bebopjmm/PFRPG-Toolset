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

import org.lostkingdomsfrontier.pfrpg.encounter.Action;
import org.lostkingdomsfrontier.pfrpg.encounter.Combatant;
import org.lostkingdomsfrontier.pfrpgtools.arena.Arena;

public class TotalDefense implements Action
{
   public double calculate(Combatant combatant)
   {
      // Determine likelihood of performing a TotalDefense
      // Check HP, can't move, etc..

//      int hp = combatant.getActor().getHP();
//      int max = combatant.getActor().getMaxHP();
//
//      return hp / max;
      return 1.0;
   }


   public void execute()
   {
      // Modify dodge bonus +4 for one round.
   }
}
