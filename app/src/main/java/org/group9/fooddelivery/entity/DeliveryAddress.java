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

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Integer getUserId() {
      return userId;
   }

   public void setUserId(Integer userId) {
      this.userId = userId;
   }

   public String getReceiverName() {
      return receiverName;
   }

   public void setReceiverName(String receiverName) {
      this.receiverName = receiverName;
   }

   public String getReceiverPhoneNumber() {
      return receiverPhoneNumber;
   }

   public void setReceiverPhoneNumber(String receiverPhoneNumber) {
      this.receiverPhoneNumber = receiverPhoneNumber;
   }

   public String getReceiverAddress() {
      return receiverAddress;
   }

   public void setReceiverAddress(String receiverAddress) {
      this.receiverAddress = receiverAddress;
   }

   public String getTag() {
      return tag;
   }

   public void setTag(String tag) {
      this.tag = tag;
   }
}
