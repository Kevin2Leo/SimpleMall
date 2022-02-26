package com.cskaoyan.mall.model.vo.adminVo;

/**
 * 展示层 对象
 */
public class BaseRespVo {


    private Integer code;//自己的前后端应用 ： 自定义的状态码 → 通常前端根据该状态码做不同的处理
    private Object data;
    private String message;//告诉前端请求的消息
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    //有参构造函数
    public BaseRespVo(Integer code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseRespVo() {
    }


    public static BaseRespVo ok(){
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setCode(0);
        return baseRespVo;
    }
    public static BaseRespVo ok(Object data){
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setCode(0);
        baseRespVo.setData(data);
        return baseRespVo;
    }
//
//    public static BaseRespVo ok(Object data,String msg){
//        BaseRespVo baseRespVo = new BaseRespVo();
//        baseRespVo.setData(data);
//        baseRespVo.setMessage("登录成功");
//        baseRespVo.setCode(0);
//        return baseRespVo;
//    }
//
//
//
    public static BaseRespVo error(String message){
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setCode(10000);
        baseRespVo.setMessage(message);
        return baseRespVo;
    }
//
//    public static BaseRespVo fail(){
//        BaseRespVo baseRespVo = new BaseRespVo();
//        baseRespVo.setCode(10000);
//        baseRespVo.setMessage("失败");
//        return baseRespVo;
//    }

}
