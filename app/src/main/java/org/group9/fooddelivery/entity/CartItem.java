package org.group9.fooddelivery.entity;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CartItem {

   @Getter(AccessLevel.NONE)
   @Setter(AccessLevel.NONE)
   private Cart ownerCart;

   @NotNull(message = "产品ID不能为空")
   private Integer productId;
   private String name;
   private String pictureUrl;
   private Integer categoryId;

   @Min(0)
   private BigDecimal price;
   @Min(0)
   private Integer quantity;

   public CartItem() {
      this(null);
   }

   public CartItem(Cart ownerCart) {
      this.ownerCart = ownerCart;
   }

   public void addOne() {
      setQuantity(quantity + 1);
   }

   public void addN(int n) {
      setQuantity(quantity + n);
   }

   /**
    * 设置产品数量
    * 如果产品数量为0，则该实体将被自动移除
    */
   public void setQuantity(Integer quantity) {
      if (quantity < 0) {
         quantity = 0;
      }

      this.quantity = quantity;
      if (quantity == 0) {
         if (ownerCart != null) {
            ownerCart.removeCartItem(productId);
         }
      }
   }

   public BigDecimal getTotalPrice() {
      return price.multiply(BigDecimal.valueOf(quantity));
   }

}
