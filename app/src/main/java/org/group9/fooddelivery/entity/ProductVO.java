package org.group9.fooddelivery.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductVO {

   private Integer id;
   private String name;
   private Integer categoryId;
   private String description;
   private BigDecimal price;
   private Integer remaining;
   private String picturePath;

   private Integer isSpicy;

   private Integer enabled;

   private Date createdTime;
   private Date updatedTime;

}
