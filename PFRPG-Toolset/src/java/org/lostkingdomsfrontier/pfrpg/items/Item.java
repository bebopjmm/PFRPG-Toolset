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
package org.lostkingdomsfrontier.pfrpg.items;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * @author bebopjmm
 * 
 */
@XmlType(name = "ItemType", namespace = "java:org.rollinitiative.d20.item")
public abstract class Item
{

    @XmlType(name = "CraftsmanshipEnum", namespace = "java:org.rollinitiative.d20.item")
    public enum Craftsmanship {
        MUNDANE, MASTERWORK, MAGICAL;
    }

    private String typeID;

    private String name = "Unnamed Item";

    private double gpCost = 0;

    private double weight = 0;

    private Craftsmanship craftsmanship = Craftsmanship.MUNDANE;


    abstract protected void configure();


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
    @XmlAttribute(required = true)
    public void setName(String name)
    {
        this.name = name;
    }


    /**
     * @return the typeID
     */
    public String getTypeID()
    {
        return typeID;
    }


    /**
     * @param typeID the typeID to set
     */
    @XmlAttribute(required = true)
    public void setTypeID(String typeID)
    {
        this.typeID = typeID;
    }


    /**
     * @return the weight of this item
     */
    public double getWeight()
    {
        return weight;
    }


    /**
     * @param weight the weight to set for this item
     */
    @XmlAttribute(required = true)
    public void setWeight(double weight)
    {
        this.weight = weight;
    }


    /**
     * @return the craftsmanship
     */
    public Craftsmanship getCraftsmanship()
    {
        return craftsmanship;
    }


    /**
     * @param craftsmanship the craftsmanship to set
     */
    @XmlAttribute(required = true)
    public void setCraftsmanship(Craftsmanship craftsmanship)
    {
        this.craftsmanship = craftsmanship;
    }


    /**
     * @return the gpCost
     */
    public double getGpCost()
    {
        return gpCost;
    }


    /**
     * @param gpCost the gpCost to set
     */
    @XmlAttribute(required = true)
    public void setGpCost(double gpCost)
    {
        this.gpCost = gpCost;
    }

}
