package org.group9.fooddelivery.entity;

import lombok.Data;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order {

   private Integer id;
   private String serialNumber;
   private Integer userId;

   // 订单状态：0配送中,1已送达,2已收货,3已取消
   public static final int STATE_DELIVERING = 0;
   public static final int STATE_DELIVERED = 1;
   public static final int STATE_RECEIVED = 2;
   public static final int STATE_CANCELED = 3;
   private Integer state;

   private String receiverName;
   private String receiverPhoneNumber;
   private String receiverAddress;
   private String remark;
   private Integer tablewares;

   public static final BigDecimal DEFAULT_DELIVERY_FEE = BigDecimal.valueOf(9);
   private BigDecimal deliveryFee;
   private BigDecimal totalPrice;
   private Date finishTime;

   private Date createdTime;
   private Date updatedTime;

   public void setState(Integer state) {
      if (state != null) {
         if (!(state >= 0 && state <= 3)) {
            throw new ConstraintViolationException("无效订单状态值：" + state, null);
         }
      }
      this.state = state;
   }

   public String getDisplayState() {
      if (state == null) {
         return "";
      }
      switch (state) {
         case STATE_DELIVERING:
            return "配送中";
         case STATE_DELIVERED:
            return "已送达";
         case STATE_RECEIVED:
            return "已收货";
         case STATE_CANCELED:
            return "已取消";
         default:
            return "";
      }
   }

   public boolean isCancelable() {
      return state == STATE_DELIVERING
          || state == STATE_DELIVERED;
   }

   public boolean isFinished() {
      return state == STATE_RECEIVED
          || state == STATE_CANCELED;
   }

}
