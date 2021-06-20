package org.group9.fooddelivery.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProductCategoryDTO {

   @NotBlank(message = "产品类别名称不能为空")
   private String name;
}
