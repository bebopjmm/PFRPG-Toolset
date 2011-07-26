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
import org.lostkingdomsfrontier.pfrpg.AdjustableValue;
import org.lostkingdomsfrontier.pfrpg.Adjustment;
import org.lostkingdomsfrontier.pfrpg.AdjustmentCategory;

/**
 * The AbilityValue extends the basic AdjustableValue in order to provide event-driven notification
 * to subscribers of changes in the Ability modifier due to current Ability value.
 * 
 * @author bebopjmm
 * @since sprint-0.1
 */
public class AbilityValue extends AdjustableValue
{
    static final Log LOG = LogFactory.getLog(AbilityValue.class);

    public static final short DEFAULT_VALUE = 10;

    /**
     * Ability modifier adjustment
     */
    Adjustment abilityMod;

    /**
     * Ability that this AbilityValue is associated with.
     */
    Ability ability;


    /**
     * Initializes an AbilityValue to have the specified baseValue and no subscribers.
     * 
     * @param baseValue base value for the AdjustableValue
     * @param ability Ability that this AbilityValue represents
     */
    public AbilityValue(short baseValue, Ability ability)
    {
        super(baseValue);
        this.ability = ability;
        this.setName(ability.toString());
        abilityMod = new Adjustment(AdjustmentCategory.INHERENT,
                Ability.getModifier((short)this.getCurrent()), "ability." + ability.name());
    }


    /**
     * Returns the Ability associated with this AbilityValue
     * 
     * @return the associated Ability.
     * @since sprint-0.1
     */
    public Ability getAbility()
    {
        return ability;
    }


    /**
     * Returns the current ability modifier adjustment for this AbilityValue.
     * 
     * @return the ability modifier adjustment.
     * @since sprint-0.1
     */
    public Adjustment getModifier()
    {
        return abilityMod;
    }


    /**
     * Assigns a new base value, which triggers update to the ability modifier Adjustment and
     * subscriber notification.
     * 
     * @param baseValue the base value to set
     * @since sprint-0.1
     */
    @Override
    public synchronized void setBase(int baseValue)
    {
        super.setBase(baseValue);
        updateAdjustment();
    }


    /**
     * Returns the ability modifier value for the current ability value.
     * 
     * @return current ability modifier value.
     * @since sprint-0.1
     */
    public int getAbilityModifier()
    {
        return abilityMod.getValue();
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.rollinitiative.d20.AdjustableValue#addAdjustment(org.rollinitiative.d20.Adjustment)
     */
    @Override
    public synchronized void addAdjustment(Adjustment adjustment)
    {
        LOG.debug("Adding new Adjustment: " + adjustment.getPedigree());
        super.addAdjustment(adjustment);
        updateAdjustment();
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.rollinitiative.d20.AdjustableValue#removeAdjustment(org.rollinitiative.d20.Adjustment)
     */
    @Override
    public synchronized void removeAdjustment(Adjustment adjustment)
    {
        LOG.debug("Removing Adjustment: " + adjustment.getPedigree());
        super.removeAdjustment(adjustment);
        updateAdjustment();

    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.rollinitiative.d20.AdjustmentListener#valueChanged(org.rollinitiative.d20.Adjustment)
     */
    @Override
    public void valueChanged(Adjustment adjustment)
    {
        super.valueChanged(adjustment);
        updateAdjustment();
    }


    /**
     * Updates the ability modifier value, which notifies all subscribers.
     * 
     * @since sprint-0.1
     */
    void updateAdjustment()
    {
        abilityMod.setValue(Ability.getModifier((short)this.getCurrent()));
        LOG.debug("New abilityMod = " + abilityMod.getValue());
    }

}
