package org.group9.fooddelivery.ui;

/**
 * UI常量
 */
public class UiConstants {

   // Bundle Key
   // 指明来源Activity，即当前Activity是谁启动的
   public static final String REFERRER_ACTIVITY = "referrer_activity";
   // 选择模式
   public static final String SELECTION_MODE = "selection_mode";
   // 选择结果
   public static final String SELECTION_RESULT = "SELECTION_RESULT";

   // Request Code
   public static final int REQUEST_CODE_IGNORE = -1;

   public static final int DEFAULT_REQUEST_CODE = -1;

   public static final int DEFAULT_RESULT_CODE = -1;
   public static final int RESULT_CODE_DELIVERY_ADDRESS_SELECTED = 0xff01;
}
