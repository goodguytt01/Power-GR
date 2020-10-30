package tk.mybatis.springboot.dto;


import tk.mybatis.springboot.enums.ERROR_ENUM;

public class JsonResVo {
    private int code;
    private String errmsg;
    private Object data;

    public JsonResVo() {
    }

    public static JsonResVo buildSuccess() {
        return buildSuccess((Object)null);
    }

    public static JsonResVo buildSuccess(Object data) {
        return build(0, (String)null, data);
    }

    public static JsonResVo buildFail(ERROR_ENUM errorEnum) {
        return buildErrorResult(errorEnum.getCode(), errorEnum.getMsg());
    }

    public static JsonResVo buildErrorResult(int code, String errmsg) {
        return buildErrorResult(code, errmsg, (Object)null);
    }

    public static JsonResVo buildErrorResult(ERROR_ENUM errorEnum) {
        return buildErrorResult(errorEnum.getCode(), errorEnum.getMsg(), (Object)null);
    }

    public static JsonResVo buildErrorResult(int code, String errmsg, Object data) {
        return build(code, errmsg, data);
    }

    private static JsonResVo build(int code, String errmsg, Object data) {
        JsonResVo jsonResVo = new JsonResVo();
        jsonResVo.setCode(code);
        jsonResVo.setErrmsg(errmsg);
        jsonResVo.setData(data);
        return jsonResVo;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrmsg() {
        return this.errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}