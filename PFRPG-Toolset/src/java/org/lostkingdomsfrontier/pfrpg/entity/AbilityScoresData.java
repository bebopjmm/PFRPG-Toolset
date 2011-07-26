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

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@Embeddable
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbilityScoresType", namespace = "java:org.rollinitiative.d20.entity")
public class AbilityScoresData
{
    @XmlElement(name = "STR", required = true)
    private short str;

    @XmlElement(name = "DEX", required = true)
    private short dex;

    @XmlElement(name = "CON", required = true)
    private short con;

    @XmlElement(name = "INT", required = true)
    private short int_;

    @XmlElement(name = "WIS", required = true)
    private short wis;

    @XmlElement(name = "CHA", required = true)
    private short cha;


    public AbilityScoresData()
    {
        this.str = AbilityValue.DEFAULT_VALUE;
        this.dex = AbilityValue.DEFAULT_VALUE;
        this.con = AbilityValue.DEFAULT_VALUE;
        int_ = AbilityValue.DEFAULT_VALUE;
        this.wis = AbilityValue.DEFAULT_VALUE;
        this.cha = AbilityValue.DEFAULT_VALUE;
    }


    public AbilityScoresData(short str, short dex, short con, short int1, short wis, short cha)
    {
        this.str = str;
        this.dex = dex;
        this.con = con;
        int_ = int1;
        this.wis = wis;
        this.cha = cha;
    }


    public short getStr()
    {
        return str;
    }


    public void setStr(short str)
    {
        this.str = str;
    }


    public short getDex()
    {
        return dex;
    }


    public void setDex(short dex)
    {
        this.dex = dex;
    }


    public short getCon()
    {
        return con;
    }


    public void setCon(short con)
    {
        this.con = con;
    }


    public short getInt_()
    {
        return int_;
    }


    public void setInt_(short int1)
    {
        int_ = int1;
    }


    public short getWis()
    {
        return wis;
    }


    public void setWis(short wis)
    {
        this.wis = wis;
    }


    public short getCha()
    {
        return cha;
    }


    public void setCha(short cha)
    {
        this.cha = cha;
    }


    public void setScore(Ability ability, short value)
    {
        switch (ability) {
        case STR:
            setStr(value);
            break;
        case DEX:
            setDex(value);
            break;
        case CON:
            setCon(value);
            break;
        case INT:
            setInt_(value);
            break;
        case WIS:
            setWis(value);
            break;
        case CHA:
            setCha(value);
            break;
        default:
            break;
        }
    }
}