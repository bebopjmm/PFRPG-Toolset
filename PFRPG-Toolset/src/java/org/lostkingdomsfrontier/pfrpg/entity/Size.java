/**
 * Project: PFRPG-Toolset
 * Created: Oct 21, 2006 by bebopJMM
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

import org.lostkingdomsfrontier.pfrpg.Adjustment;
import org.lostkingdomsfrontier.pfrpg.AdjustmentCategory;
import org.lostkingdomsfrontier.pfrpg.AdjustmentData;

import javax.xml.bind.annotation.*;

/**
 * Enumeration of the sizes that an entity may be classified as. The modifiers comply to the D20
 * SRD.
 * 
 * @see http://www.d20srd.org/srd/combat/combatStatistics.htm
 * 
 * TODO Refactor static methods to return Adjustment Data objects instead of Adjustment objects
 * 
 * @author bebopjmm
 * @since sprint-0.1
 */
@XmlType(name = "SizeEnum", namespace = "java:org.rollinitiative.d20.entity")
public enum Size {
    FINE((short) 8, (short) -16), DIMINUTIVE((short) 4, (short) -8), TINY((short) 2, (short) -4), SMALL(
            (short) 1, (short) -2), MEDIUM((short) 0, (short) 0), LARGE_TALL((short) -1, (short) 2), LARGE_LONG(
            (short) -1, (short) 2), HUGE_TALL((short) -2, (short) 4), HUGE_LONG((short) -2,
            (short) 4), GARGANTUAN_TALL((short) -4, (short) 8), GARGANTUAN_LONG((short) -4,
            (short) 8), COLOSSAL_TALL((short) -8, (short) 16), COLOSSAL_LONG((short) -8, (short) 16);

    private final short combatMod;

    private final short grappleMod;


    Size(short combatModifier, short grappleModifier)
    {
        combatMod = combatModifier;
        grappleMod = grappleModifier;
    }


    public short getCombatModifier()
    {
        return combatMod;
    }


    public short getGrappleModifier()
    {
        return grappleMod;
    }


    public static AdjustmentData generateCombatAdjustment(Size size)
    {
        return new AdjustmentData("Combat.Attack", AdjustmentCategory.INHERENT, size.combatMod, "size.combat");
    }


    public static AdjustmentData generateGrappleAdjustment(Size size)
    {
        return new AdjustmentData("Combat.CMB", AdjustmentCategory.INHERENT, size.grappleMod, "size.grapple");
    }

}
