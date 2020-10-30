package tk.mybatis.springboot.dto;




import tk.mybatis.springboot.enums.ERROR_ENUM;

import java.io.Serializable;

public class Result implements Serializable {
    private Object data;

    private int code;

    private String msg;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

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

    public Result(ERROR_ENUM error_enum, Object obj){
        this.code = error_enum.getCode();
        this.msg = error_enum.getMsg();
        this.data = obj;
    }

    public Result(ERROR_ENUM error_enum){
        this.code = error_enum.getCode();
        this.msg = error_enum.getMsg();
    }


    public Result(){
    }

    public Result(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public static Result success(Object obj){
        return new Result(ERROR_ENUM.SUCCESS,obj);
    }


    public static Result error(){
        return new Result(ERROR_ENUM.SYSTEM_ERROR);
    }

    public static Result emptyParameter(){
        return new Result(ERROR_ENUM.EMPTY_PARAMETER.getCode(),ERROR_ENUM.EMPTY_PARAMETER.getMsg());
    }

    public boolean success(){
        if(this.code==0){
            return true;
        }else{
            return false;
        }
    }
}
