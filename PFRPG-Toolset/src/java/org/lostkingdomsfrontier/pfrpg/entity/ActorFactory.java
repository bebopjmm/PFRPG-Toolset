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

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lostkingdomsfrontier.pfrpg.entity.classes.ClassContentBridge;
import org.lostkingdomsfrontier.pfrpg.entity.races.RaceContentBridge;
import org.lostkingdomsfrontier.pfrpg.entity.talents.Talent;
import org.lostkingdomsfrontier.pfrpg.entity.talents.TalentContentBridge;
import org.lostkingdomsfrontier.pfrpg.items.Item;
import org.lostkingdomsfrontier.pfrpg.items.ItemContentBridge;
import org.lostkingdomsfrontier.pfrpg.items.ItemInstance;

/**
 * @author bebopjmm
 * 
 */
public abstract class ActorFactory
{
   static final Log LOG = LogFactory.getLog(ActorFactory.class);

   /**
    * Bridge to accessing Race-based content.
    */
   RaceContentBridge raceBridge;

   /**
    * Bridge to accessing Class-based content
    */
   ClassContentBridge classBridge;

   /**
    * Bridge to accessing Skill-based content
    */
   TalentContentBridge talentBridge;

   ItemContentBridge itemBridge;


   /**
    * @param bridge
    */
   public void setRaceContentBridge(RaceContentBridge bridge)
   {
      raceBridge = bridge;
   }


   public RaceContentBridge getRaceContentBridge()
   {
      return raceBridge;
   }


   /**
    * @param bridge
    */
   public void setClassContentBridge(ClassContentBridge bridge)
   {
      classBridge = bridge;
   }


   public ClassContentBridge getClassContentBridge()
   {
      return classBridge;
   }


   /**
    * @return the skillBridge
    */
   public TalentContentBridge getTalentBridge()
   {
      return talentBridge;
   }


   /**
    * @param talentBridge the skillBridge to set
    */
   public void setTalentBridge(TalentContentBridge talentBridge)
   {
      this.talentBridge = talentBridge;
//      Actor.setSkillRules(talentBridge.getSkillRules());
   }


   /**
    * @return the itemBridge
    */
   public ItemContentBridge getItemBridge()
   {
      return itemBridge;
   }


   /**
    * @param itemBridge the itemBridge to set
    */
   public void setItemBridge(ItemContentBridge itemBridge)
   {
      this.itemBridge = itemBridge;
   }


   protected boolean verifyBridgeConfiguration()
   {
      if (talentBridge == null) {
         LOG.error("SkillContentBridge has not been set!");
         return false;
      }

      if (itemBridge == null) {
         LOG.error("ItemContentBridge has not been set!");
         return false;
      }

      if (classBridge == null) {
         LOG.error("ClassContentBridge has not been set!");
         return false;
      }

      if (raceBridge == null) {
         LOG.error("RaceContentBridge has not been set!");
         return false;
      }

      return true;
   }


//   protected void processTalents(Actor actor, TalentType[] talents)
//   {
//      Talent talent = null;
//      for (TalentType talentType : talents) {
//         talent = new Talent();
//         talent.setId(talentType.getId().toString());
//         talent.setName(talentType.getName());
//         talent.setDescription(talentType.getDescription());
//         actor.addTalent(talent);
//      }
//   }


   /**
    * This method instantiates instances of each item in the InventoryItemType array and adds it to
    * the actor's inventory.
    * 
    * @param actor The actor to receive the inventory items
    * @param items Array of InventoryItemTypes
    * @throws InvalidEntityException when a specified InventoryItemType can't be located.
    */
   void processInventory(Actor actor) throws InvalidEntityException
   {
//      // iterate over items array retrieving true instances from contentBridge and setting them
//      for (ItemInstance item : actor.inventory.getContents()) {
//         QName itemType = item.getItemTypeID();
//         Item orig =itemBridge.retrieveItem(itemType);
//         item.setItemType(orig);
//      }
//      actor.inventory.recalcWeight();
   }
}
