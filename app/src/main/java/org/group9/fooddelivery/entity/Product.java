package org.group9.fooddelivery.entity;

import lombok.Data;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Product {

   private Integer id;
   private String name;
   private Integer categoryId;
   private String description;
   private BigDecimal price;
   private Integer remaining;
   private String pictureUrl;
   private String pictureLocalPath;

   private Integer isSpicy;

   private Integer enabled;

   private Date createdTime;
   private Date updatedTime;
}
