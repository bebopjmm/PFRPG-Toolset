/**
 * Project: PFRPG-Toolset
 * Created: Sep 4, 2007 by bebopJMM
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
 * The Adjustment captures a singular alteration to a modifiable score (AdjustableValue), such as an
 * Ability, Skill, or Save. Any change in the value of the Adjustment triggers a notification event
 * to all subscribed listeners.
 * 
 * @author bebopjmm
 * @since sprint-0.1
 */
public class Adjustment
{

    static final Log LOG = LogFactory.getLog(Adjustment.class);

    /**
     * Listeners to changes in the Adjustment
     */
    List<AdjustmentListener> subscribers;

    AdjustmentData data = new AdjustmentData();

    /**
     * Upper limit that the adjustment may take
     */
    short limit = Short.MAX_VALUE;


    /**
     * Instantiates a new Adjustment with an empty list of subscribers
     * 
     * @param type AdjustmentType for the new Adjustment
     * @param value initial value for the new Adjustment
     * @param pedigree source of this new Adjustment
     * @since sprint-0.1
     */
    public Adjustment(AdjustmentCategory type, short value, String pedigree)
    {
        this.data.setAdjustmentType(type);
        this.data.setValue(value);
        this.data.setPedigree(pedigree);
        subscribers = new ArrayList<AdjustmentListener>();
    }
    
    public Adjustment(AdjustmentData data)
    {
        this.data = data;
        subscribers = new ArrayList<AdjustmentListener>();
    }


    /**
     * Returns the current value of the adjustment, which may be limited.
     * 
     * @return the current value of the Adjustment
     * @since sprint-0.1
     */
    public short getValue()
    {
        if (data.getValue() <= limit)
            return data.getValue();
        else
            return limit;
    }


    /**
     * This method updates the value of this Adjustment and notifies all subscribers of the change.
     * 
     * @param value new value for the adjustment
     * @since sprint-0.1
     */
    public void setValue(short value)
    {
        this.data.setValue(value);
        notifyOfChange();
    }


    /**
     * Returns the current limit being applied to the adjustment. Integer.MAX_VALUE is essentially
     * no limit.
     * 
     * @return the current limit being applied to the adjustment.
     * @since sprint-0.1
     */
    public int getLimit()
    {
        return limit;
    }


    /**
     * This method updates the limit of this Adjustment and notifies all subscribers of the change.
     * 
     * @param limit new limit to be applied
     * @since sprint-0.1
     */
    public void setLimit(short limit)
    {
        this.limit = limit;
        notifyOfChange();
    }


    /**
     * Returns the classification type of the adjustment.
     * 
     * @return the AdjustementCategory of the adjustment
     * @since sprint-0.1
     */
    public AdjustmentCategory getCategory()
    {
        return data.getAdjustmentType();
    }


    /**
     * Returns the pedigree source for the adjustment.
     * 
     * @return the pedigree of the Adjustment
     * @since sprint-0.1
     */
    public String getPedigree()
    {
        return data.getPedigree();
    }

    
    

    /**
     * @return the mapping descriptor
     */
    public String getMapping()
    {
        return data.getMapping();
    }


    /**
     * Add the listener as a subscriber to changes in the adjustment if it hasn't already
     * subscribed.
     * 
     * @param listener AdjustmentListener to add.
     * @since sprint-0.1
     */
    public synchronized void subscribe(AdjustmentListener listener)
    {
        if (!subscribers.contains(listener)) {
            subscribers.add(listener);
        }
    }


    /**
     * Removes the listener as a subscriber to changes in the adjustment
     * 
     * @param listener AdjustmentListener to remove
     * @since sprint-0.1
     */
    public synchronized void unsubscribe(AdjustmentListener listener)
    {
        if (subscribers.contains(listener)) {
            subscribers.remove(listener);
        }
    }


    /**
     * This method notifies all AdjustmentListener subscribers of a change in the Adjustment's value
     * via the subcriber's valueChanged() method.
     * 
     * {@link AdjustmentListener#valueChanged(Adjustment)}
     * 
     * @since sprint-0.1
     */
    protected void notifyOfChange()
    {
        synchronized (subscribers) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(data.getPedigree() + " notifying subscribers, total = " + subscribers.size());
            }
            for (AdjustmentListener listener : subscribers) {
                listener.valueChanged(this);
            }
        }
    }

}
