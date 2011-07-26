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
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * The AdjustmentChoices class allows for defining a choice set of Adjustments from which a selection may be made.
 * 
 * @author bebopjmm
 *
 * @since sprint-0.3
 */
@SuppressWarnings("serial")
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdjustmentChoicesType", namespace = "java:org.rollinitiative.d20")
public class AdjustmentChoices implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Long persistID;
    
    @XmlID
    @XmlAttribute(required = true)
    private String xmlID;
    
    @XmlAttribute(required=true)
    private String choiceName = new String();
    
    @XmlElement(name = "ChoiceEntry")
    @OneToMany(cascade = CascadeType.ALL)
    private Set<AdjustmentData> options = new HashSet<AdjustmentData>();
    
    public AdjustmentChoices()
    {
        
    }
    
    public AdjustmentChoices(String choiceName)
    {
        this.choiceName = choiceName;
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
    
    public String getXmlID()
    {
        return xmlID;
    }
    
    public void setXmlID(String xmlID)
    {
        this.xmlID = xmlID;
    }

    /**
     * @return the choiceName
     */
    public String getChoiceName()
    {
        return choiceName;
    }

    /**
     * @param choiceName the choiceName to set
     */
    public void setChoiceName(String choiceName)
    {
        this.choiceName = choiceName;
    }

    /**
     * @return the options
     */
    public Set<AdjustmentData> getOptions()
    {
        return options;
    }
    
    
    
    
}
