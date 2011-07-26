/**
 * Project: PFRPG-Toolset
 * Created: Oct 25, 2007 by bebopJMM
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
package org.lostkingdomsfrontier.pfrpg.entity.races;

import java.io.ByteArrayInputStream;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exist.xmldb.XQueryService;
import org.lostkingdomsfrontier.pfrpg.entity.InvalidEntityException;
import org.lostkingdomsfrontier.pfrpg.persist.PersistenceBridge;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;

/**
 * This class bridges the domains of data content and data persistence for Race related content.
 * 
 * @author bebopjmm
 * 
 */
public class RaceContentBridge extends PersistenceBridge
{
   final static Log LOG = LogFactory.getLog(RaceContentBridge.class);

   HashMap<String, Race> raceCache = new HashMap<String, Race>();

   Collection racesCollection;

   JAXBContext context;

   Unmarshaller unmarshaller;


   public RaceContentBridge(XMLConfiguration config)
   {
      super(config);
      try {
         context = JAXBContext.newInstance("org.rollinitiative.d20.entity.races");
         unmarshaller = context.createUnmarshaller();
      } catch (JAXBException ex) {
         LOG.error("Unexpected problem establishing JAXB context!", ex);
      }
   }


   /**
    * Retrieves the race collection for the specified campaign.
    * 
    * @param campaign
    */
   public void loadCollection(String campaign)
   {
      try {
         connect(campaign);
         racesCollection = retrieveCollection("races");
         xqueryService = (XQueryService) racesCollection.getService("XQueryService", "1.0");
      } catch (XMLDBException ex) {
         LOG.error("Failed to load the races collection");
      }
   }


   public Race retrieveRace(String raceID) throws InvalidEntityException
   {
      // Check cache first
      if (raceCache.containsKey(raceID)) {
         LOG.debug("Cache contained requested race: " + raceID);
         return raceCache.get(raceID);
      }

      try {
         LOG.debug("Querying for requested race: " + raceID);
         String raceQuery = config.getString("raceBridge.queries.raceByID");
         raceQuery = raceQuery.replace("#ID#", raceID);
         LOG.debug("Parameterized Query: " + raceQuery);
         ResourceSet results = query(raceQuery);
         if (results.getSize() < 1) {
            throw new InvalidEntityException("Specified Race not Found: " + raceID);
         }
         String xml = results.getResource(0).getContent().toString();
         LOG.debug("XML for Race:\n" + xml);

         ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes());
         Race race = (Race) unmarshaller.unmarshal(input);
         raceCache.put(raceID, race);
         
//         if (race.getAdvancement() == null) {
//            LOG.error("Problem unmarshalling racial advancements!!");
//         }
//         else {
//            LOG.info ("nLevels in racial advancement = " + race.getAdvancement().levels.size());
//         }
         return race;
      } catch (JAXBException ex) {
         LOG.error("Failure to parse requested race: )" + raceID, ex);
         throw new InvalidEntityException("Failure to retrieve specified Race: " + raceID);
      } catch (XMLDBException e) {
         LOG.error("Failure to parse requested race: )" + raceID, e);
         throw new InvalidEntityException("Failure to retrieve specified Race: " + raceID);
      }
   }
}
