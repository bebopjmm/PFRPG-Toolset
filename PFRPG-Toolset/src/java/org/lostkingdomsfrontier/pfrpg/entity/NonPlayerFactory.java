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

import java.util.EnumSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lostkingdomsfrontier.pfrpg.Adjustment;
import org.lostkingdomsfrontier.pfrpg.entity.classes.CharacterClass;
import org.lostkingdomsfrontier.pfrpg.entity.classes.ClassLevel;
import org.lostkingdomsfrontier.pfrpg.entity.races.Race;
import org.lostkingdomsfrontier.pfrpg.entity.talents.Skill;
import org.lostkingdomsfrontier.pfrpg.persist.BridgeException;

/**
 * @author bebopjmm
 * 
 */
public class NonPlayerFactory extends ActorFactory
{
   static final Log LOG = LogFactory.getLog(NonPlayerFactory.class);


   /**
    * The method constructs a new NonPlayer instance from the specified tempate.
    * 
    * @param npcTemplateID id of the NPC template to base new NonPlayer from
    * @return
    * @throws InvalidEntityException
    */
   public void initNPC(NonPlayer npc) throws InvalidEntityException, BridgeException
   {
      if (!verifyBridgeConfiguration()) {
         throw new BridgeException("Not all bridges have been assigned");
      }

      // ------------------------------------------
      // Overview of instantiation steps.
      // 1. Configure the abilities using the template
      // 2. Initialize the baseline set of skills
      // 3. Configure racial baseline info using the template
      // 4. Process the advancements by level
      // ------------------------------------------

      // 1. Configuring the abilities using the ncpTemplate
      npc.initAbilities();
      if (LOG.isDebugEnabled()) {
         for (Ability a : EnumSet.range(Ability.STR, Ability.CHA)) {
            LOG.debug("Ability, " + a + " has base value: " + npc.abilities.get(a).getBase());
         }
      }

      // 2. Establish the base set of skills (Everyone has untrained skills in their list)
      Skill[] untrainedSkills = talentBridge.retrieveUntrainedSkills();
      if (untrainedSkills != null) {
         LOG.info("Initializing basic skills, total = " + untrainedSkills.length);
//         for (Skill skill : untrainedSkills) {
//            npc.initSkill(skill);
//         }
      }
      else {
         LOG.warn("NULL untrained skills! There is likely a problem with the SkillContentBridge!");
      }

      // 3. Configure Racial Baseline info
      String raceID = npc.getRace().getId();
      Race npcRace = raceBridge.retrieveRace(raceID);
      npc.setRace(npcRace);
      if (LOG.isDebugEnabled()) {
         LOG.debug("Abilities following racial assignment:");
         for (Ability a : EnumSet.range(Ability.STR, Ability.CHA)) {
            LOG.debug("Ability, " + a + " has current value: " + npc.abilities.get(a).getCurrent());
         }
      }
      LOG.debug("racial characteristics complete");

      // 4. Process the Level Advancements. For each level we need to
      // 4.1. Advance the correct class level.
      // 4.2. Advance the hit points
      // 4.3. Advance the skill points
      // 4.4. Advance abilities
      CharacterLevel characterLevel;
      CharacterClass levelClass = null;
      LOG.info("Total levels for " + npc.getName() + " = " + npc.level);
//      for (int i = 0; i < npc.level; i++) {
//         characterLevel = npc.getCharacterLevels().levels.get(i);
//
//         // Aggregate Hit Points.
//         npc.levelUpHP(characterLevel.getHitPoints());
//
//         // TODO Aggregate Skills
//         for (SkillTraining training : characterLevel.skills) {
//            Skill skill = talentBridge.retrieveSkill(training.getSkillID());
//
//            // if new skill, then it must be initialized.
//            if (!npc.hasSkill(skill)) {
//               npc.initSkill(skill);
//            }
//            // add the ranks to the skill
//            npc.advanceSkill(skill, training.getPoints());
//         }
//         
//         // Apply Class Bonuses
//         if(characterLevel.getClassID().getLocalPart().equals("racial")) {
//            LOG.warn("NO SUPPORT YET FOR RACIAL CLASS LEVELS");
//            continue;
//            // TODO Add support for racial level bonuses (if necessary)
//         }
//         levelClass = classBridge.retrieveClass(characterLevel.getClassID());
//         if (npc.classMap.containsKey(levelClass)) {
//            int level = npc.classMap.get(levelClass).intValue() + 1;
//            LOG.debug("Advancing " + npc.getName() + " level in "
//                  + characterLevel.getClassID().getLocalPart() + " to " + level);
//            npc.classMap.put(levelClass, level);
//         }
//         else {
//            npc.classMap.put(levelClass, Integer.valueOf(1));
//            LOG.debug("Initializing " + npc.getName() + " level in "
//                  + characterLevel.getClassID().getLocalPart() + " to 1");
//         }
//      }

      // Establish Base Attack Bonus and Saving Throws based on class(es) and level.
//      Set<CharacterClass> charClasses = npc.classMap.keySet();
//      int bab = 0;
//      int fort = 0;
//      int will = 0;
//      int refl = 0;
//      int level = 0;
//      ClassLevel classLevel;
//      StringBuffer nameBuffer = new StringBuffer();
//      LOG.debug("Total characterClasses to process = " + charClasses.size());
//      for (CharacterClass characterClass : charClasses) {
//         level = npc.classMap.get(characterClass).intValue();
//         LOG.debug(npc.getName() + " is a level " + level + characterClass.getShortName());
//         classLevel = characterClass.getAdvancementLevel(level-1);
//         bab += classLevel.getBaseAttackBonus();
//         fort += classLevel.getSaves().fort;
//         will += classLevel.getSaves().will;
//         refl += classLevel.getSaves().refl;
//         nameBuffer.append(characterClass.getShortName() + level);
//      }
////      npc.setClassDescript(nameBuffer.toString());
//      npc.attackStats.modifyBaseAttackBonus(bab);
////      npc.setMaxBaseAttackBonus(bab);

      // Assign Class-Based Adjustments to Saving Throws
      Adjustment saveAdj;
      AbilityListenerValue saveVal;
      
//      saveAdj = new Adjustment(AdjustmentType.INHERENT, fort, new QName(
//            "java:org.rollinitiative.d20.entity", "classLevel"));
//      saveVal = npc.saves.get(SavingThrow.FORTITUDE);
//      saveVal.addAdjustment(saveAdj);
//      
//      saveAdj = new Adjustment(AdjustmentType.INHERENT, will, new QName(
//            "java:org.rollinitiative.d20.entity", "classLevel"));
//      saveVal = npc.saves.get(SavingThrow.WILL);
//      saveVal.addAdjustment(saveAdj);
//      
//      saveAdj = new Adjustment(AdjustmentType.INHERENT, refl, new QName(
//            "java:org.rollinitiative.d20.entity", "classLevel"));
//      saveVal = npc.saves.get(SavingThrow.REFLEX);
//      saveVal.addAdjustment(saveAdj);

      // // Process the character's inventory.
      // InventoryItemType[] inventory = npcTemplate.getEquipment().getItemArray();
      // processInventory(npc, inventory);
   }

}
