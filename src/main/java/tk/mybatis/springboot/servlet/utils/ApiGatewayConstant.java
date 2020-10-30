package tk.mybatis.springboot.servlet.utils;

import java.util.HashMap;
import java.util.Map;

public class ApiGatewayConstant {
    public static final String API_PARAMETER_PATH = "com.jlb.common.gw.annotations.dto.ApiParameterDTO";
    public static final String API_LOGININFO_PATH = "com.jlb.common.gw.annotations.dto.ApiLoginInfoDTO";
    public static final String EMPTY_STR = "";
    public static final String LEFT_SLASH = "/";
    public static final String COMMA = ",";
    public static final String CHARACTER_ENCODING = "UTF-8";
    public static final String CONTENT_TYPE = "text/json;charset=" + CHARACTER_ENCODING;
    public static final String JLB_SOURCE = "JLBSOURCE";
    public static final String DEVICE_TYPE = "DEVICETYPE";
    public static final Map<String, Object> typeMap = new HashMap<>();

    static {
        typeMap.put("long", 0);
        typeMap.put("int", 0);
        typeMap.put("boolean", false);
        typeMap.put("double", 0.0d);
        typeMap.put("byte", 0);
        typeMap.put("char", '\u0000');
        typeMap.put("short", 0);
        typeMap.put("float", 0.0f);
        typeMap.put("java.lang.String", "");
        typeMap.put("java.lang.Long", 0);
        typeMap.put("java.lang.Integer", 0);
        typeMap.put("java.lang.Boolean", false);
        typeMap.put("java.lang.Double", 0.0d);
        typeMap.put("java.lang.Byte", 0);
        typeMap.put("java.lang.Character", '\u0000');
        typeMap.put("java.lang.Short", 0);
        typeMap.put("java.lang.Float", 0.0f);
    }
}
