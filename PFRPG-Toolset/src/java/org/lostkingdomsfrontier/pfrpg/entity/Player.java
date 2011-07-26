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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.lostkingdomsfrontier.pfrpg.entity.talents.SkillsCollection;

/**
 * A Player is an actor that is controlled or directed by a human player.
 * 
 * @author bebopjmm
 * @since sprint-0.1
 */
@SuppressWarnings("serial")
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Player", namespace = "java:org.rollinitiative.d20.entity")
@XmlType(name = "PlayerType", namespace = "java:org.rollinitiative.d20.entity")
public class Player extends Actor
{

    @XmlAttribute(required = true)
    String playerName;

    @XmlAttribute(required = true)
    int xp = 0;

    @Transient
    @XmlTransient
    String classID;

    @Transient
    @XmlTransient
    String classDescript;


    public Player()
    {
        this("NOT-SET", "", null);
    }


    public Player(String playerName)
    {
        this(playerName, "", null);
    }


    public Player(String playerName, String characterName, SkillsCollection rulesetSkills)
    {
        super(characterName, rulesetSkills);
        this.playerName = playerName;
        // this.hp.setHasReserve(true);
    }


    /**
     * @return the playerName
     * @since sprint-0.1
     */
    public String getPlayerName()
    {
        return playerName;
    }


    /**
     * @param playerName the playerName_ to set
     * @since sprint-0.1
     */
    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }


    /**
     * @return the xp
     * @since sprint-0.1
     */
    public int getXp()
    {
        return xp;
    }


    /**
     * @param xp the xp to set
     * @since sprint-0.1
     */
    public void setXp(int xp)
    {
        this.xp = xp;
    }


    /**
     * @return the classID
     * @since sprint-0.1
     */
    public String getClassID()
    {
        return classID;
    }


    /**
     * @param classID the classID to set
     * @since sprint-0.1
     */
    public void setClassID(String classID)
    {
        this.classID = classID;
    }


    /**
     * @return the classDescript
     * @since sprint-0.1
     */
    public String getClassDescript()
    {
        return classDescript;
    }


    /**
     * @param classDescript the classDescript to set
     * @since sprint-0.1
     */
    public void setClassDescript(String classDescript)
    {
        this.classDescript = classDescript;
    }

}
