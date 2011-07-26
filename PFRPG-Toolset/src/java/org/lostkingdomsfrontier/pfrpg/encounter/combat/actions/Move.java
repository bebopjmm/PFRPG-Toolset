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

public class Move implements Action
{
   public double calculate(Combatant combatant)
   {
      // Calculate near target, weapon drawn, etc...
      return 0.0;
   }


   public void move()
   {
      System.out.println("Move!");
   }


   public void execute()
   {
      move();
   }
}
