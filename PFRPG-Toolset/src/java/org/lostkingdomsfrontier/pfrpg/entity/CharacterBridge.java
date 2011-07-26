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

import java.io.ByteArrayInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exist.xmldb.XQueryService;
import org.lostkingdomsfrontier.pfrpg.persist.BridgeException;
import org.lostkingdomsfrontier.pfrpg.persist.PersistenceBridge;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;

/**
 * This class bridges the domains of data content and data persistence for Character related
 * content.
 * 
 * @author bebopjmm
 * 
 */
public class CharacterBridge extends PersistenceBridge
{
   final static Log LOG = LogFactory.getLog(CharacterBridge.class);

   Collection characterCollection;

   JAXBContext context;

   Unmarshaller unmarshaller;

   CharacterFactory characterFactory;


   public CharacterBridge(XMLConfiguration config)
   {
      super(config);
      try {
         context = JAXBContext.newInstance("org.rollinitiative.d20.entity");
         unmarshaller = context.createUnmarshaller();
      } catch (JAXBException ex) {
         LOG.error("Unexpected problem establishing JAXB context!", ex);
      }
   }


   /**
    * @return the charcterFactory
    */
   public CharacterFactory getCharacterFactory()
   {
      return characterFactory;
   }


   /**
    * @param charcterFactory the charcterFactory to set
    */
   public void setCharacterFactory(CharacterFactory characterFactory)
   {
      this.characterFactory = characterFactory;
   }


   /**
    * This method loads the collection where campaign is directly off the root collection of the
    * repository, and characterCollection is directly off the campaign.
    * 
    * @param campaign
    * @param characterCollection
    */
   public void loadCollection(String campaign, String characterCollection)
   {
      try {
         connect(campaign);
         this.characterCollection = retrieveCollection(characterCollection);
         xqueryService = (XQueryService) this.characterCollection
               .getService("XQueryService", "1.0");
      } catch (XMLDBException ex) {
         LOG.error("Failed to load the characre collection collection: " + characterCollection);
      }
   }


   /**
    * This method returns the D20 Player Character information for the character identified by
    * characterID. Currently the name attribute is used as the characterID.
    * 
    * @param characterID Identification attribute of the character being retrieved
    * @return Player for the character identified by characterID, null if not found.
    */
   public Player getPC(String characterID)
   {
      String indexQuery = config.getString("characterBridge.queries.characterByID");
      indexQuery = indexQuery.replace("#ID#", characterID);

      LOG.debug("Parameterized Query: " + indexQuery);
      ResourceSet results = query(indexQuery);

      try {
         String xml = results.getResource(0).getContent().toString();
         LOG.debug("XML for Player:\n" + xml);

         ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes());
         Player player = (Player) unmarshaller.unmarshal(input);
         characterFactory.initPlayer(player);
         return player;
      } catch (XMLDBException xmldbEx) {
         LOG.error("Failure processing ResourceSet", xmldbEx);
         return null;
      } catch (JAXBException jaxbEx) {
         LOG.error("Failure unmarshalling player", jaxbEx);
         return null;
      } catch (InvalidEntityException e) {
         LOG.error("Failure initializing player", e);
         return null;
      }
   }


   public Player[] getGroupPCs(String groupName) throws BridgeException, InvalidEntityException
   {
      if (characterFactory == null) {
         throw new BridgeException("Character Factory has not been assigned");
      }
      Player[] group = null;
      String indexQuery = config.getString("characterBridge.queries.group");
      indexQuery = indexQuery.replace("#NAME#", groupName);
      

      LOG.debug("Parameterized Query: " + indexQuery);
      ResourceSet results = query(indexQuery);
      if (results == null) {
         LOG.warn("Returning EMPTY array due to NULL ResultSet for query on groupName: "
               + groupName);
         return new Player[0];
      }

      try {
         group = new Player[(int) results.getSize()];
         LOG.info("Total PCs in group = " + group.length);
         for (int i = 0; i < group.length; i++) {
            String xml = results.getResource(i).getContent().toString();
            LOG.debug("XML for Player:\n" + xml);

            ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes());
            group[i] = (Player) unmarshaller.unmarshal(input);
            characterFactory.initPlayer(group[i]);
         }
      } catch (JAXBException jaxbEx) {
         LOG.error("Failure unmarshalling Player", jaxbEx);
         return new Player[0];
      } catch (XMLDBException xmldbEx) {
         LOG.error("Failure processing ResourceSet", xmldbEx);
         return new Player[0];
      }

      return group;
   }

}
