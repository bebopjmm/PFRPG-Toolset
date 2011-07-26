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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.lostkingdomsfrontier.pfrpg.Adjustment;
import org.lostkingdomsfrontier.pfrpg.AdjustmentChoices;
import org.lostkingdomsfrontier.pfrpg.AdjustmentData;

/**
 * RacialLevel is a data model class that captures the adjustments and capabilities associated with
 * a specific level for a race.
 * 
 * @author bebopjmm
 * 
 * @since sprint-0.2
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RacialLevelType", namespace = "java:org.rollinitiative.d20.entity")
public class RacialLevel
{
    /**
     * JPA Persistence ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Long persistID;

    @XmlAttribute(required = true)
    private short level = 0;

    @XmlElement(name = "AdjustmentEntry")
    @OneToMany(cascade = CascadeType.ALL)
    private Set<AdjustmentData> adjustmentData = new HashSet<AdjustmentData>();

    @XmlTransient
    @Transient
    private Set<Adjustment> adjustments = null;

    @XmlElement(name = "AdjustmentChoiceEntry")
    @OneToMany(cascade = CascadeType.ALL)
    private Set<AdjustmentChoices> adjustmentChoices = new HashSet<AdjustmentChoices>();


    // private CombatSpec combatMods = new CombatSpec();

    public RacialLevel()
    {

    }


    public RacialLevel(short level)
    {
        this.level = level;
    }


    /**
     * @return the level
     * @since sprint-0.2
     */
    public short getLevel()
    {
        return level;
    }


    /**
     * @param level the level to set
     * @since sprint-0.2
     */
    public void setLevel(short level)
    {
        this.level = level;
    }


    // /**
    // * @return the combatMods
    // */
    // public CombatSpec getCombatMods()
    // {
    // return combatMods;
    // }
    //
    //
    // /**
    // * @param combatMods the combatMods to set
    // */
    // @XmlElement(name = "CombatMods", required = true)
    // public void setCombatMods(CombatSpec combatMods)
    // {
    // this.combatMods = combatMods;
    // }

    // /**
    // * @return the abilModChoice
    // */
    // public AbilityModifierChoice getAbilModChoice()
    // {
    // return abilModChoice;
    // }
    //
    //
    // /**
    // * @param abilModChoice the abilModChoice to set
    // */
    // @XmlElement(name = "AbilityModifierChoice", required = false)
    // public void setAbilModChoice(AbilityModifierChoice abilModChoice)
    // {
    // this.abilModChoice = abilModChoice;
    // }

    /**
     * @return the adjustmentData
     * @since sprint-0.2
     */
    public Set<AdjustmentData> getAdjustmentData()
    {
        return adjustmentData;
    }


    /**
     * @param adjustmentData the adjustmentData to set
     * @since sprint-0.2
     */
    public void setAdjustmentData(Set<AdjustmentData> adjustmentData)
    {
        this.adjustmentData = adjustmentData;
    }


    /**
     * @return the adjustments
     * @since sprint-0.2
     */
    public Set<Adjustment> getAdjustments()
    {
        if (adjustments == null) {
            adjustments = new HashSet<Adjustment>(adjustmentData.size());
            for (AdjustmentData entry : adjustmentData) {
                adjustments.add(new Adjustment(entry));
            }
        }
        return adjustments;
    }


    /**
     * @return the adjustmentChoices
     * @since sprint-0.3
     */
    public Set<AdjustmentChoices> getAdjustmentChoices()
    {
        return adjustmentChoices;
    }


    /**
     * @param adjustmentChoices the adjustmentChoices to set
     * @since sprint-0.3
     */
    public void setAdjustmentChoices(Set<AdjustmentChoices> adjustmentChoices)
    {
        this.adjustmentChoices = adjustmentChoices;
    }


    public Long getPersistID()
    {
        return persistID;
    }


    public void setPersistID(Long persistID)
    {
        this.persistID = persistID;
    }

    // @XmlType(name = "RacialCombatType", namespace = "java:org.rollinitiative.d20.entity")
    // public static class CombatSpec
    // {
    // private int naturalArmor = 0;
    //
    // private int racialBAB = 0;
    //
    //
    // /**
    // * @return the naturalArmor
    // */
    // public int getNaturalArmor()
    // {
    // return naturalArmor;
    // }
    //
    //
    // /**
    // * @param naturalArmor the naturalArmor to set
    // */
    // @XmlAttribute(required = true)
    // public void setNaturalArmor(int naturalArmor)
    // {
    // this.naturalArmor = naturalArmor;
    // }
    //
    //
    // /**
    // * @return the racialBAB
    // */
    // public int getRacialBAB()
    // {
    // return racialBAB;
    // }
    //
    //
    // /**
    // * @param racialBAB the racialBAB to set
    // */
    // @XmlAttribute(required = true)
    // public void setRacialBAB(int racialBAB)
    // {
    // this.racialBAB = racialBAB;
    // }
    //
    // }

    // @XmlType(name = "AbilityModifierChoiceType", namespace =
    // "java:org.rollinitiative.d20.entity")
    // public static class AbilityModifierChoice
    // {
    // private int modifier = 0;
    //
    // private QName id;
    //
    //
    // /**
    // * @return the modifier
    // */
    // public int getModifier()
    // {
    // return modifier;
    // }
    //
    //
    // /**
    // * @param modifier the modifier to set
    // */
    // @XmlAttribute(name = "modifier", required = true)
    // public void setModifier(int modifier)
    // {
    // this.modifier = modifier;
    // }
    //
    //
    // /**
    // * @return the id
    // */
    // public QName getId()
    // {
    // return id;
    // }
    //
    //
    // /**
    // * @param id the id to set
    // */
    // @XmlAttribute(name = "id", required = true)
    // public void setId(QName id)
    // {
    // this.id = id;
    // }
    //
    // }
}
