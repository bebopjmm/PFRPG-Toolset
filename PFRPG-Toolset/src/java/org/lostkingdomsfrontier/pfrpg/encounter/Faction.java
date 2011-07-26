/**
 * Project: PFRPG-Toolset
 * Created: Aug 13, 2006 by bebopJMM
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
package org.lostkingdomsfrontier.pfrpg.encounter;

/**
 * A Faction is used to represent factions of interest within an encounter. The default factions are
 * PARTY, ALLY, NEUTRAL, and HOSTILE.
 * 
 * @author bebopJMM
 */
public class Faction
{

   public static final Faction PARTY = new Faction("Party");

   public static final Faction ALLY = new Faction("Allies");

   public static final Faction NEUTRAL = new Faction("Neutrals");

   public static final Faction HOSTILE = new Faction("Hostiles");

   private String descriptor;


   public Faction(String descriptor)
   {
      this.descriptor = descriptor;
   }


   public String toString()
   {
      return descriptor;
   }

}
