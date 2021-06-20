package org.group9.fooddelivery.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserProfileDTO {

   private String phoneNumber;

   @NotBlank(message = "昵称不能为空")
   private String nickName;

   @NotBlank(message = "邮箱不能为空")
   private String email;
   @NotNull(message = "未输入密码")
   private String password;

}
