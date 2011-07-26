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
import java.util.Hashtable;
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
 * The CharacterFactory is primarily responsible for configuring Player objects following
 * unmarshalling.
 * 
 * @author bebopjmm
 * 
 */
public class CharacterFactory extends ActorFactory
{
   static final Log LOG = LogFactory.getLog(CharacterFactory.class);


   public CharacterFactory()
   {
      super();
   }


   /**
    * 
    * Overview of instantiation steps.
    * <ol>
    * <li>Configure the abilities</li>
    * <li>Initialize baseline skills</li>
    * <li>Apply racial characteristics</li>
    * </ol>
    * 
    * @param player
    */
   public void initPlayer(Player player) throws InvalidEntityException
   {
      LOG.info(player.getName() + " has an alignment of " + player.getAlignment());
      LOG.info(player.getName() + " has a total level of " + player.level);
      player.initAbilities();

      // Establish the base set of skills (Everyone has untrained skills in their list)
      Skill[] untrainedSkills = talentBridge.retrieveUntrainedSkills();
      if (untrainedSkills != null) {
         LOG.info("Initializing basic skills, total = " + untrainedSkills.length);
         for (Skill skill : untrainedSkills) {
//            player.initSkill(skill);
         }
      }
      else {
         LOG.warn("NULL untrained skills! There is likely a problem with the SkillContentBridge!");
      }

      // Apply Racial Characteristics: movement, ability bonuses
      String raceID = player.getRace().getId();
      LOG.debug("Applying racial characteristics for raceID = " + raceID);

      Race playerRace = raceBridge.retrieveRace(raceID);
      player.setRace(playerRace);
      if (LOG.isDebugEnabled()) {
         LOG.debug("Abilities following racial assignment:");
         for (Ability a : EnumSet.range(Ability.STR, Ability.CHA)) {
            LOG.debug("Ability, " + a + " has current value: "
                  + player.abilities.get(a).getCurrent());
         }
      }
      LOG.debug("racial characteristics complete");

      // 
      // Process the Level Advancements. For each level we need to
      // 1. Advance the correct class level.
      // 2. Advance the hit points
      // 3. Advance the skill points
      // 4. Advance abilities
      //
      CharacterLevel characterLevel;
      CharacterClass levelClass = null;
//      for (int i = 0; i < player.level; i++) {
//         characterLevel = player.getCharacterLevels().levels.get(i);
//         levelClass = classBridge.retrieveClass(characterLevel.getClassID());
//         if (player.classMap.containsKey(levelClass)) {
//            int level = player.classMap.get(levelClass).intValue() + 1;
//            LOG.debug("Advancing " + player.getName() + " level in "
//                  + characterLevel.getClassID().getLocalPart() + " to " + level);
//            player.classMap.put(levelClass, level);
//         }
//         else {
//            player.classMap.put(levelClass, Integer.valueOf(1));
//            LOG.debug("Initializing " + player.getName() + " level in "
//                  + characterLevel.getClassID().getLocalPart() + " to 1");
//         }
//
//         // Aggregate Hit Points.
//         player.levelUpHP(characterLevel.getHitPoints());
//
//         // TODO Aggregate Skills
//         for (SkillTraining training : characterLevel.skills) {
//            Skill skill = talentBridge.retrieveSkill(training.getSkillID());
//
//            // if new skill, then it must be initialized.
//            if (!player.hasSkill(skill)) {
//               player.initSkill(skill);
//            }
//            // add the ranks to the skill
//            player.advanceSkill(skill, training.getPoints());
//         }
//
//      }

      // TODO Establish Base Attack Bonus and Saving Throws based on class(es) and level.
//      Set<CharacterClass> charClasses = player.classMap.keySet();
//      int bab = 0;
//      int fort = 0;
//      int will = 0;
//      int refl = 0;
//      int level = 0;
//      ClassLevel classLevel;
//      StringBuffer nameBuffer = new StringBuffer();
//      LOG.debug("Total characterClasses to process = " + charClasses.size());
//      for (CharacterClass characterClass : charClasses) {
//         level = player.classMap.get(characterClass).intValue();
//         LOG.debug(player.getName() + " is a level " + level + characterClass.getShortName());
//         classLevel = characterClass.getAdvancementLevel(level - 1);
//
//         bab += classLevel.getBaseAttackBonus();
//         fort += classLevel.getSaves().fort;
//         will += classLevel.getSaves().will;
//         refl += classLevel.getSaves().refl;
//         nameBuffer.append(characterClass.getShortName() + level);
//         LOG.debug("Processed level " + level + " of " + characterClass.getShortName());
//      }
//      player.setClassDescript(nameBuffer.toString());
//      LOG.debug("Class adjustment to BAB: " + bab);
//      player.attackStats.modifyBaseAttackBonus(bab);
      // player.setMaxBaseAttackBonus(bab);

      // Assign Class-Based Adjustments to Saving Throws
      Adjustment saveAdj;
      AbilityListenerValue saveVal;

//      saveAdj = new Adjustment(AdjustmentType.INHERENT, fort, new QName(
//            "java:org.rollinitiative.d20.entity", "classLevel"));
//      saveVal = player.saves.get(SavingThrow.FORTITUDE);
//      saveVal.addAdjustment(saveAdj);
//
//      saveAdj = new Adjustment(AdjustmentType.INHERENT, will, new QName(
//            "java:org.rollinitiative.d20.entity", "classLevel"));
//      saveVal = player.saves.get(SavingThrow.WILL);
//      saveVal.addAdjustment(saveAdj);
//
//      saveAdj = new Adjustment(AdjustmentType.INHERENT, refl, new QName(
//            "java:org.rollinitiative.d20.entity", "classLevel"));
//      saveVal = player.saves.get(SavingThrow.REFLEX);
//      saveVal.addAdjustment(saveAdj);

      // Process the character's inventory.
      processInventory(player);
   }

}
