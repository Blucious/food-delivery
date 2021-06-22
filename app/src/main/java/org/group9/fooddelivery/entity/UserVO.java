package org.group9.fooddelivery.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserVO {

   private Integer id;
   private String email;
   private String phoneNumber;

   private String nickName;
   private String profilePicturePath;

   private Integer points;

   private Date createdTime;
   private Date updatedTime;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPhoneNumber() {
      return phoneNumber;
   }

   public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
   }

   public String getNickName() {
      return nickName;
   }

   public void setNickName(String nickName) {
      this.nickName = nickName;
   }

   public String getProfilePicturePath() {
      return profilePicturePath;
   }

   public void setProfilePicturePath(String profilePicturePath) {
      this.profilePicturePath = profilePicturePath;
   }

   public Integer getPoints() {
      return points;
   }

   public void setPoints(Integer points) {
      this.points = points;
   }

   public Date getCreatedTime() {
      return createdTime;
   }

   public void setCreatedTime(Date createdTime) {
      this.createdTime = createdTime;
   }

   public Date getUpdatedTime() {
      return updatedTime;
   }

   public void setUpdatedTime(Date updatedTime) {
      this.updatedTime = updatedTime;
   }
}
