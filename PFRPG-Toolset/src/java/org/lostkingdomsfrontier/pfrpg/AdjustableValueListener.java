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
 * Implementers of this interface will receive notice of changes in value to AdjustableValue objects
 * for which they are subscribed.
 * 
 * @author bebopjmm
 * @since sprint-0.1
 */
public interface AdjustableValueListener
{

   /**
    * This method will be invoked by an AdjustableValue when its value changes.
    * 
    * @param adjustable AdjustableValue that has changed.
    */
   public void valueChanged(AdjustableValue adjustable);
}
