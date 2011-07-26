/**
 * Project: PFRPG-Toolset
 * Created: Aug 9, 2006 by bebopJMM
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
package org.lostkingdomsfrontier.pfrpg.dice;

import java.util.Random;

/**
 * Singleton for generating dice rolling values.
 * 
 * @author bebopJMM
 * 
 */
public class Dice
{

   private Random random_;

   static Dice INSTANCE_ = null;


   private Dice()
   {
      this.random_ = new Random();
   }


   /**
    * @return
    */
   public static Dice getInstance()
   {
      if (INSTANCE_ == null) {
         INSTANCE_ = new Dice();
      }
      return INSTANCE_;
   }


   /**
    * 
    * @param numDice
    * @param sides
    * @param modifier
    * @return
    */
   public int roll(int numDice, int sides, int modifier)
   {
      int value = 0;
      for (int i = 0; i < numDice; i++) {
         value += 1 + (int) (random_.nextInt(sides));
      }
      return value + modifier;
   }

}
