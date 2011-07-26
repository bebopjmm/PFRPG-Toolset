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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lostkingdomsfrontier.pfrpg.entity.talents.ManagedSkill;
import org.lostkingdomsfrontier.pfrpg.entity.talents.Skill;
import org.lostkingdomsfrontier.pfrpg.entity.talents.SkillRules;

/**
 * @author bebopjmm
 * 
 */
public class AdvancementEngine
{
    static final Log LOG = LogFactory.getLog(AdvancementEngine.class);

    static SkillRules skillRules;


    public AdvancementEngine()
    {

    }


    /**
     * @return the skillRules
     */
    public SkillRules getSkillRules()
    {
        return skillRules;
    }


    /**
     * @param skillRules the skillRules to set
     */
    public void setSkillRules(SkillRules skillRules)
    {
        AdvancementEngine.skillRules = skillRules;
    }


    static void trainSkill(Actor actor, Skill skill, short points)
    {
        LOG.debug("For actor, " + actor.getName() + ", advancing ranks in " + skill.getName()
                + " by " + points);
        if (!actor.hasSkill(skill)) {
            LOG.info("Initializing new skill before training: " + skill.getName());
            actor.initSkill(skill);
        }
        ManagedSkill managedSkill = actor.skillsTable.get(skill);
        managedSkill.addRanks(points);

        // TODO Check if class skill bonus has been applied.
        if (actor.isClassSkill(skill) && (managedSkill.hasClassAdjustment())) {
            LOG.info("Adding class skill bonus to " + skill.getName());
            managedSkill.setClassSkillAdjustment(skillRules.getClassSkillBonus());
        }
    }
}
