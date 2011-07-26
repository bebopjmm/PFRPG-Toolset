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
 * The RaceSubType defines abilities that are inherent to a specific racial subtype in the game,
 * currently it only maintains a display name.
 * 
 * TODO allow for talents and modifiers to be associated with the class.
 * 
 * @author bebopjmm
 * 
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RaceSubType", namespace = "java:org.rollinitiative.d20.entity")
@SuppressWarnings("serial")
public class RacialSubtype implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Long persistID;

    @XmlAttribute(required = true)
    private String name;


    public RacialSubtype()
    {

    }


    public RacialSubtype(String name)
    {
        this.name = name;
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
     * @return the name
     */
    public String getName()
    {
        return name;
    }


    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

}
