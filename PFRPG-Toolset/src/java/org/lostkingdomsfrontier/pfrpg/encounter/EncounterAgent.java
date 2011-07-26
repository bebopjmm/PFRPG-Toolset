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
package org.lostkingdomsfrontier.pfrpg.encounter;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lostkingdomsfrontier.pfrpg.dice.Dice;

/**
 * An encounter agent processes the execution of encounters
 * Basic Usage:
 * <ol>
 * <li>Instantiate the encounter and add all the participants via joinEncounter</li>
 * <li>Make all combatants aware that are not surprised via makeCombatantAware</li>
 * <li>Begin the encounter by invoking initialize()</li>
 * <li>For a fully automated resolution of a single round, use executeRound()</li>
 * <li>To step incrementally through each combatant in initiative order, use advanceToNextCombatant
 * and executeActionSet.</li>
 * </ol>
 * @author bebopjmm
 *
 *        TODO Currently cannot handle concept of DELAYS or READY Actions, particularly with
 *         respect to impact on turn order.
 */
public class EncounterAgent
{
   static final Log LOG = LogFactory.getLog(EncounterAgent.class);
   
   /**
    * 
    */
    Dice dice = Dice.getInstance();

    TurnProcessor turnEngine = new TurnProcessor();
    
    Encounter currentEncounter;
    
    /**
     * Flag if the encounter is currently active and in progress
     */
    boolean isActive = false;
    
    /**
     * Counter of the current round of this encounter.
     */
    int currentRound = 0;
    

    Iterator<Combatant> turnOrder;
    
    Combatant activeCombatant = null;
    
    ArrayList<EncounterListener> listeners = new ArrayList<EncounterListener>();


   /**
    * @return the currentEncounter
    */
   public Encounter getCurrentEncounter()
   {
      return currentEncounter;
   }

   /**
    * @param currentEncounter the currentEncounter to set
    */
   public void setCurrentEncounter(Encounter currentEncounter)
   {
      this.currentEncounter = currentEncounter;
   }
   
   
    
   /**
    * @return the currentRound
    */
   public int getCurrentRound()
   {
      return currentRound;
   }


   public synchronized void subscribe(EncounterListener listener)
   {
      if (!listeners.contains(listener)) {
         listeners.add(listener);
      }
   }
   
   public synchronized void unsubscribe(EncounterListener listener)
   {
      if (listeners.contains(listener)) {
         listeners.remove(listener);
      }
   }
   
   public synchronized void initialize()
   {
      // Don't initialize an encounter that is already active.
      if (isActive) {
         LOG.warn("Ignoring attempt to initiate encounter that is already active, id = "
               + currentEncounter.getName());
         return;
      }

      isActive = true;
      currentRound = 0;

      turnEngine = new TurnProcessor();
      turnEngine.setEncounter(this.currentEncounter);

      advanceRound();
   }
   
   /**
    * This method conducts a single round of the encounter.
    */
   public synchronized void executeRound()
   {
      if (!isActive) {
         LOG.warn("Cannot execute an Encounter that has not been initialized.");
         return;
      }
      // TODO: Launch threads for all the combatants

      // Process rounds until the round is complete
      currentRound++;
      LOG.info("Beginnning combat sequence for round: " + currentRound);
      Combatant[] combatants;

      LOG.info("Round = " + currentRound);
      combatants = currentEncounter.getInitiativeOrder();
      for (Combatant combatant : combatants) {
         LOG.info("Next up is " + combatant.getActor().getName());
         // retrieve and execute actions selected by the combatant
         turnEngine.process(combatant.getActionSet());
      }

   }
   
   public void executeActionSet()
   {
      // retrieve and execute actions selected by the combatant
      turnEngine.process(activeCombatant.getActionSet());
   }
   
   public Combatant advanceToNextCombatant()
   {
      if (!isActive) {
         LOG.warn("Attempt to advanceToNextCombatant before Encounter has been initiallized.");
         return null;
      }

      if (!turnOrder.hasNext()) {
         advanceRound();
      }

      activeCombatant = turnOrder.next();
      return activeCombatant;
   }
   
   void advanceRound()
   {
      synchronized (listeners) {
         if (LOG.isDebugEnabled()) {
            LOG.debug("Notifying subscribers, total = " + listeners.size());
         }
         for (EncounterListener listener : listeners) {
            listener.endOfRound(currentRound);
         }
      }
      
      currentRound++;
      // turnOrder = initiativeOrder.descendingIterator();
      turnOrder = currentEncounter.initiativeOrder.iterator();
   }
    
}
