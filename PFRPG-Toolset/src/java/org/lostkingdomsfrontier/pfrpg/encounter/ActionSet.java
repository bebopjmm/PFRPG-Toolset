/**
 * Project: d20Campaigner
 * Created: Aug 21, 2007 by bebopJMM
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
package org.lostkingdomsfrontier.pfrpg.encounter;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author bebopJMM
 * 
 */
public class ActionSet
{
   static final Log LOG = LogFactory.getLog(ActionSet.class);
   
   boolean hasMove = false;
   boolean hasStandard = false;
   boolean hasFull = false;
   
   
   ArrayList<Action> actionList = new ArrayList<Action>();
   Iterator<Action> execution = null;
   
   
   public void executeNext()
   {
      if (execution == null) {
         execution = actionList.iterator();
      }
      Action action = execution.next();
      action.execute();
   }
   
   public void addAction(Action newAction)
   {
      if (actionList.contains(newAction)) {
         LOG.warn("Ignoring attempt to add action already in ActionSet.");
         return;
      }
      actionList.add(newAction);
   }
}
