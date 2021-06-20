package org.group9.fooddelivery.entity;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderDTO {

   @NotNull(message = "缺少收货地址ID")
   private Integer deliveryAddressId;
   private String remark;
   @NotNull(message = "必须输入餐具数量")
   @Min(0)
   private Integer tablewares;

}
