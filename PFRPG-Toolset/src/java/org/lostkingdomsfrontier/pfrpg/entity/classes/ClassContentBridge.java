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
package org.lostkingdomsfrontier.pfrpg.entity.classes;

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
import org.lostkingdomsfrontier.pfrpg.entity.InvalidEntityException;
import org.lostkingdomsfrontier.pfrpg.entity.talents.TalentContentBridge;
import org.lostkingdomsfrontier.pfrpg.persist.PersistenceBridge;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;

/**
 * @author bebopjmm
 * 
 */
public class ClassContentBridge extends PersistenceBridge
{
   final static Log LOG = LogFactory.getLog(ClassContentBridge.class);

   HashMap<QName, CharacterClass> classCache = new HashMap<QName, CharacterClass>();

   TalentContentBridge skillBridge = null;

   Collection classesCollection;

   JAXBContext context;

   Unmarshaller unmarshaller;


   public ClassContentBridge(XMLConfiguration config)
   {
      super(config);
      try {
         context = JAXBContext.newInstance("org.rollinitiative.d20.entity.classes");
         unmarshaller = context.createUnmarshaller();
      } catch (JAXBException ex) {
         LOG.error("Unexpected problem establishing JAXB context!", ex);
      }
   }


   public void setSkillContentBridge(TalentContentBridge skillBridge)
   {
      this.skillBridge = skillBridge;
   }


   public void loadCollection(String campaign)
   {
      try {
         connect(campaign);
         classesCollection = retrieveCollection("classes");
         xqueryService = (XQueryService) classesCollection.getService("XQueryService", "1.0");
      } catch (XMLDBException ex) {
         LOG.error("Failed to load the classes collection");
      }
   }


   public CharacterClass retrieveClass(QName classID) throws InvalidEntityException
   {
      // Check Cache first
      if (classCache.containsKey(classID)) {
         LOG.debug("Cache contained requested class: " + classID.getLocalPart());
         return classCache.get(classID);
      }

      try {
         LOG.debug("Querying for requested class: " + classID.getLocalPart());
         String classQuery = config.getString("classBridge.queries.classByID");
         classQuery = classQuery.replace("#ID#", classID.getLocalPart());
         LOG.debug("Parameterized Query: " + classQuery);
         ResourceSet results = query(classQuery);
         if (results.getSize() < 1) {
            throw new InvalidEntityException("Specified Class not Found: " + classID);
         }
         String xml = results.getResource(0).getContent().toString();
         LOG.debug("XML for CharacterClass:\n" + xml);

         ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes());
         CharacterClass d20Class = (CharacterClass) unmarshaller.unmarshal(input);

         classCache.put(classID, d20Class);
         return d20Class;
      } catch (XMLDBException e) {
         LOG.error("Failure to parse requested Class: " + classID.getLocalPart(), e);
         throw new InvalidEntityException("Failure to retrieve specified Class: "
               + classID.getLocalPart());
      } catch (JAXBException jaxbEx) {
         LOG.error("Failure unmarshalling class", jaxbEx);
         throw new InvalidEntityException("Failure to unmarshall specified Class: "
               + classID.getLocalPart());
      }
   }

}
