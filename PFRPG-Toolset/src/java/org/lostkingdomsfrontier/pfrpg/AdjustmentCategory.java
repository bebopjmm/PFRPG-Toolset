/**
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
package org.lostkingdomsfrontier.pfrpg;

import javax.xml.bind.annotation.XmlType;

/**
 * Classification type for an adjustment
 * <ul>
 * <li>INHERENT: essential aspect and typically permanent (i.e. racial modifier to an attribute)</li>
 * <li>EQUIPMENT: associated with gear and only valid when equipped</li>
 * <li>EFFECT: consequence of external influence (i.e. spell or disease)</li>
 * </ul>
 * 
 * @author bebopjmm
 * 
 */
@XmlType(name = "AdjustmentEnum", namespace = "java:org.rollinitiative.d20")
public enum AdjustmentCategory {
    INHERENT, EQUIPMENT, EFFECT
}
