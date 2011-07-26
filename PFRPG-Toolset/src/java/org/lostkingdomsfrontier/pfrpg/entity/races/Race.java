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
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.*;

import org.lostkingdomsfrontier.pfrpg.entity.Movement;
import org.lostkingdomsfrontier.pfrpg.entity.Size;

/**
 * @author bebopjmm
 * 
 */
@SuppressWarnings("serial")
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Race", namespace = "java:org.rollinitiative.d20.entity")
@XmlType(name = "RaceType", namespace = "java:org.rollinitiative.d20.entity")
public class Race implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Long persistID;

    @XmlAttribute(required = true)
    private String name;

    @XmlAttribute(required = true)
    private Size size;

    @XmlID
    @XmlAttribute(required = true)
    private String id;

    @OneToOne(cascade = CascadeType.ALL)
    @XmlElement(name = "Classification", required = true)
    private RacialClassification classification;

    @Embedded
    @XmlElement(name = "Movement", required = true)
    private Movement movement;

    @Embedded
    @XmlElement(name = "Advancement", required = true)
    private Advancement advancement;


    // private CombatOptions combatOptions;

    public Race()
    {
        this.name = "";
        this.id = new String("default");
        this.movement = new Movement();
        this.classification = new RacialClassification();
        // this.combatOptions = new CombatOptions();
    }


    public Race(String name, Size size, RacialClassification classification, Movement movement,
            Race.Advancement advancement)
    {
        this.name = name;
        this.size = size;
        this.classification = classification;
        this.movement = movement;
        this.advancement = advancement;
    }


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }


    public Size getSize()
    {
        return size;
    }


    public void setSize(Size size)
    {
        this.size = size;
    }


    public String getId()
    {
        return id;
    }


    public void setId(String id)
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


    public RacialClassification getClassification()
    {
        return classification;
    }


    public void setClassification(RacialClassification classification)
    {
        this.classification = classification;
    }


    public Movement getMovement()
    {
        return movement;
    }


    public void setMovement(Movement movement)
    {
        this.movement = movement;
    }


    /**
     * @return the advancement
     */
    public Advancement getAdvancement()
    {
        return advancement;
    }


    /**
     * @param advancement the advancement to set
     */

    public void setAdvancement(Advancement advancement)
    {
        this.advancement = advancement;
    }

    // @XmlElement(name = "CombatOptions")
    // public CombatOptions getCombatOptions()
    // {
    // return combatOptions;
    // }
    //
    //
    // public void setCombatOptions(CombatOptions combatOptions)
    // {
    // this.combatOptions = combatOptions;
    // }

    // @XmlType(name = "CombatOptionsType", namespace = "java:org.rollinitiative.d20.entity")
    // public static class CombatOptions
    // {
    // private int reach;
    //
    // ActionOptions actionOptions;
    //
    //
    // public int getReach()
    // {
    // return reach;
    // }
    //
    //
    // @XmlAttribute(name = "reach", required = true)
    // public void setReach(int reach)
    // {
    // this.reach = reach;
    // }
    //
    //
    // /**
    // * @return the actionOptions
    // */
    // public ActionOptions getActionOptions()
    // {
    // return actionOptions;
    // }
    //
    //
    // /**
    // * @param actionOptions the actionOptions to set
    // */
    // @XmlElement(name = "ActionOptions")
    // public void setActionOptions(ActionOptions actionOptions)
    // {
    // this.actionOptions = actionOptions;
    // }
    //
    // }

    @XmlType(name = "RaceLevelListType", namespace = "java:org.rollinitiative.d20.entity")
    @Embeddable
    public static class Advancement
    {

        @OneToMany(cascade = CascadeType.ALL)
        List<RacialLevel> levels = new ArrayList<RacialLevel>();


        /**
         * @return the levels
         */
        public List<RacialLevel> getLevels()
        {
            return levels;
        }


        /**
         * @param levels the levels to set
         */
        @XmlElement(name = "RacialLevel", required = true)
        public void setLevels(List<RacialLevel> levels)
        {
            this.levels = levels;
        }


        public void addLevel(RacialLevel level)
        {
            levels.add(level);
        }
    }
}
