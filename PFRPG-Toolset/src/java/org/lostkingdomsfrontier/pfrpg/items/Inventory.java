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

import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class maintains a collection of items and their state with respect to carrying and
 * equipping. Weight of carried and equipped items is calculated.
 * 
 * @author bebopjmm
 * 
 */
@XmlType(name = "InventoryType", namespace = "java:org.rollinitiative.d20.entity")
public class Inventory
{
   final static Log LOG = LogFactory.getLog(Inventory.class);

   @XmlElement(name = "Item")
   ArrayList<ItemInstance> items = new ArrayList<ItemInstance>();

   // ItemCollection items = new ItemCollection();

   double weight = 0.0;


   public synchronized void addItem(ItemInstance newItem)
   {
      // Verify this item is not already part of our inventory
      if (items.contains(newItem)) {
         return;
      }

      items.add(newItem);
      weight += newItem.getItemType().getWeight();
   }


   public synchronized void removeItem(ItemInstance targetItem)
   {
      // Verify this item is part of our inventory
      if (!items.contains(targetItem)) {
         return;
      }

      items.remove(targetItem);
      weight -= targetItem.getItemType().getWeight();
   }


   public double getWeight()
   {
      return weight;
   }


   public synchronized ItemInstance[] getContents()
   {
      return items.toArray(new ItemInstance[items.size()]);
   }


   public synchronized void recalcWeight()
   {
      weight = 0.0;
      for (ItemInstance item : items) {
         Item srcType = item.getItemType();
         if (srcType != null) {
            weight += srcType.getWeight();
         }
         else {
            LOG.warn("No srcType set for inventory item: " + item.getItemTypeID().getLocalPart());
         }
      }
   }
}
