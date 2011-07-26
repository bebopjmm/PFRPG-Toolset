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

import java.util.UUID;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

/**
 * This class associates instance attributes with an Item type
 * 
 * @author jmccormi
 *
 */
@XmlType(name = "ItemInstanceType", namespace = "java:org.rollinitiative.d20.item")
public class ItemInstance
{
   private UUID instanceID;
   
   private QName itemTypeID;
   
   
   Item itemType;
   
   /**
    * @return the instanceID
    */
   public UUID getInstanceID()
   {
      return instanceID;
   }


   /**
    * @param instanceID the instanceID to set
    */
   @XmlAttribute(required = true)
   public void setInstanceID(UUID instanceID)
   {
      this.instanceID = instanceID;
   }


   /**
    * @return the itemType
    */
   public Item getItemType()
   {
      return itemType;
   }


   /**
    * @param itemType the itemType to set
    */
   @XmlTransient
   public void setItemType(Item itemType)
   {
      this.itemType = itemType;
   }
   
   
   
   /**
    * @return the itemTypeID
    */
   public QName getItemTypeID()
   {
      return itemTypeID;
   }


   /**
    * @param itemTypeID the itemTypeID to set
    */
   @XmlAttribute(required = true)
   public void setItemTypeID(QName itemTypeID)
   {
      this.itemTypeID = itemTypeID;
   }


   public static ItemInstance newItem(Item item)
   {
      ItemInstance newInstance = new ItemInstance();
      newInstance.setInstanceID(UUID.randomUUID());
      newInstance.setItemType(item);
      return newInstance;
   }
   
}
