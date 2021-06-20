package org.group9.fooddelivery.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemVO {

   private Integer id;
   private Integer orderId;
   private Integer productId;
   private Integer quantity;

   /********* 冗余产品信息 *********/
   private String name;
   private Integer categoryId;
   private BigDecimal price;
   private String pictureUrl;
   /********* /冗余产品信息 *********/


}
