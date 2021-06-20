package org.group9.fooddelivery.util;

import java.util.Calendar;

public class DateUtils {

   public static String getTimePeriodDisplayName() {
      int h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
      if (h <= 6) {
         return "凌晨";
      } else if (h <= 12) {
         return "上午";
      } else if (h <= 13) {
         return "中午";
      } else if (h <= 18) {
         return "下午";
      } else {
         return "晚上";
      }
   }

}
