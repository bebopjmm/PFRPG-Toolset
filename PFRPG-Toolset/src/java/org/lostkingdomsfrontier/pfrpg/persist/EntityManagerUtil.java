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
package org.lostkingdomsfrontier.pfrpg.persist;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EntityManagerUtil 
{
	private static final Log LOG = LogFactory.getLog(EntityManagerUtil.class);
	
	private final static String DATABASE = "d20engine";
	
	private final static EntityManager entityManager = Persistence.createEntityManagerFactory(DATABASE).createEntityManager();
	
	public static void persist(Object object)
	{
		LOG.debug("Persisting object");
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.persist(object);
		tx.commit();
	}
	
	public static List<?> query(String namedQuery)
	{
		LOG.debug("Running query: " + namedQuery);
		List<?> list = new LinkedList<Object>();
		
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		list = entityManager.createNamedQuery(namedQuery).getResultList();
		tx.commit();
		
		LOG.debug("Query result count: " + list.size());
		
		return list;
	}
	
	public static Object find(Class<?> c, Object id)
	{
		LOG.debug("Finding object with id: " + id + " of type: " + c);
		Object o = null;
		
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		o = entityManager.find(c, id);
		tx.commit();
		
		return o;
	}
	
	public static void close()
	{
	    entityManager.close();
	}
}
