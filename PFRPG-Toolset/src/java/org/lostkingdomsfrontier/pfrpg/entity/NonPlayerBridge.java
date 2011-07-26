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
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

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
 * @author bebopjmm
 *
 */
public class NonPlayerBridge extends PersistenceBridge
{
   final static Log LOG = LogFactory.getLog(NonPlayerBridge.class);
   
   HashMap<QName, NonPlayerTemplate> templateCache = new HashMap<QName, NonPlayerTemplate>();

   Collection npcCollection;

   JAXBContext context;

   Unmarshaller unmarshaller;
   
   NonPlayerFactory npcFactory;
   
   public NonPlayerBridge(XMLConfiguration config)
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
    * @return the npcFactory
    */
   public NonPlayerFactory getNpcFactory()
   {
      return npcFactory;
   }



   /**
    * @param npcFactory the npcFactory to set
    */
   public void setNpcFactory(NonPlayerFactory npcFactory)
   {
      this.npcFactory = npcFactory;
   }



   /**
    * Retrieves the npc collection for the specified campaign.
    * 
    * @param campaign
    */
   public void loadCollection(String campaign)
   {
      try {
         connect(campaign);
         npcCollection = retrieveCollection("npcs");
         xqueryService = (XQueryService) npcCollection.getService("XQueryService", "1.0");
      } catch (XMLDBException ex) {
         LOG.error("Failed to load the races collection");
      }
   }

   
   public NonPlayer getNPCTemplateInstance(QName templateID) throws InvalidEntityException, BridgeException
   {
      NonPlayerTemplate npcTemplate = retrieveTemplate(templateID);
      NonPlayer npc = npcTemplate.makeNewInstance();
      npcFactory.initNPC(npc);
      return npc;
   }


   NonPlayerTemplate retrieveTemplate(QName templateID) throws InvalidEntityException
   {
      // Check Cache first
      if (templateCache.containsKey(templateID)) {
         LOG.debug("Cache contained requested template: " + templateID);
         return templateCache.get(templateID);
      }
      try {
         LOG.debug("Querying for requested template: " + templateID);
         String templateQuery = config.getString("npcBridge.queries.templateByID");
         templateQuery = templateQuery.replace("#ID#", templateID.getLocalPart());
         LOG.debug("Parameterized Query: " + templateQuery);
         ResourceSet results = query(templateQuery);
         if (results.getSize() < 1) {
            throw new InvalidEntityException("Specified Template not Found: " + templateID);
         }
         String xml = results.getResource(0).getContent().toString();
         LOG.debug("XML for Template:\n" + xml);


         ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes());
         NonPlayerTemplate template = (NonPlayerTemplate) unmarshaller.unmarshal(input);
         
         templateCache.put(templateID, template);
         return template;
      } catch (XMLDBException e) {
         LOG.error("Failure to parse requested race: )" + templateID, e);
         throw new InvalidEntityException("Failure to retrieve specified Race: " + templateID);
      } catch (JAXBException jaxbEx) {
         LOG.error("Failure to parse requested NonPlayerTemplate: )" + templateID, jaxbEx);
         throw new InvalidEntityException("Failure to retrieve specified NonPlayerTemplate: "
               + templateID);
      }
   }
}
