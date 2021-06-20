package org.group9.fooddelivery.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 将响应结果封装一个类
 */
@Data
public class Result {
   /**
    * 响应码
    */
   private Integer code;

   /************* 响应码 与其 默认信息 定义 *************/
   public static final int CODE_OK = 200;
   public static final int CODE_BAD_REQUEST = 400;
   public static final int CODE_UNAUTHORIZED = 401;
   public static final int CODE_FORBIDDEN = 403;
   public static final int CODE_NOT_FOUND = 404;
   public static final int CODE_METHOD_NOT_ALLOWED = 405;
   public static final int CODE_UNPROCESSABLE_ENTITY = 422;
   public static final int CODE_ERROR = 500;

   private static final Map<Integer, String> CODE_DEFAULT_DESCRIPTIONS;

   static {
      Map<Integer, String> m = new HashMap<>();
      CODE_DEFAULT_DESCRIPTIONS = m;
      m.put(CODE_OK, "请求成功");
      m.put(CODE_BAD_REQUEST, "客户端请求的语法错误");
      m.put(CODE_UNAUTHORIZED, "缺乏目标资源要求的身份验证凭证");
      m.put(CODE_FORBIDDEN, "服务器理解请求客户端的请求，但是拒绝执行此请求");
      m.put(CODE_NOT_FOUND, "无此资源");
      m.put(CODE_METHOD_NOT_ALLOWED, "Method Not Allowed");
      m.put(CODE_UNPROCESSABLE_ENTITY, "请求格式正确，但是由于含有语义错误，无法响应");
      m.put(CODE_ERROR, "服务器内部错误，无法完成请求");
   }
   /************* /响应码 与其 默认信息 定义 *************/

   /**
    * 响应消息
    */
   private String msg;

   /**
    * 响应数据
    */
   private Object data;

   /************* 便捷构造方法 *************/
   public static Result with(Integer code) {
      Result r = new Result();
      r.setCode(code);
      return r;
   }

   public static Result with(Integer code, String msg) {
      return new Result()
          .code(code)
          .msg(msg);
   }

   public static Result withData(Integer code, Object data) {
      return new Result()
          .code(code)
          .data(data);
   }
   /************* /便捷构造方法 *************/

   /************* 基于响应码的便捷构造方法 *************/
   public static Result ok() {
      return new Result()
          .code(CODE_OK);
   }

   public static Result unauthorized() {
      return new Result()
          .code(CODE_UNAUTHORIZED);
   }

   public static Result unprocessableEntity() {
      return new Result()
          .code(CODE_UNPROCESSABLE_ENTITY);
   }

   public static Result forbidden() {
      return new Result()
          .code(CODE_FORBIDDEN);
   }

   public static Result notFound() {
      return new Result()
          .code(CODE_NOT_FOUND);
   }

   public static Result methodNotAllowed() {
      return new Result()
          .code(CODE_METHOD_NOT_ALLOWED);
   }

   public static Result badRequest() {
      return new Result()
          .code(CODE_BAD_REQUEST);
   }

   /************* /基于响应码的便捷构造方法 *************/

   public void setCode(Integer code) {
      this.code = code;
      if (msg == null) {
         msg = CODE_DEFAULT_DESCRIPTIONS.get(code);
      }
   }

   public Result code(Integer code) {
      setCode(code);
      return this;
   }

   public Result data(Object data) {
      setData(data);
      return this;
   }

   public Result msg(String msg) {
      setMsg(msg);
      return this;
   }

   public MapDataBuilder buildMapData() {
      return new MapDataBuilder();
   }

   public class MapDataBuilder {
      private final Map<String, Object> map;

      public MapDataBuilder() {
         map = new HashMap<>();
      }

      public MapDataBuilder put(String key, Object value) {
         map.put(key, value);
         return this;
      }

      public Result finish() {
         Result.this.setData(map);
         return Result.this;
      }
   }
}
