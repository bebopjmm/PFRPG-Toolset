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
import org.lostkingdomsfrontier.pfrpg.AdjustableValueListener;
import org.lostkingdomsfrontier.pfrpg.Adjustment;
import org.lostkingdomsfrontier.pfrpg.AdjustmentCategory;
import org.lostkingdomsfrontier.pfrpg.AdjustmentListener;
import org.lostkingdomsfrontier.pfrpg.ResourceException;
import org.lostkingdomsfrontier.pfrpg.ResourcePool;

/**
 * The HitPoints class manages the hit points for an entity. Hit points are managed as a
 * ResourcePool with support for an optional reserve. In general, Max hitPoints = base hp + (level *
 * conModifer).
 * 
 * @author bebopjmm
 * 
 */
public class HitPoints implements AdjustableValueListener
{
    static final Log LOG = LogFactory.getLog(HitPoints.class);

    public enum LifeState {
        ALIVE, DYING, DEAD;

        public static LifeState assess(int poolCurrent, int deathThreshold)
        {
            if (poolCurrent > 0)
                return ALIVE;
            else if (poolCurrent <= deathThreshold)
                return DEAD;
            else
                return DYING;
        }
    };

    private ResourcePool hp;

    private ResourcePool rp;

    private ConstitutionAdjustment abilityBonus;

    private AdjustableValue maxHP;

    private short level = 0;

    private short deathThreshold = 0;

    boolean hasReserve = false;


    /**
     * Constructs a new HitPoints object with the designated number of hit points and an optional
     * reserve.
     * 
     * @param hitPoints number of hit points
     * @param hasReserve true if a reserve is to be established
     */
    public HitPoints(short hitPoints, boolean hasReserve)
    {
        maxHP = new AdjustableValue(hitPoints);
        maxHP.setName("maxHP");
        maxHP.subscribe(this);
        hp = new ResourcePool(hitPoints, true);
        hp.setDeficitAllowed(true);
        hp.setSurplusAllowed(true);
        this.hasReserve = hasReserve;
        if (this.hasReserve) {
            rp = new ResourcePool(hitPoints, true);
        }
        else {
            rp = new ResourcePool(0, true);
        }
    }


    /**
     * Constructs a new HitPoints object with 0 hit points. The Constitution AbilityValue is linked
     * to properly modify the hit points based on Constitution value and level. An optional reserve
     * is supported.
     * 
     * @param conVal Constitution AbilityValue to influence hit points
     * @param hasReserve true if a reserve is to be established
     */
    public HitPoints(AbilityValue conVal, boolean hasReserve)
    {

        hp = new ResourcePool(0, true);
        hp.setDeficitAllowed(true);
        hp.setSurplusAllowed(true);
        rp = new ResourcePool(0, true);
        abilityBonus = new ConstitutionAdjustment(conVal);
        maxHP = new AdjustableValue(0);
        maxHP.setName("maxHP");
        maxHP.subscribe(this);
        maxHP.addAdjustment(abilityBonus);
        this.hasReserve = hasReserve;
    }


    /**
     * This method increased the hit point pool by increaseHP, and increments the level value used
     * in calculating the adjustment due to Constitution modifier.
     * 
     * @param increaseHP number of hit points to be added due to level advancement
     */
    public void advanceLevel(int increaseHP)
    {
        LOG.info("Advancing Level: increaseHP = " + increaseHP);
        level++;
        int oldBaseHP = maxHP.getBase();
        maxHP.setBase(oldBaseHP + increaseHP);
        abilityBonus.setLevel(level);
    }


    /**
     * @param hasReserve
     */
    public void setHasReserve(boolean hasReserve)
    {
        if (this.hasReserve == hasReserve) {
            return;
        }

        this.hasReserve = hasReserve;
        if (this.hasReserve) {
            // TODO fill rp to match current hp

        }
        else {
            // TODO empty rp
        }
    }


    /**
     * This method returns true if a reserve is in place, otherwise false.
     * 
     * @return true if a reserve is in place, otherwise false.
     */
    public boolean hasReserve()
    {
        return this.hasReserve;
    }


    /**
     * This method returns the current number of hit points.
     * 
     * @return current number of hit points.
     */
    public int getCurrentHP()
    {
        return hp.getPoolCurrent();
    }


    /**
     * This method returns the maximum number of hit points.
     * 
     * @return maximum number of hit points
     */
    public int getMaxHP()
    {
        return hp.getPoolMax();
    }


