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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 * This class provides a data model for a single Adjustment.
 * 
 * @author bebopjmm
 *
 *@since sprint-0.2
 */
@SuppressWarnings("serial")
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdjustmentDataType", namespace = "java:org.rollinitiative.d20")
public class AdjustmentData implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Long persistID;
    
    @XmlAttribute(required=true)
    private String mapping;
    
    @XmlAttribute(required=true)
    AdjustmentCategory category;
    
    @XmlAttribute(required=true)
    short modifier;
    
    @XmlAttribute(required=true)
    String pedigree;
    
    
    public AdjustmentData()
    {
        
    }
    
    public AdjustmentData(String mapping, AdjustmentCategory category,  short modifier, String pedigree)
    {
        this.mapping = mapping;
        this.category = category;
        this.modifier = modifier;
        this.pedigree = pedigree;
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
     * @return the mapping
     */
    public String getMapping()
    {
        return mapping;
    }

    /**
     * @param mapping the mapping to set
     */
    public void setMapping(String mapping)
    {
        this.mapping = mapping;
    }

    /**
     * @return the adjustmentType
     */
    public AdjustmentCategory getAdjustmentType()
    {
        return category;
    }

    /**
     * @param adjustmentType the adjustmentType to set
     */
    public void setAdjustmentType(AdjustmentCategory adjustmentType)
    {
        this.category = adjustmentType;
    }

    /**
     * @return the value
     */
    public short getValue()
    {
        return modifier;
    }

    /**
     * @param value the value to set
     */
    public void setValue(short value)
    {
        this.modifier = value;
    }

    /**
     * @return the pedigree
     */
    public String getPedigree()
    {
        return pedigree;
    }

    /**
     * @param pedigree the pedigree to set
     */
    public void setPedigree(String pedigree)
    {
        this.pedigree = pedigree;
    }

}
