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
package org.lostkingdomsfrontier.pfrpg.entity;

import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.xml.bind.annotation.*;

import org.lostkingdomsfrontier.pfrpg.entity.talents.SkillsCollection;

/**
 * @author jmccormi
 * 
 * @since sprint-0.2
 */
@SuppressWarnings("serial")
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Template", namespace = "java:org.rollinitiative.d20.entity")
@XmlType(name = "TemplateType", namespace = "java:org.rollinitiative.d20.entity")
public class NonPlayerTemplate extends Actor
{

    @XmlTransient
    @Transient
    private int instanceCount = 0;

    @XmlAttribute(required = true)
    short challengeRating = 0;

    @XmlAttribute(required = true)
    String id = new String("NOT SET");

    @XmlTransient
    @Transient
    SkillsCollection rulesetSkills;

    
    public NonPlayerTemplate()
    {
        super();
    }
    
    public NonPlayerTemplate(String templateName, SkillsCollection rulesetSkills)
    {
        super(templateName,rulesetSkills);
    }

    /**
     * @return the challengeRating
     */
    public short getChallengeRating()
    {
        return challengeRating;
    }


    /**
     * @param challengeRating the challengeRating to set
     */
    public void setChallengeRating(short challengeRating)
    {
        this.challengeRating = challengeRating;
    }


    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }


    /**
     * @param id the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }


    /**
     * @return the rulesetSkills
     */
    public SkillsCollection getRulesetSkills()
    {
        return rulesetSkills;
    }


    /**
     * @param rulesetSkills the rulesetSkills to set
     */
    public void setRulesetSkills(SkillsCollection rulesetSkills)
    {
        this.rulesetSkills = rulesetSkills;
    }


    public NonPlayer makeNewInstance()
    {
        instanceCount++;
        NonPlayer npc = new NonPlayer(this.getName() + "-" + instanceCount, rulesetSkills);
        npc.alignment = this.alignment;
        npc.description = this.description;
        npc.baseScores = this.baseScores;
        npc.initAbilities();
        npc.setRace(this.getRace());
        // npc.setCharacterLevels(this.characterLevels);

        return npc;
    }

}
