package tk.mybatis.springboot.servlet.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


@Component
public class HttpServletUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(tk.mybatis.springboot.servlet.utils.HttpServletUtil.class);





    public static Map<String, String> getReqParasGet(HttpServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();
        Map<String, String> result = new HashMap<>();
        for (String key : map.keySet()) {
            //keySet获取map集合key的集合  然后在遍历key即可
            result.put(key, map.get(key)[0]);
        }
        return result;
    }

    public static String[] getUrlPath(HttpServletRequest request) {
        if (null == request) {
            return new String[]{};
        }
        String url = request.getRequestURL().toString();
        String[] parts = url.split(ApiGatewayConstant.LEFT_SLASH);
        String domain = ApiGatewayConstant.EMPTY_STR;
        String urlPart = ApiGatewayConstant.EMPTY_STR;
        for (int i = 0; i < parts.length; i++) {
            if (i == 4) {
                domain = parts[i];
            }
            if (i > 4) {
                urlPart = urlPart + ApiGatewayConstant.LEFT_SLASH + parts[i];
            }
        }
        return new String[]{domain, urlPart};
    }




    public static String getHeaderSource(HttpServletRequest request) {
        if (null == request) {
            return null;
        }
        Enumeration<String> enumer = request.getHeaderNames();
        while (enumer.hasMoreElements()) {
            String key = enumer.nextElement();
            String value = request.getHeader(key);
            if (key.equalsIgnoreCase(ApiGatewayConstant.JLB_SOURCE)) {
                return value;
            }
        }
        return null;
    }


    public  Map<String,String>  getHeaders(HttpServletRequest request) {
        if (null == request) {
            return null;
        }
        HashMap<String,String> map = new HashMap<>();
        Enumeration<String> enumer = request.getHeaderNames();
        while (enumer.hasMoreElements()) {
            String key = enumer.nextElement();
            if(key.equals("x-reqid")){
                key = "x-reqId";
            }
            String value = request.getHeader(key);
            map.put(key,value);
        }
        return map;
    }


}