    /**
     * This method returns the current number of reserve hit points.
     * 
     * @return current number of reserve hit points.
     */
    public int getReserveHP()
    {
        return rp.getPoolCurrent();
    }


    /**
     * @return the deathThreshold
     */
    public int getDeathThreshold()
    {
        return deathThreshold;
    }


    /**
     * @param deathThreshold the deathThreshold to set
     */
    public void setDeathThreshold(short deathThreshold)
    {
        if (deathThreshold > 0) {
            LOG.warn("Ignoring attempt to set death threshold > 0");
            return;
        }
        this.deathThreshold = deathThreshold;
    }


    /**
     * This method inflicts the designated amount of hit point damage to the actor.
     * 
     * @param hp amount of damage to inflict
     * @return LifeState following the damage.
     */
    public LifeState damage(int hp)
    {
        synchronized (this.hp) {
            try {
                this.hp.expend(hp);
                return LifeState.assess(this.hp.getPoolCurrent(), this.deathThreshold);
            } catch (ResourceException ex) {
                return LifeState.DEAD;
            }
        }
    }


    /**
     * This method cures the designated amount of hit points to the actor. A DEAD actor cannot
     * receive healing.
     * 
     * @param hp amount of curing to apply
     * @return LifeState following the curing
     */
    public LifeState heal(short hp)
    {
        if (LifeState.assess(this.hp.getPoolCurrent(), deathThreshold) == LifeState.DEAD) {
            LOG.info("Cannot heal a dead creature");
            return LifeState.DEAD;
        }
        synchronized (this.hp) {
            this.hp.replenish(hp);
            return LifeState.assess(this.hp.getPoolCurrent(), this.deathThreshold);
        }
    }


    /**
     * This method is triggered whenever maxHP is altered. It triggers the recalculation of pool
     * levels.
     * 
     * @seeorg.rollinitiative.d20.AdjustableValueListener#valueChanged(org.rollinitiative.d20. 
     *                                                                                         AdjustableValue
     *                                                                                         )
     */

    @Override
    public void valueChanged(AdjustableValue adjustable)
    {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Recalculating hpPool due to change in maxHP.");
        }
        try {
            recalcMax();
        } catch (ResourceException ex) {
            LOG.error("Failure to update the hp/rp pools!", ex);
        }
    }


    /**
     * This method updates the max pool sizes of hp and rp based on changes in the maxHP
     * AdjustableValue.
     */
    protected void recalcMax() throws ResourceException
    {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Assessing maxHP: New max=" + maxHP.getCurrent() + ", old max="
                    + hp.getPoolMax());
        }
        int delta = maxHP.getCurrent() - hp.getPoolMax();
        hp.updateMax(delta, true);
        if (hasReserve) {
            rp.updateMax(delta, true);
        }
    }

    /**
     * The ConstitutionAdjustment calculates the hp adjustment due to level and CON ability modifier
     * where adjustment = level * abilityMod
     * 
     * @author bebopjmm
     * 
     */
    class ConstitutionAdjustment extends Adjustment implements AdjustmentListener
    {
        Adjustment conAdj_;


        public ConstitutionAdjustment(AbilityValue conVal)
        {
            super(AdjustmentCategory.INHERENT, (short)0, "hitPoints.CON");
            conAdj_ = conVal.getModifier();
            conAdj_.subscribe(this);
            this.setValue((short)(level * conAdj_.getValue()));
        }


        /**
         * This method assigns the value of the adjustment based on newLevel and the current CON
         * adjustment. It should trigger the valueChanged() method on subscribed maxHP
         * AdjustableValues.
         * 
         * @param newLevel total levels for CON adjustment.
         */
        public void setLevel(short newLevel)
        {
            this.setValue((short)(newLevel * conAdj_.getValue()));
        }


        /**
         * This will be invoked when the CON modifier changes.
         * 
         * @see org.lostkingdomsfrontier.pfrpg.AdjustmentListener#valueChanged(org.lostkingdomsfrontier.pfrpg.Adjustment)
         */
        @Override
        public void valueChanged(Adjustment adjustment)
        {
            LOG.debug("Updating CON adjustment to hp = level(" + level + ") * modifier("
                    + adjustment.getValue() + ")");
            this.setValue((short)(level * adjustment.getValue()));
        }
    }
}
