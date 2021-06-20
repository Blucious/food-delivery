package org.group9.fooddelivery.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DeliveryAddressDTO {

   @NotBlank(message = "收货人姓名不能为空")
   private String receiverName;
   @NotBlank(message = "收货人电话号码不能为空")
   private String receiverPhoneNumber;
   @NotBlank(message = "收货地址不能为空")
   private String receiverAddress;
   @NotBlank(message = "标签不能为空")
   private String tag;

}
