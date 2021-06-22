package org.group9.fooddelivery.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
public class Cart {

   /**
    * 购物车项表（productId 到 CartItem 的映射）
    */
   @Setter(AccessLevel.NONE)
   @Getter(AccessLevel.NONE)
   private Map<Integer, CartItem> cartItems;

   /**
    * 配送费
    */
   private BigDecimal deliveryFee = Order.DEFAULT_DELIVERY_FEE;

   public Cart() {
      cartItems = new LinkedHashMap<>();
   }

   public int size() {
      return cartItems.size();
   }

   public CartItem getCartItem(Integer productId) {
      return cartItems.get(productId);
   }

   public void setCartItems(List<CartItem> cartItems) {
      clear();
      for (CartItem cartItem : cartItems) {
         addCartItem(cartItem);
      }
   }

   public List<CartItem> getCartItems() {
      return new ArrayList<>(cartItems.values());
   }

   /**
    * 添加购物车项
    */
   public void addCartItem(CartItem cartItem) {

      Integer productId = cartItem.getProductId();
      CartItem existedCartItem = cartItems.get(productId);
      if (existedCartItem != null) {
         existedCartItem.addOne();
      } else {
         cartItems.put(productId, cartItem);
      }
   }

   /**
    * 删除购物车项，通过产品ID
    */
   public boolean removeCartItem(Integer productId) {
      return cartItems.remove(productId) != null;
   }

   /**
    * 清空购物车
    */
   public void clear() {
      cartItems.clear();
   }

   /**
    * 获取总价格
    */
   public BigDecimal getProductsTotalPrice() {
      BigDecimal productsTotalPrice = BigDecimal.ZERO;
      for (CartItem cartItem : cartItems.values()) {
         productsTotalPrice = productsTotalPrice.add(cartItem.getTotalPrice());
      }
      return productsTotalPrice;
   }

   public BigDecimal getTotalPrice() {
      return getProductsTotalPrice()
          .add(deliveryFee);
   }

}
