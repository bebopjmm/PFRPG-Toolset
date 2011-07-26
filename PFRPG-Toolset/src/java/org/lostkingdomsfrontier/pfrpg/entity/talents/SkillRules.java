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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * @author bebopjmm
 * 
 * @since sprint-0.2
 */
@SuppressWarnings("serial")
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SkillRulesType", namespace = "java:org.rollinitiative.d20.entity")
public class SkillRules implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Long persistID;

    @XmlElement(name = "ClassSkillBonus", required = true)
    short classSkillBonus = 0;

    @XmlElement(name = "NonClassSkillRankCost", required = true)
    short nonClassSkillRankCost = 0;

    @XmlElement(name = "ClassSkillRankCost", required = true)
    short classSkillRankCost = 0;


    public Long getPersistID()
    {
        return persistID;
    }


    public void setPersistID(Long persistID)
    {
        this.persistID = persistID;
    }


    /**
     * @return the classSkillBonus
     */
    public short getClassSkillBonus()
    {
        return classSkillBonus;
    }


    /**
     * @param classSkillBonus the classSkillBonus to set
     */
    public void setClassSkillBonus(short classSkillBonus)
    {
        this.classSkillBonus = classSkillBonus;
    }


    /**
     * @return the nonClassSkillRankCost
     */
    public short getNonClassSkillRankCost()
    {
        return nonClassSkillRankCost;
    }


    /**
     * @param nonClassSkillRankCost the nonClassSkillRankCost to set
     */
    public void setNonClassSkillRankCost(short nonClassSkillRankCost)
    {
        this.nonClassSkillRankCost = nonClassSkillRankCost;
    }


    /**
     * @return the classSkillRankCost
     */
    public short getClassSkillRankCost()
    {
        return classSkillRankCost;
    }


    /**
     * @param classSkillRankCost the classSkillRankCost to set
     */
    public void setClassSkillRankCost(short classSkillRankCost)
    {
        this.classSkillRankCost = classSkillRankCost;
    }

}
