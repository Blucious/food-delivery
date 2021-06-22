package org.group9.fooddelivery.context;

import java.nio.file.Paths;

/**
 * API相关常量定义
 */
public class ApiContants {
   public static final String server_url = "http://10.0.2.2:8080";

   // 用户相关
   public static final String api_user_register = "/api/user/register";
   public static final String api_user_login = "/api/user/login";
   public static final String api_user_logout = "/api/user/logout";
   public static final String api_user_getCurrentUser = "/api/user/getCurrentUser";
   public static final String api_user_isEmailExists = "/api/user/isEmailExists";
   public static final String api_user_changeProfile = "/api/user/changeProfile";
   public static final String api_user_changePasswordByCurrentPassword = "/api/user/changePasswordByCurrentPassword";

   // 收货地址相关
   public static final String api_deliveryAddress_save = "/api/deliveryAddress/save";
   public static final String api_deliveryAddress_deleteById = "/api/deliveryAddress/deleteById";
   public static final String api_deliveryAddress_updateById = "/api/deliveryAddress/updateById";
   public static final String api_deliveryAddress_listForCurrentUser = "/api/deliveryAddress/listForCurrentUser";

   // 产品类别相关
   public static final String api_productCategory_save = "/api/productCategory/save";
   public static final String api_productCategory_list = "/api/productCategory/list";

   // 产品相关
   public static final String api_product_list = "/api/product/list";

   // 购物车相关
   public static final String api_cart_save = "/api/cart/save";
   public static final String api_cart_deleteByProductId = "/api/cart/deleteByProductId";
   public static final String api_cart_deleteAllForCurrentUser = "/api/cart/deleteAllForCurrentUser";
   public static final String api_cart_updateByProductId = "/api/cart/updateByProductId";
   public static final String api_cart_listForCurrentUser = "/api/cart/listForCurrentUser";
   public static final String api_cart_detailForCurrentUser = "/api/cart/detailForCurrentUser";

   // 订单相关
   public static final String api_order_createOrder = "/api/order/createOrder";
   public static final String api_order_setOrderCanceledById = "/api/order/setOrderCanceledById";
   public static final String api_order_setOrderReceivedById = "/api/order/setOrderReceivedById";
   public static final String api_order_getOrderById = "/api/order/getOrderById";
   public static final String api_order_listForCurrentUser = "/api/order/listForCurrentUser";
   public static final String api_order_listOrderItemsById = "/api/order/listOrderItemsById";
   public static final String api_order_OrderListItemById = "/api/order/OrderListItemById";

   /**
    * 将服务器地址与api路径与进行拼接，得到完整的api路径
    */
   public static String apiUrl(String path) {
      return Paths.get(server_url, path).toString();
   }

   public static String resUrl(String path) {
      return Paths.get(server_url, path).toString();
   }

}
