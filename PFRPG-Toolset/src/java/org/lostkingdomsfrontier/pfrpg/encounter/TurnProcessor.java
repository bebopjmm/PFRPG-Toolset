/**
 * Project: PFRPG-Toolset
 * Created: Aug 13, 2007 by bebopJMM
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lostkingdomsfrontier.pfrpg.encounter.combat.actions.Attack;
import org.lostkingdomsfrontier.pfrpg.encounter.combat.actions.MeleeAttack;
import org.lostkingdomsfrontier.pfrpg.entity.Actor;
import org.lostkingdomsfrontier.pfrpgtools.arena.Arena;

/**
 * @author bebopJMM
 * 
 */
public class TurnProcessor
{
   static final Log LOG = LogFactory.getLog(TurnProcessor.class);

   Encounter encounter;


   public void setEncounter(Encounter encounter)
   {
      this.encounter = encounter;
   }


   public void process(Combatant combatant)
   {
      LOG.info("Processing actionSet");

      // Need to populate action set...
      ActionSet actionSet = combatant.getActionSet();

      // Get action set
      // Calculate action
      // Execute action

      // Action[] = { new Attack(), new TotalDefense(), new Move()...
      // For now... choose FIGHT! :)
      Actor defender = null;

      // if (arena.getPartyList().contains(combatant.getActor())) {
      // // defender = getStrongestHostileDefender(attacker);
      // defender = arena.getWeakestHostileDefender(combatant.getActor());
      // // defender = getHostileDefender();
      // }
      // else
      // defender = arena.getPartyDefender();
      Attack action = new MeleeAttack();
      action.setAttacker(combatant);
      action.execute();
   }


   public void process(ActionSet actionSet)
   {
      // FIXME This only works for full-round actions right now.
      LOG.info("Processing actionSet");
      actionSet.executeNext();
   }
}
