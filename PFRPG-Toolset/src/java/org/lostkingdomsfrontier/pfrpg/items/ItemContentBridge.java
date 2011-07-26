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
package org.lostkingdomsfrontier.pfrpg.items;

import java.io.ByteArrayInputStream;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

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
 * This class bridges the domains of data content and data persistence for Equipment related
 * content.
 * 
 * @author bebopjmm
 * 
 *         TODO Determine if separate collections should be maintained for major groups (i.e. armor
 *         and weapons)
 */
public class ItemContentBridge extends PersistenceBridge
{
   final static Log LOG = LogFactory.getLog(ItemContentBridge.class);

   Collection itemCollection;

   HashMap<String, Item> itemCache = new HashMap<String, Item>();

   HashMap<QName, Item> cache = new HashMap<QName, Item>();

   JAXBContext context;

   Unmarshaller unmarshaller;


   public ItemContentBridge(XMLConfiguration config)
   {
      super(config);
      try {
         context = JAXBContext.newInstance("org.rollinitiative.d20.items");
         unmarshaller = context.createUnmarshaller();
      } catch (JAXBException ex) {
         LOG.error("Unexpected problem establishing JAXB context!", ex);
      }
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
         itemCollection = retrieveCollection("items");
         xqueryService = (XQueryService) itemCollection.getService("XQueryService", "1.0");
      } catch (XMLDBException ex) {
         LOG.error("Failed to load the races collection");
      }
   }


   /**
    * This method constructs a new instance of an Item of the type designated by itemTypeID. NOTE:
    * currently only armor is supported.
    * 
    * @param itemTypeID uniqueID of the ItemType to create an instance Item from
    * @return a new Item based on ItemType
    * @throws InvalidEntityException when no ItemType of itemTypeID can be retrieved.
    */
   public ItemInstance makeInstanceOf(QName itemTypeID) throws InvalidEntityException
   {
      Item itemTemplate = retrieveItem(itemTypeID);
      return ItemInstance.newItem(itemTemplate);
   }


   public Item retrieveItem(QName itemID) throws InvalidEntityException
   {
      // Check Cache first
      if (cache.containsKey(itemID)) {
         LOG.debug("Cache contained requested item: " + itemID.getLocalPart());
         return cache.get(itemID);
      }

      try {
         LOG.debug("Querying for requested item: " + itemID.getLocalPart());
         String itemQuery = config.getString("itemBridge.queries.itemByID");
         itemQuery = itemQuery.replace("#ID#", itemID.getLocalPart());
         LOG.debug("Parameterized Query: " + itemQuery);
         ResourceSet results = query(itemQuery);
         if (results.getSize() < 1) {
            throw new InvalidEntityException("Specified Item not Found: " + itemID.getLocalPart());
         }
         String xml = results.getResource(0).getContent().toString();
         LOG.debug("XML for Item:\n" + xml);

         ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes());
         if (xml.startsWith("<Armor")) {
            JAXBElement<Armor> armor = unmarshaller.unmarshal(new StreamSource(input), Armor.class);
            cache.put(itemID, armor.getValue());
            return armor.getValue();
         }
         else {
            LOG.error("Unsupported Item Class");
            throw new InvalidEntityException("Unsupported Item Class: "
                  + itemID.getLocalPart());
         }
         
      } catch (XMLDBException e) {
         LOG.error("Failure to retrieve requested item: " + itemID.getLocalPart(), e);
         throw new InvalidEntityException("Failure to retrieve specified Item: "
               + itemID.getLocalPart());
      } catch (JAXBException ex) {
         LOG.error("Failure to parse requested item: " + itemID.getLocalPart(), ex);
         throw new InvalidEntityException("Failure to retrieve specified Item: " + itemID.getLocalPart());
      }
   }
}
