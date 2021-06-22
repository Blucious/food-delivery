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

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getSerialNumber() {
      return serialNumber;
   }

   public void setSerialNumber(String serialNumber) {
      this.serialNumber = serialNumber;
   }

   public Integer getUserId() {
      return userId;
   }

   public void setUserId(Integer userId) {
      this.userId = userId;
   }

   public static int getStateDelivering() {
      return STATE_DELIVERING;
   }

   public static int getStateDelivered() {
      return STATE_DELIVERED;
   }

   public static int getStateReceived() {
      return STATE_RECEIVED;
   }

   public static int getStateCanceled() {
      return STATE_CANCELED;
   }

   public Integer getState() {
      return state;
   }

   public String getReceiverName() {
      return receiverName;
   }

   public void setReceiverName(String receiverName) {
      this.receiverName = receiverName;
   }

   public String getReceiverPhoneNumber() {
      return receiverPhoneNumber;
   }

   public void setReceiverPhoneNumber(String receiverPhoneNumber) {
      this.receiverPhoneNumber = receiverPhoneNumber;
   }

   public String getReceiverAddress() {
      return receiverAddress;
   }

   public void setReceiverAddress(String receiverAddress) {
      this.receiverAddress = receiverAddress;
   }

   public String getRemark() {
      return remark;
   }

   public void setRemark(String remark) {
      this.remark = remark;
   }

   public Integer getTablewares() {
      return tablewares;
   }

   public void setTablewares(Integer tablewares) {
      this.tablewares = tablewares;
   }

   public static BigDecimal getDefaultDeliveryFee() {
      return DEFAULT_DELIVERY_FEE;
   }

   public BigDecimal getDeliveryFee() {
      return deliveryFee;
   }

   public void setDeliveryFee(BigDecimal deliveryFee) {
      this.deliveryFee = deliveryFee;
   }

   public BigDecimal getTotalPrice() {
      return totalPrice;
   }

   public void setTotalPrice(BigDecimal totalPrice) {
      this.totalPrice = totalPrice;
   }

   public Date getFinishTime() {
      return finishTime;
   }

   public void setFinishTime(Date finishTime) {
      this.finishTime = finishTime;
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
