package tk.mybatis.springboot.enums;

import java.io.Serializable;

public enum ERROR_ENUM{
    SUCCESS(0,"success"),
    EMPTY_PARAMETER(1,"参数为空"),
    DUPLICATED_MOBILE(1005,"手机号码重复"),
    WRONG_MOBILE_PATTERN(1006,"手机号码格式错误"),
    EMPETY_PARAMETER_EXCEPTION(9001,"传入参数为空"),  WRONG_PASS(1004,"密码错误，请重新输入"),SYSTEM_ERROR(9999,"系统内部错误"),  VALID_CODE_ERROR(1002,"验证码错误，请重新输入"),  SEMD_VALID_CODE_BEFORE_LOGIN(1001,"登录前请先发送验证码"),  USER_NOT_EXIST_ERROR_STATUS(1003,"用户不存在或异常状态"),

    SERVER_ERROR(2,"服务器错误");

    private final int code;

    private final String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ERROR_ENUM(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
