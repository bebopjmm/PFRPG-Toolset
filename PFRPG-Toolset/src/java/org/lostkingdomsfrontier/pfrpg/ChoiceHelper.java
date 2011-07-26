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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * @author bebopjmm
 *
 */
@Embeddable
public class ChoiceHelper
{

    @OneToMany(cascade = CascadeType.ALL)
    private Set<AdjustmentChoiceSelection> options = new HashSet<AdjustmentChoiceSelection>();
    
    @Transient
    private Set<AdjustmentChoices> managedChoices = new HashSet<AdjustmentChoices>();
    
    @Transient
    int selectionsMade = 0;
    
    public void addChoiceSet(AdjustmentChoices choiceSet)
    {
        if(!managedChoices.contains(choiceSet)) {
            managedChoices.add(choiceSet);
            options.add(new AdjustmentChoiceSelection(choiceSet));
        }
    }
    
    public int getTotalSelections()
    {
        return options.size();
    }
    
    public int getSelectionsMade()
    {
        return selectionsMade;
    }
}
