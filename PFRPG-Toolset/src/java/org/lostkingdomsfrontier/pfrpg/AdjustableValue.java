/**
 * Project: PFRPG-Toolset
 * Created: Oct 21, 2006 by bebopJMM
 *
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
package org.lostkingdomsfrontier.pfrpg;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * AdjustableValue manages a single score, such as an ability or hitPoints. It tracks all
 * adjustments, however temporary or permanent, against the base score. It notifies all subscribers
 * to changes in the value being managed.
 * 
 * @author bebopjmm
 * @since sprint-0.1
 */
public class AdjustableValue implements AdjustmentListener
{
    static final Log LOG = LogFactory.getLog(AdjustableValue.class);

    public static final int DEFAULT_VALUE = 0;

    /**
     * The baseValue is the raw score without any modifiers.
     */
    private int baseValue;

    /**
     * The currentValue is the calculated score with all currently applicable modifiers
     */
    private int currentValue;

    /**
     * Optional identifying name for the adjustable value (useful for debugging)
     */
    private String name = "";

    /**
     * The current set of adjustments impacting this adjustable value.
     */
    List<Adjustment> adjustments;

    /**
     * Subscribers to changes in currentValue
     */
    List<AdjustableValueListener> subscribers;


    /**
     * Instantiates a new AdjustableValue, setting both base and current values to the provided
     * baseValue.
     * 
     * @param baseValue
     * @since sprint-0.1
     */
    public AdjustableValue(int baseValue)
    {
        this.baseValue = baseValue;
        this.currentValue = baseValue;
        adjustments = new ArrayList<Adjustment>();
        subscribers = new ArrayList<AdjustableValueListener>();
    }


    /**
     * Returns the identifying name of this adjustable value.
     * 
     * @return the identifying name
     * @since sprint-0.1
     */
    public String getName()
    {
        return name;
    }


    /**
     * Assigns an identifying name to this adjustable value.
     * 
     * @param name the name to set
     * @since sprint-0.1
     */
    public void setName(String name)
    {
        this.name = name;
    }


    /**
     * Returns the base score with no adjustments applied.
     * 
     * @return the baseValue
     * @since sprint-0.1
     */
    public synchronized int getBase()
    {
        return baseValue;
    }


    /**
     * Assigns a base score to which adjustments will be applied. This will trigger a recalculation
     * and notification to subscribers.
     * 
     * @param baseValue the baseValue to set
     * @since sprint-0.1
     */
    public synchronized void setBase(int baseValue)
    {
        this.baseValue = baseValue;
        recalcValue();
        notifyOfChange();
    }


    /**
     * Returns the calculated value (base + adjustments).
     * 
     * @return the current ability value
     * @since sprint-0.1
     */
    public synchronized int getCurrent()
    {
        return currentValue;
    }


    /**
     * Adds the adjustment to the value calculation set, updating the currentValue and notifying all
     * subscribed listeners. No action is taken if the provided Adjustment is already part of the
     * list.
     * 
     * @param adjustment new Adjustment to include in value calculation
     * @since sprint-0.1
     */
    public synchronized void addAdjustment(Adjustment adjustment)
    {
        if (adjustments.contains(adjustment)) {
            LOG.warn(name + ": Attempt to add duplicate ability adjustment: "
                    + adjustment.getPedigree());
            return;
        }
        LOG.debug(name + ": Adding Adjustment: " + adjustment.getPedigree() + " of value: "
                + adjustment.getValue());
        adjustments.add(adjustment);
        adjustment.subscribe(this);
        currentValue += adjustment.getValue();
        LOG.debug(name + ": Current value =" + currentValue);
        notifyOfChange();
    }


    /**
     * Removes the adjustment from the value calculation set, updating the currentValue and
     * notifying all subscribed listeners. No action is taken if the provide Adjustment is not part
     * of the list.
     * 
     * @param adjustment Adjustment to be removed from value calculation.
     * @since sprint-0.1
     */
    public synchronized void removeAdjustment(Adjustment adjustment)
    {
        if (!adjustments.contains(adjustment)) {
            LOG.warn(name + ": Attempt to remove adjustment not associated to ability: "
                    + adjustment.getPedigree());
            return;
        }
        LOG.debug(name + ": Removing Adjustment: " + adjustment.getPedigree() + " of value: "
                + adjustment.getValue());
        adjustments.remove(adjustment);
        adjustment.unsubscribe(this);
        currentValue -= adjustment.getValue();
        LOG.debug(name + ": Current value =" + currentValue);
        notifyOfChange();
    }


    /**
     * Returns the list of Adjustments currently affecting the base score.
     * 
     * @return the list of adjustments
     * @since sprint-0.1
     */
    public List<Adjustment> getAdjustments()
    {
        return adjustments;
    }

    
    /**
     * Add the listener as a subscriber to changes in the AdjustableValue if it hasn't already
     * subscribed.
     * 
     * @param listener AdjustableValueListener to add
     * @since sprint-0.1
     */
    public void subscribe(AdjustableValueListener listener)
    {
        synchronized (subscribers) {
            if (!subscribers.contains(listener)) {
                subscribers.add(listener);
            }
        }
    }


    /**
     * Removes the listener as a subscriber to changes in the AdjustableValue.
     * 
     * @param listener AdjustableValueListener to remove
     * @since sprint-0.1
     */
    public void unsubscribe(AdjustableValueListener listener)
    {
        synchronized (subscribers) {
            if (subscribers.contains(listener)) {
                subscribers.remove(listener);
            }
        }
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.rollinitiative.d20.AdjustmentListener#valueChanged(org.rollinitiative.d20.Adjustment)
     * 
     * @since sprint-0.1
     */
    @Override
    public synchronized void valueChanged(Adjustment adjustment)
    {
        // Recalculate the currentValue and notify if different than old value.
        int oldVal = currentValue;
        recalcValue();
        if (oldVal != currentValue) {
            notifyOfChange();
        }
        else {
            LOG.debug(name + ": No change in currentValue, not notifying subscribers");
        }
    }


    public static Adjustment findAdjustmentByPedigree(AdjustableValue value, String adjustmentPedigree)
    {
        synchronized (value) {
            for (Adjustment adjustment : value.getAdjustments()) {
                if (adjustment.getPedigree().equals(adjustmentPedigree))
                    return adjustment;
            }
        }
        return null;
    }


    /**
     * Recalculates the current value (baseValue + all adjustments)
     * 
     * @since sprint-0.1
     */
    protected void recalcValue()
    {
        LOG.debug(name + ": recalculating current value: baseValue + sum of adjustments");
        currentValue = baseValue;
        for (Adjustment adjustment : adjustments) {
            currentValue += adjustment.getValue();
        }
    }


    /**
     * Notifies all subscribers of a change in the AdjustableValue via their valueChanged() method.
     * 
     * {@link AdjustableValueListener#valueChanged(AdjustableValue)}
     * 
     * @since sprint-0.1
     */
    protected void notifyOfChange()
    {
        synchronized (subscribers) {
            LOG.debug(name + ": Notifying subcribers of currentValue change, totalSubscribers = "
                    + subscribers.size());
            for (AdjustableValueListener subscriber : subscribers) {
                subscriber.valueChanged(this);
            }
        }
    }
}
