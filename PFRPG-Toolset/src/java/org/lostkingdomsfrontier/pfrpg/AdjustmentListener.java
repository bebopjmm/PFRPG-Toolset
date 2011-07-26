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

/**
 * The AdjustmentListener interface enables an object to be notified in changes to an Adjustment
 * value.
 * 
 * @author bebopjmm
 * @since sprint-0.1
 * 
 */
public interface AdjustmentListener
{

    /**
     * This method will be invoked by an Adjustment when its value changes.
     * @since sprint-0.1
     */
    public void valueChanged(Adjustment adjustment);
}
