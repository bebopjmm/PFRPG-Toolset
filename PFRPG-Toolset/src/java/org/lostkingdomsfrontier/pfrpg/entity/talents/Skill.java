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
package org.lostkingdomsfrontier.pfrpg.entity.talents;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.lostkingdomsfrontier.pfrpg.entity.Ability;

/**
 * @author bebopjmm
 * 
 * @since sprint-0.2
 */
@SuppressWarnings("serial")
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SkillType", namespace = "java:org.rollinitiative.d20.entity")
public class Skill implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Long persistID;

    @XmlAttribute(required = true)
    private String id = new String("NOT SET");

    @XmlElement(name = "Name", required = true)
    private String name = "NOT SET";

    @XmlElement(name = "KeyAbility", required = true)
    private Ability keyAbility;

    @XmlAttribute(required = true)
    private boolean useUntrained;

    @XmlAttribute(required = true)
    private short armorPenalty;

    
    public Skill()
    {
        
    }
    
    public Skill(String name, Ability keyAbility, boolean useUntrained, short armorPenalty)
    {
        this.name = name;
        this.keyAbility = keyAbility;
        this.useUntrained = useUntrained;
        this.armorPenalty = armorPenalty;
    }

    /**
     * @return the id of the skill
     */
    public String getId()
    {
        return id;
    }


    /**
     * @param id new ID attribute for the skill
     */
    void setId(String id)
    {
        this.id = id;
    }


    public Long getPersistID()
    {
        return persistID;
    }


    public void setPersistID(Long persistID)
    {
        this.persistID = persistID;
    }


    /**
     * @return the descriptive name of the skill
     */
    public String getName()
    {
        return name;
    }


    /**
     * @param name new name attribute for the skill
     */
    public void setName(String name)
    {
        this.name = name;
    }


    /**
     * @return the keyAbility associated with this skill
     */
    public Ability getKeyAbility()
    {
        return keyAbility;
    }


    /**
     * @param keyAbility new keyAbility for this skill
     */
    public void setKeyAbility(Ability keyAbility)
    {
        this.keyAbility = keyAbility;
    }


    /**
     * @return the untrained
     */
    public boolean isUntrained()
    {
        return useUntrained;
    }


    /**
     * @param untrained the untrained to set
     */
    public void setUntrained(boolean untrained)
    {
        this.useUntrained = untrained;
    }


    /**
     * @return the armorPenalty
     */
    public short getArmorPenalty()
    {
        return armorPenalty;
    }


    /**
     * @param armorPenalty the armorPenalty to set
     */
    public void setArmorPenalty(short armorPenalty)
    {
        this.armorPenalty = armorPenalty;
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Skill)) {
            return false;
        }
        Skill target = (Skill) obj;
        if (this.id.equals(target.id)) {
            return true;
        }
        else {
            return false;
        }
    }

}
