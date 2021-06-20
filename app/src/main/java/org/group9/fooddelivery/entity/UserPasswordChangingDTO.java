package org.group9.fooddelivery.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserPasswordChangingDTO {

   @NotNull(message = "当前密码不能为空")
   private String currentPassword;
   @NotNull(message = "新密码不能为空")
   private String newPassword;

}
