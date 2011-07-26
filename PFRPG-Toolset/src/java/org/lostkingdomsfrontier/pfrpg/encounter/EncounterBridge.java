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
package org.lostkingdomsfrontier.pfrpg.encounter;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exist.xmldb.XQueryService;
import org.lostkingdomsfrontier.pfrpg.persist.PersistenceBridge;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;

/**
 * @author bebopjmm
 * 
 */
public class EncounterBridge extends PersistenceBridge
{
   final static Log LOG = LogFactory.getLog(EncounterBridge.class);

   Collection encounterCollection;


   public EncounterBridge(XMLConfiguration config)
   {
      super(config);
   }


   /**
    * Retrieves the item collection for the specified campaign.
    * 
    * @param campaign
    */
   public void loadCollection(String campaign)
   {
      try {
         connect(campaign);
         encounterCollection = retrieveCollection("encounters");
         xqueryService = (XQueryService) encounterCollection.getService("XQueryService", "1.0");
      } catch (XMLDBException ex) {
         LOG.error("Failed to load the races collection");
      }
   }
}
