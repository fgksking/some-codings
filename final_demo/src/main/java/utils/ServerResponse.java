package utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

public class ServerResponse <T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ServerResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ServerResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ServerResponse(int code) {
        this.code = code;
    }

    public ServerResponse(int code,T data) {
        this.data = data;
        this.code=code;
    }

    /**
     *
     * @param msg  返回的信息
     * @param data  数据
     * @return     1状态码 2msg信息 3数据
     * @param <T>
     */
    public static <T> ServerResponse<T> createSuccess(String msg, T data){
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(),msg,data);
    }
    public static <T> ServerResponse<T> createSuccess(T data){
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(),data);
    }

    public static <T> ServerResponse<T> createSuccess(String msg){
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static <T> ServerResponse<T> createSuccess() {
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode());
    }
   //失败的情况
    public static <T> ServerResponse<T> createError(int code, String msg) {
        return new ServerResponse<>(code, msg);
    }
    public static <T> ServerResponse<T> createError() {
        return new ServerResponse<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }
    public static <T> ServerResponse<T> createError(String msg) {
        return new ServerResponse<>(ResponseCode.ERROR.getCode(), msg);
    }

    public static <T> ServerResponse<T> createAGAIN(String msg){
        return  new ServerResponse<>(ResponseCode.PROBLEM.getCode(),msg);
    }

    public String writeJson(ServerResponse<T> response){
        String json = JSON.toJSONString(this, SerializerFeature.PrettyFormat);
        return json;
    }



}
