/**
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

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This association class links the selection from a specific AdjustmentChoices object.
 * 
 * @author bebopjmm
 *
 * @since sprint-0.3
 */
@SuppressWarnings("serial")
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChoiceSelectionType", namespace = "java:org.rollinitiative.d20")
public class AdjustmentChoiceSelection implements Serializable
{
    static final Log LOG = LogFactory.getLog(AdjustmentChoiceSelection.class);
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Long persistID;
    
    @XmlIDREF
    @ManyToOne(cascade=CascadeType.ALL)
    private AdjustmentChoices choices;
    
    @XmlAttribute(required=true)
    private String selectionPedigree;
    
    
    @XmlTransient
    @ManyToOne(cascade=CascadeType.ALL)
    private AdjustmentData selection = null;
    
    public AdjustmentChoiceSelection()
    {
        
    }
    
    public AdjustmentChoiceSelection(AdjustmentChoices choices)
    {
        this.choices = choices;
    }
    
    /**
     * @return the persistID
     */
    public Long getPersistID()
    {
        return persistID;
    }

    /**
     * @param persistID the persistID to set
     */
    public void setPersistID(Long persistID)
    {
        this.persistID = persistID;
    }

    /**
     * @return the choices
     */
    public AdjustmentChoices getChoices()
    {
        return choices;
    }

    /**
     * @param choices the choices to set
     */
    public void setChoices(AdjustmentChoices choices)
    {
        this.choices = choices;
    }
    
    public boolean hasChoosen()
    {
        return selection != null;
    }
    
    public String getSelectionPedigree()
    {
        return selectionPedigree;
    }
    
    public AdjustmentData getSelection()
    {
        return selection;
    }
    
    /**
     * 
     * @param option
     */
    public boolean makeSelection(AdjustmentData option)
    {
        if(choices.getOptions().contains(option)) 
        {

            selectionPedigree = option.getPedigree();
            selection = option;
            LOG.info("Assigned selection ["+selectionPedigree +"] for choice set: " + choices.getChoiceName());
            return true;
        }
        else {
            LOG.warn("Selection [" + option.getPedigree() + "] is not part of choice set: " + choices.getChoiceName());
            return false;
        }
    }
    
}
