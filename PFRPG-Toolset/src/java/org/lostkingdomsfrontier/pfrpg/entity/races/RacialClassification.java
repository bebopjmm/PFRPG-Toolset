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
package org.lostkingdomsfrontier.pfrpg.entity.races;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.*;

/**
 * The RacialClassification specifies the baseType and any appropriate subTypes for an individual
 * Race.
 * 
 * @author bebopjmm
 * 
 */
@SuppressWarnings("serial")
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClassificationType", namespace = "java:org.rollinitiative.d20.entity")
public class RacialClassification implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Long persistID;

    @XmlAttribute(required = true)
    private String baseType;

    @OneToMany(cascade = CascadeType.ALL)
    @XmlElement(name = "SubType")
    private Set<RacialSubtype> subTypes;


    public RacialClassification()
    {

    }


    public RacialClassification(String baseType)
    {
        this.baseType = baseType;
    }


    public Long getPersistID()
    {
        return persistID;
    }


    public void setPersistID(Long persistID)
    {
        this.persistID = persistID;
    }


    public String getBaseType()
    {
        return baseType;
    }


    public void setBaseType(String baseType)
    {
        this.baseType = baseType;
    }


    public Set<RacialSubtype> getSubTypes()
    {
        return subTypes;
    }


    public void setSubTypes(Set<RacialSubtype> subTypes)
    {
        this.subTypes = subTypes;
    }


    public void addSubType(RacialSubtype subtype)
    {
        if (subTypes == null) {
            subTypes = new HashSet<RacialSubtype>();
        }
        subTypes.add(subtype);
    }

}
