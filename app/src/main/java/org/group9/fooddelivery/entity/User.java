package org.group9.fooddelivery.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class User {

   private Integer id;
   private String email;
   private String password;
   private String phoneNumber;

   private String nickName;
   private String profilePicturePath;

   private Integer points;
   private BigDecimal balance;

   private Date createdTime;
   private Date updatedTime;

}
