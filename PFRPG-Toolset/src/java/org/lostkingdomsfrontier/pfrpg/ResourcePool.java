/**
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
package org.lostkingdomsfrontier.pfrpg;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A ResourcePool is used to track the expending and replenishing of a resource, such as hit points
 * 
 * @author bebopjmm
 * 
 */
public class ResourcePool
{
   static final Log LOG_ = LogFactory.getLog(ResourcePool.class);

   /**
    * Maximum number of tracked resources in the pool.
    */
   private int poolMax_;

   /**
    * Current number of tracked resources in the pool
    */
   private int poolCurrent_;

   /**
    * Current amount of allowed resources over the maximum
    */
   private int surplus_ = 0;

   /**
    * True if a surplus of resources is allowed for this pool
    */
   private boolean isSurplusAllowed_ = false;

   /**
    * True if a deficit of resources is allowed for this pool
    */
   private boolean isDeficitAllowed_ = false;


   /**
    * Constructs a new ResourcePool
    * 
    * @param max Maximum number of resources the pool can contain
    * @param isFilled If true, the pool will be filled to maximum capacity, otherwise the pool will
    *           be empty.
    */
   public ResourcePool(int max, boolean isFilled)
   {
      this.poolMax_ = max;
      if (isFilled) {
         this.poolCurrent_ = max;
      }
      else {
         this.poolCurrent_ = 0;
      }
   }


   /**
    * @return the maximum capacity of the pool.
    */
   public int getPoolMax()
   {
      return poolMax_;
   }


   /**
    * @return current number of resources in the pool.
    */
   public int getPoolCurrent()
   {
      return poolCurrent_;
   }


   /**
    * @return the current surplus of resources
    */
   public int getSurplus()
   {
      return surplus_;
   }


   /**
    * @return true if a surplus is allowed, otherwise false.
    */
   public boolean isSurplusAllowed()
   {
      return isSurplusAllowed_;
   }


   /**
    * @param isSurplusAllowed the isSurplusAllowed to set
    */
   public void setSurplusAllowed(boolean isSurplusAllowed)
   {
      this.isSurplusAllowed_ = isSurplusAllowed;
   }


   /**
    * @return true if a deficit is allowed, otherwise false.
    */
   public boolean isDeficitAllowed()
   {
      return isDeficitAllowed_;
   }


   /**
    * @param isDeficitAllowed the isDeficitAllowed to set
    */
   public void setDeficitAllowed(boolean isDeficitAllowed)
   {
      this.isDeficitAllowed_ = isDeficitAllowed;
   }


   /**
    * This method changes the maximum capacity of the pool, possibly adjusting the current value by
    * the same delta.
    * 
    * @param delta amount to change the capacity
    * @param changeCurrent if true, the current value will be changed by the same delta
    * @return the updated maximum capacity of the pool
    * @throws ResourceException when
    */
   public int updateMax(int delta, boolean changeCurrent) throws ResourceException
   {
      LOG_.debug("Max size [" + poolMax_ + "] update (" + delta + ") changeCurrent = "
            + changeCurrent);
      int oldMax = this.poolMax_;
      this.poolMax_ += delta;
      if (poolMax_ < 0) {
         this.poolMax_ = oldMax;
         throw new ResourceException();
      }
      if (changeCurrent) {
         if (delta > 0)
            replenish(delta);
         else
            expend(delta);
      }
      return this.poolMax_;
   }


   /**
    * This method adds the indicated surplus to the pool, optionally increasing the current value by
    * the same amount.
    * 
    * @param surplus amount to add to the allowed pool surplus
    * @param fill if true, the current value will be increased
    * @throws ResourceException when the pool does not allow a surplus.
    */
   public void addSurplus(int surplus, boolean fill) throws ResourceException
   {
      if (!isSurplusAllowed_) {
         throw new ResourceException();
      }

      this.surplus_ += surplus;
      if (fill) {
         replenish(surplus);
      }
   }


   /**
    * This method decreases the surplus by the indicated amount, optionally also decreasing the
    * current value by the same amount.
    * 
    * @param surplus the amount to remove from the allowed pool surplus
    * @param withdraw if true, the current value will be decreased
    * @throws ResourceException when the amount the remove exceeds the surplus of the pool.
    */
   public void removeSurplus(int surplus, boolean withdraw) throws ResourceException
   {
      if (surplus > this.surplus_) {
         throw new ResourceException();
      }

      this.surplus_ -= surplus;
      if (withdraw) {
         expend(surplus);
      }
   }


   /**
    * Removes the indicate count from the pool. Incurring a deficit when one is not allowed
    * (isDeficitAllowed) will result in a ResourceException being thrown.
    * 
    * @param count the amount to expend from the pool.
    * @return the amount expended
    * @throws ResourceException when count exceeds the current pool size and deficits are not
    *            allowed.
    */
   public synchronized int expend(int count) throws ResourceException
   {
      if ((count > poolCurrent_) && (!isDeficitAllowed_)) {
         throw new ResourceException();
      }

      poolCurrent_ -= count;
      return count;
   }


   /**
    * Adds the indicated count back to the pool.
    * 
    * @param count the amount to replenish into the pool.
    * @return the overflow that could not be added to the pool.
    */
   public synchronized int replenish(int count)
   {
      int max = poolMax_ + surplus_;
      poolCurrent_ += count;
      if (poolCurrent_ <= max) {
         return 0;
      }
      else {
         int overflow = poolCurrent_ - max;
         poolCurrent_ = max;
         return (overflow);
      }

   }
}
