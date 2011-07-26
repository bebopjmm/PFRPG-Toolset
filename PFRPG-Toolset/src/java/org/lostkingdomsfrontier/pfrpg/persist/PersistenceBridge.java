/**
 * *------------------------------------------------------------------------------
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
package org.lostkingdomsfrontier.pfrpg.persist;

import java.util.Properties;

import javax.xml.transform.OutputKeys;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exist.xmldb.XQueryService;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;

/**
 * This is a singleton for providing access to the persistence layer.
 * 
 * @author bebopjmm
 * 
 */
public class PersistenceBridge
{
   final static Log LOG = LogFactory.getLog(PersistenceBridge.class);

   protected XMLConfiguration config;

   protected XQueryService xqueryService;

   String rootCollection;


   public PersistenceBridge(XMLConfiguration config)
   {
      this.config = config;
      LOG.info("Assigned config. Root element name = " + this.config.getRootElementName());
   }


   public String getPersistenceType()
   {
      String type = config.getString("persistence[@type]");
      LOG.info("persistence type:" + type);
      return type;
   }


   public Collection retrieveCollection(String name) throws XMLDBException
   {
      String target = rootCollection + "/" + name;
      LOG.info("Retrieving collection: " + target);
      // get the collection
      return DatabaseManager.getCollection(target);
   }


   public String getQuery(String queryTag)
   {
      return config.getString(queryTag);
   }


   protected void connect(String campaign) throws XMLDBException
   {
      // initialize database drivers
      String driver = config.getString("persistence.driver");
      Database database;
      try {
         Class cl = Class.forName(driver);
         database = (Database) cl.newInstance();
      } catch (Exception ex) {
         LOG.fatal("Cannot instantiate xmldb database instance!", ex);
         throw new XMLDBException();
      }
      DatabaseManager.registerDatabase(database);
      LOG.info("Registered to XMLDB using driver: " + driver);

      String uri = config.getString("persistence.connectionURI");
      rootCollection = uri + "/" + config.getString("persistence.rootCollection") + "/" + campaign;

   }


   protected ResourceSet query(String xquery)
   {
      if (xqueryService == null) {
         LOG.warn("Invalid attempt to perform query before xqueryService_ has been set.");
         return null;
      }
      long start = System.currentTimeMillis();
      ResourceSet results = null;
      try {
         results = xqueryService.query(xquery);
         long qtime = System.currentTimeMillis() - start;
         start = System.currentTimeMillis();

         Properties outputProperties = new Properties();
         outputProperties.setProperty(OutputKeys.INDENT, "yes");
         long rtime = System.currentTimeMillis() - start;
         LOG.trace("hits/queryTimeMillis/retrieveTimeMillis: " + results.getSize() + "/" + qtime
               + "/" + rtime);
      } catch (XMLDBException e) {
         LOG.error("Failure for query: " + xquery, e);
      }
      return results;
   }
}
