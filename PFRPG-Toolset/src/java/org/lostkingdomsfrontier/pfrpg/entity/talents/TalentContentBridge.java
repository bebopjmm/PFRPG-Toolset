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
package org.lostkingdomsfrontier.pfrpg.entity.talents;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
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
import org.lostkingdomsfrontier.pfrpg.persist.PersistenceBridge;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;

/**
 * Bridges persistence layer with data model for Talents. All talents are loaded into memory are
 * startup.
 * 
 * @author bebopjmm
 * 
 */
public class TalentContentBridge extends PersistenceBridge
{
   final static Log LOG = LogFactory.getLog(TalentContentBridge.class);

   Collection talentsCollection;

   HashMap<String, Skill> skillMap = new HashMap<String, Skill>();

   Skill[] untrainedSkills = null;
   
   SkillsCollection skills;

   JAXBContext context;

   Unmarshaller unmarshaller;

   final String skillCollectionQuery;


   public TalentContentBridge(XMLConfiguration config)
   {
      super(config);
      skillCollectionQuery = config.getString("talentBridge.queries.skillCollection");
      if (skillCollectionQuery == null) {
         LOG.error("FAILED to retrieve query for (talentBridge.queries.skillCollection");
      }
      try {
         context = JAXBContext.newInstance("org.rollinitiative.d20.entity.talents");
         unmarshaller = context.createUnmarshaller();
      } catch (JAXBException ex) {
         LOG.error("Unexpected problem establishing JAXB context!", ex);
      }
   }


   /**
    * This method returns the set of skills that do not require training in order to use.
    * 
    * @return array of Skills representing the complete list of skills not requiring training
    */
   public Skill[] retrieveUntrainedSkills()
   {
      if (untrainedSkills != null) {
         return untrainedSkills;
      }
      else {
         loadSkillCollection();
         return untrainedSkills;
      }
   }


   public Skill retrieveSkill(QName skillID) throws InvalidEntityException
   {
      if (skillMap.size() < 1) {
         loadSkillCollection();
      }
      
      Skill skill = skillMap.get(skillID);
      if (skill != null) {
         return skill;
      }
      else {
         LOG.error("Failure to locate requested Skill: " + skillID.getLocalPart());
         throw new InvalidEntityException("Failure to retrieve specified Skill: " + skillID.getLocalPart());
      }
   }


   public void loadCollection(String campaign)
   {
      try {
         connect(campaign);
         talentsCollection = retrieveCollection("talents");
         xqueryService = (XQueryService) talentsCollection.getService("XQueryService", "1.0");
         loadSkillCollection();
      } catch (XMLDBException ex) {
         LOG.error("Failed to load the classes collection");
      }
   }


   public int getClassSkillBonus()
   {
      if (skills == null) {
         LOG
               .error("TalentContentBridge has not been associated to content, loadCollection needs to be invoked.");
         return 0;
      }
      return skills.rules.getClassSkillBonus();
   }


   public int getClassSkillRankCost()
   {
      if (skills == null) {
         LOG
               .error("TalentContentBridge has not been associated to content, loadCollection needs to be invoked.");
         return 0;
      }
      return skills.rules.getClassSkillRankCost();
   }


   public int getNonClassSkillRankCost()
   {
      if (skills == null) {
         LOG
               .error("TalentContentBridge has not been associated to content, loadCollection needs to be invoked.");
         return 0;
      }
      return skills.rules.getNonClassSkillRankCost();
   }


   public SkillRules getSkillRules()
   {
      if (skills == null) {
         LOG
               .error("TalentContentBridge has not been associated to content, loadCollection needs to be invoked.");
         return null;
      }
      return skills.rules;
   }


   void loadSkillCollection()
   {
      if (skillCollectionQuery == null) {
         LOG.error("FAILED to retrieve query for skillCollectionQuery");
         // establish rules defaults
         return;
      }

      ResourceSet results = query(skillCollectionQuery);
      try {
         String xml = results.getResource(0).getContent().toString();
         LOG.debug("XML for SkillCollection:\n" + xml);

         ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes());
         skills = (SkillsCollection) unmarshaller.unmarshal(input);
         LOG.info("Skills.rules.classSkillBonus = " + skills.rules.classSkillBonus);
         LOG.info("Total skills in collection = " + skills.skills.size());

      } catch (XMLDBException e) {
         LOG.error("Failure to parse skill rules ", e);
      } catch (JAXBException jaxbEx) {
         LOG.error("Failure to parse skill rules ", jaxbEx);
      }

      // initialize the untrainedSkills and skillMap
      ArrayList<Skill> temp = new ArrayList<Skill>();
      for (Skill skill : skills.getSkills()) {
         skillMap.put(skill.getId(), skill);
         if (skill.isUntrained()) {
            LOG.debug("Found untrained skill: " + skill.getName() + ", id: " + skill.getId());
            temp.add(skill);
         }
      }
      untrainedSkills = temp.toArray(new Skill[temp.size()]);
   }
}
