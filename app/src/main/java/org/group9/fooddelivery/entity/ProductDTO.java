package org.group9.fooddelivery.entity;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ProductDTO {
   @NotBlank(message = "产品名称不能为空")
   private String name;
   @NotNull(message = "缺少产品类别信息")
   private Integer categoryId;
   private String description;
   @NotNull(message = "缺少产品价格信息")
   @Min(0)
   private BigDecimal price;
   @Min(0)
   private Integer remaining;
   private String picturePath;

   private Integer isSpicy;

   private Integer enabled;

}
