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
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class manages the collection of skills supported by a ruleset.
 * 
 * @author bebopjmm
 * 
 * @since sprint-0.2
 */
@SuppressWarnings("serial")
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SkillCollection", namespace = "java:org.rollinitiative.d20.entity")
@XmlType(name = "SkillCollectionType", namespace = "java:org.rollinitiative.d20.entity")
public class SkillsCollection implements Serializable
{
    static final Log LOG = LogFactory.getLog(SkillsCollection.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Long persistID;

    @OneToOne
    @XmlElement(name = "SkillRules", required = true)
    SkillRules rules = new SkillRules();

    @OneToMany(cascade = CascadeType.ALL)
    @XmlElement(name = "Skill")
    List<Skill> skills = new ArrayList<Skill>();


    /**
     * Instantiates a new skill collection with no managed skills or class skills.
     */
    public SkillsCollection()
    {

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
     * This method returns the current skills managed in the collection.
     * 
     * @return array of skills managed in the collection
     */
    public List<Skill> getSkills()
    {
        return skills;
    }


    public boolean hasSkill(Skill skill)
    {
        synchronized (skills) {
            return skills.contains(skill);
        }
    }


    /**
     * @return the rules
     */
    public SkillRules getRules()
    {
        return rules;
    }


    /**
     * @param rules the rules to set
     */
    public void setRules(SkillRules rules)
    {
        this.rules = rules;
    }


    /**
     * @return the classSkillBonus
     */
    public int getClassSkillBonus()
    {
        return rules.classSkillBonus;
    }

}
