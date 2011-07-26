/**
 * Project: PFRPG-Toolset
 * Created: Sep 4, 2007 by bebopJMM
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

import javax.xml.bind.annotation.XmlType;

/**
 * @author bebopJMM
 * 
 */
@XmlType (name = "AlignmentEnum", namespace = "java:org.rollinitiative.d20.entity")
public enum Alignment {

   LG("Lawful Good"), NG("Neutral Good"), CG("Chaotic Good"), LN("Lawful Neutral"), TN(
         "True Neutal"), CN("Chaotic Neutral"), LE("Lawful Evil"), NE("Neutral Evil"), CE(
         "Chaotic Evil");

   private final String longName;


   Alignment(String longName)
   {
      this.longName = longName;
   }


   public String longName()
   {
      return longName;
   }

}
