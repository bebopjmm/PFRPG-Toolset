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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlType;

/**
 * This class allows for the specification of an adjustment to a specific ability (i.e. racial
 * bonus/penalty).
 * 
 * @author bebopjmm
 * @since sprint-0.1
 */
@XmlType(name = "AbilityModifierType", namespace = "java:org.rollinitiative.d20.entity")
public class AbilityModifier
{
    private int modifier = 0;

    private Ability ability;

    private String id;

    public AbilityModifier()
    {
        
    }
    
    public AbilityModifier(Ability ability, int value)
    {
        this.ability = ability;
        this.modifier = value;
    }

    /**
     * @return the modifier
     */
    public int getModifier()
    {
        return modifier;
    }


    /**
     * @param modifier the modifier to set
     */
    @XmlAttribute(required = true)
    public void setModifier(int modifier)
    {
        this.modifier = modifier;
    }


    /**
     * @return the ability
     */
    public Ability getAbility()
    {
        return ability;
    }


    /**
     * @param ability the ability to set
     */
    @XmlAttribute(required = true)
    public void setAbility(Ability ability)
    {
        this.ability = ability;
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
    @XmlID
    public void setId(String id)
    {
        this.id = id;
    }

}
