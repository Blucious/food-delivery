package org.group9.fooddelivery.entity;

import lombok.Data;

@Data
public class DeliveryAddress {

   private Integer id;
   private Integer userId;
   private String receiverName;
   private String receiverPhoneNumber;
   private String receiverAddress;
   private String tag;

}
