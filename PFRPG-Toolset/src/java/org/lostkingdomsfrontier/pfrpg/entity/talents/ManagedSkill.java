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
package org.lostkingdomsfrontier.pfrpg.entity.talents;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lostkingdomsfrontier.pfrpg.AdjustableValue;
import org.lostkingdomsfrontier.pfrpg.Adjustment;
import org.lostkingdomsfrontier.pfrpg.AdjustmentCategory;
import org.lostkingdomsfrontier.pfrpg.entity.AbilityListenerValue;
import org.lostkingdomsfrontier.pfrpg.entity.AbilityValue;

/**
 * A ManagedSkill is and individual instantiation of a Skill for an Actor realized as an
 * AbilityListenerValue. All adjustments to the skill are tracked by this class.
 * 
 * @author bebopjmm
 * @since sprint-0.2
 */
public class ManagedSkill
{
    static final Log LOG = LogFactory.getLog(ManagedSkill.class);
    
    public static final String RANKS_PEDIGREE = "skillRanks";
    
    public static final String CLASS_PEDIGREE = "classSkillBonus";

    Skill skill;

    AbilityListenerValue skillValue;

    Adjustment ranksAdjustment;

    Adjustment classSkillAdjustment = null;


    /**
     * @param skill
     * @param keyAbility
     * 
     * @since sprint-0.2
     */
    public ManagedSkill(Skill skill, AbilityValue keyAbility)
    {
        this.skill = skill;
        skillValue = new AbilityListenerValue(skill.getId(), 0, keyAbility);
        ranksAdjustment = new Adjustment(AdjustmentCategory.INHERENT, (short)0, RANKS_PEDIGREE);
        skillValue.addAdjustment(ranksAdjustment);
        LOG.debug("Ability adjusted score for initialized skill, " + this.skill.getId() + " = "
                + skillValue.getCurrent());
    }


    /**
     * @return true if a class skill adjustment has been applied to this managed skill.
     * 
     * @since sprint-0.2
     */
    public boolean hasClassAdjustment()
    {
        return classSkillAdjustment != null;
    }


    /**
     * @param bonus class skill bonus to be applied as an adjustment. If an adjustment is already in
     *            place, this one will supersede it.
     * 
     * @since sprint-0.2
     */
    public void setClassSkillAdjustment(short bonus)
    {
        if (hasClassAdjustment()) {
            classSkillAdjustment.setValue(bonus);
        }
        else {
            classSkillAdjustment = new Adjustment(AdjustmentCategory.INHERENT, bonus, CLASS_PEDIGREE);
            skillValue.addAdjustment(classSkillAdjustment);
        }
    }


    /**
     * @return the AdjustableValue this object manages
     * 
     * @since sprint-0.2
     */
    public AdjustableValue getSkillValue()
    {
        return skillValue;
    }
    
    /**
     * @return the current modifier for this skill, taking all adjustments into account.
     * 
     * @since sprint-0.2
     */
    public short getCurrentModifier()
    {
        return (short)skillValue.getCurrent();
    }


    /**
     * @param points additional ranks to add to this skill
     * 
     * @since sprint-0.2
     */
    public void addRanks(short points)
    {
        short ranks = (short)(ranksAdjustment.getValue() + points);
        ranksAdjustment.setValue(ranks);
    }
}
