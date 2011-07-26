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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author bebopjmm
 *
 */
@XmlRootElement(name = "ItemCollection", namespace = "java:org.rollinitiative.d20.item")
@XmlType(name = "ItemCollectionType", namespace = "java:org.rollinitiative.d20.item")
public class ItemCollection
{
   static final Log LOG = LogFactory.getLog(ItemCollection.class);
   
   @XmlElements({@XmlElement(name = "Armor", type=Armor.class), @XmlElement(name="Weapon", type=Weapon.class)})
   private ArrayList<Item> items = new ArrayList<Item>();

   public synchronized void addItem(Item newItem)
   {
      // Verify this item is not already part of our inventory
      if (items.contains(newItem)) {
         return;
      }

      items.add(newItem);
   }
   
   public synchronized boolean containsItem(Item item)
   {
      return items.contains(item);
   }
   
   public synchronized void removeItem(Item targetItem)
   {
      // Verify this item is part of our inventory
      if (!items.contains(targetItem)) {
         return;
      }

      items.remove(targetItem);
   }
   
   public synchronized Item[] getItems()
   {
      return items.toArray(new Item[items.size()]);
   }
}
