package org.group9.fooddelivery.entity;

import lombok.Data;

@Data
public class SessionVO {

   private String id;
   private Long creationTime;
   private Long lastAccessedTime;

}
