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
package org.lostkingdomsfrontier.pfrpg.encounter.combat.actions;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lostkingdomsfrontier.pfrpg.dice.Dice;
import org.lostkingdomsfrontier.pfrpg.encounter.Action;
import org.lostkingdomsfrontier.pfrpg.encounter.Combatant;
import org.lostkingdomsfrontier.pfrpg.entity.HitPoints.LifeState;

@XmlType(name = "AttackType", namespace = "java:org.rollinitiative.d20.combat")
public abstract class Attack implements Action
{
   static final Log LOG_ = LogFactory.getLog(Attack.class);

   protected Combatant attacker = null;

   protected Combatant defender = null;

   protected boolean isTouchAttack = false;
   
   int damageDice;
   int damageDie;
   int damageModifier;
   
   String name;
   
   protected Dice dice = Dice.getInstance();


   public Attack()
   {
   }


   public void setAttacker(Combatant attacker)
   {
      this.attacker = attacker;
   }


   public void setDefender(Combatant defender)
   {
      this.defender = defender;
   }


   public double calculate(Combatant combatant)
   {
      // Calculate near target, weapon drawn, etc...
      return 0.0;
   }
   
   


   /**
    * @return the name
    */
   public String getName()
   {
      return name;
   }


   /**
    * @param name the name to set
    */
   @XmlAttribute(required = true)
   public void setName(String name)
   {
      this.name = name;
   }


   /**
    * @return the isTouchAttack
    */
   public boolean isTouchAttack()
   {
      return isTouchAttack;
   }


   /**
    * @param isTouchAttack the isTouchAttack to set
    */
   @XmlAttribute(required = true)
   public void setTouchAttack(boolean isTouchAttack)
   {
      this.isTouchAttack = isTouchAttack;
   }

   

   /**
    * @return the damageDice
    */
   public int getDamageDice()
   {
      return damageDice;
   }


   /**
    * @param damageDice the damageDice to set
    */
   @XmlAttribute(required = true)
   public void setDamageDice(int damageDice)
   {
      this.damageDice = damageDice;
   }


   /**
    * @return the damageDie
    */
   public int getDamageDie()
   {
      return damageDie;
   }


   /**
    * @param damageDie the damageDie to set
    */
   @XmlAttribute(required = true)
   public void setDamageDie(int damageDie)
   {
      this.damageDie = damageDie;
   }


   /**
    * @return the damageModifier
    */
   public int getDamageModifier()
   {
      return damageModifier;
   }


   /**
    * @param damageModifier the damageModifier to set
    */
   @XmlAttribute(required = true)
   public void setDamageModifier(int damageModifier)
   {
      this.damageModifier = damageModifier;
   }


   public void execute()
   {
      String attackerName = attacker.getActor().getName();
      String defenderName = defender.getActor().getName();
      // competitive roles
      LOG_.info(attackerName + " is attacking " + defenderName);
      int playerA = attacker.meleeAttack();
      int playerB = defender.rollDefense(isTouchAttack);

      // determine outcome
      if (playerA >= playerB) {
         int dmg = dice.roll(1, 8, 0);
//         LifeState lifeState = defender.getActor().damage(dmg);
         LifeState lifeState = LifeState.ALIVE;
         LOG_.info("--" + attackerName + " hits " + defenderName + " for " + dmg);
         if (lifeState != LifeState.ALIVE) {
            LOG_.info("----" + defenderName + " is dead or dying...");
            // if (arena.getPartyList().contains(defender.getActor()))
            // arena.getPartyList().remove(defender.getActor());
            // if (arena.getHostileList().contains(defender.getActor()))
            // arena.getHostileList().remove(defender.getActor());
            // }
         }
         else if (playerB > playerA) {
            LOG_.info("--" + attackerName + " misses " + defenderName);
         }
      }
   }
}
