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

import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lostkingdomsfrontier.pfrpg.AdjustableValue;
import org.lostkingdomsfrontier.pfrpg.Adjustment;
import org.lostkingdomsfrontier.pfrpg.AdjustmentLimiter;

/**
 * The AbilityListenerValue class extends AdjustableValue to apply ability modifiers its the
 * baseValue. An example would be saving throws, whose base value is determined by class and level
 * but are modified by an ability modifier score.
 * 
 * A limiter can be established, placing a ceiling on the ability modifier that can be applied. An
 * example use case of this would be armor limiting the DEX bonus that can be applied to AC.
 * 
 * @author bebopjmm
 * @since sprint-0.1
 */
public class AbilityListenerValue extends AdjustableValue
{
    static final Log LOG = LogFactory.getLog(AbilityListenerValue.class);

    Hashtable<Ability, Adjustment> abilityModTable = new Hashtable<Ability, Adjustment>();

    Hashtable<Ability, AdjustmentLimiter> limits = new Hashtable<Ability, AdjustmentLimiter>();


    /**
     * Instantiates a new AbilityListenerValue
     * 
     * @param name identifying name for this new AbilityListenerValue
     * @param baseValue raw score without any adjustments
     * @param ability AbilityValue to associate with this object
     * 
     * @since sprint-0.1
     */
    public AbilityListenerValue(String name, int baseValue, AbilityValue ability)
    {
        super(baseValue);
        setName(name);
        addModifier(ability);
    }


    public synchronized void addModifier(AbilityValue ability)
    {
        if (!abilityModTable.containsKey(ability.getAbility())) {
            Adjustment abilityMod = ability.getModifier();
            abilityModTable.put(ability.getAbility(), abilityMod);
            addAdjustment(abilityMod);
        }
        else {
            LOG.warn("Ignoring attempt to add modifier for AbilityValue already associated: "
                    + ability.getAbility());
        }
    }


    public synchronized void removeModifier(AbilityValue ability)
    {
        if (abilityModTable.containsKey(ability.getAbility())) {
            Adjustment abilityMod = abilityModTable.remove(ability.getAbility());
            removeAdjustment(abilityMod);
        }
        else {
            LOG.warn("Ignoring attempt to remove modifier for AbilityValue not associated: "
                    + ability.getAbility());
        }
    }


    public synchronized void setLimiter(Ability ability, short modifierLimit)
    {
        if (abilityModTable.containsKey(ability)) {
            Adjustment abilityMod = abilityModTable.get(ability);
            removeAdjustment(abilityMod);
            AdjustmentLimiter limiter = new AdjustmentLimiter(abilityMod, modifierLimit);
            addAdjustment(limiter.getLimitedAdjustment());
            limits.put(ability, limiter);
        }
        else {
            LOG.warn("Ignoring attempt to limit modifier for AbilityValue not associated: "
                    + ability);
        }
    }


    public synchronized void removeLimiter(Ability ability)
    {
        if (limits.containsKey(ability)) {
            AdjustmentLimiter limit = limits.remove(ability);
            Adjustment abilityMod = abilityModTable.get(ability);
            removeAdjustment(limit.getLimitedAdjustment());
            addAdjustment(abilityMod);
            limit.clear();
        }
        else {
            LOG.warn("Ignoring attempt to remove modifier limt for AbilityValue not limited: "
                    + ability);
        }
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.rollinitiative.d20.AdjustmentListener#valueChanged(org.rollinitiative.d20.Adjustment)
     */
    @Override
    public synchronized void valueChanged(Adjustment adjustment)
    {
        recalcValue();
    }

}
