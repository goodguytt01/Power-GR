package tk.mybatis.springboot.servlet.utils;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.mybatis.springboot.dto.DubboInterfaceDTO;
import tk.mybatis.springboot.dto.DubboMethodDTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DubboUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(tk.mybatis.springboot.servlet.utils.DubboUtil.class);

    public GenericService getGenericService(DubboInterfaceDTO apiInfoDTO) {
        // 连接注册中心配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName(apiInfoDTO.getDomain());
        RegistryConfig registry = new RegistryConfig();
        String zkAddr;
        if (!(apiInfoDTO.getZkAddr()).startsWith("zookeeper://")) {
            zkAddr = "zookeeper://" + apiInfoDTO.getZkAddr();
        } else {
            zkAddr = apiInfoDTO.getZkAddr();
        }
        registry.setAddress(zkAddr);
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setRetries(0);
        reference.setTimeout(5000);
        reference.setCluster("failfast");
        reference.setInterface(apiInfoDTO.getInterfaceAddr());
        reference.setGeneric(true);
        // 声明为泛化接口
//        reference.setVersion("*");
        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        return cache.get(reference);
    }

    public Object invokeRemoteRpc(GenericService genericService, DubboMethodDTO methodDTO, Object[] paras) {
        Object obj;
        if (StringUtils.isEmpty(methodDTO.getParameterName())) {
            obj = genericService.$invoke(methodDTO.getMethodName(),
                    new String[]{},
                    new Object[]{});
        } else {
            obj = genericService.$invoke(methodDTO.getMethodName(),
                    methodDTO.getParameterType().split(ApiGatewayConstant.COMMA),
                    paras);
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Invoke dubbo api ({})[{}] from application [{}] result: {}", methodDTO.getUrl(),
                    methodDTO.getUrl() + "#"+JSON.toJSONString(obj));
        }
        return removeMapKeyIfClass(obj);
    }

    public Object genericCallRpc(GenericService genericService,
                                 DubboMethodDTO apiInfoDTO, Map<String, String> reqParameters
                                ) {
        JSONObject allJson = new JSONObject();
        if (StringUtils.isEmpty(apiInfoDTO.getParameterName()) ||
                apiInfoDTO.getParameterName().split(ApiGatewayConstant.COMMA).length == 1) {
            Object[] paras = new Object[1];
            String type = apiInfoDTO.getParameterType();
            // 如果参数类型是对象且不是约定的网关dto类型
            if (ApiGatewayConstant.typeMap.get(type) == null || !type.equals(ApiGatewayConstant.API_PARAMETER_PATH)) {
                allJson.putAll(reqParameters);
                if (type.equals("java.util.List")) {
                    JSONArray array = (JSONArray) allJson.get(apiInfoDTO.getParameterName());
                    paras[0] = array;
                } else {
                    paras[0] = allJson.get(apiInfoDTO.getParameterName());
                    if(paras[0]==null&&!StringUtils.isEmpty(apiInfoDTO.getParameterName())){
                        paras[0] = new HashMap<>();
                    }
                }
                //如果是网关dto类型

                return invokeRemoteRpc(genericService, apiInfoDTO, paras);
            } else {
                int i = 0;
                String[] types = apiInfoDTO.getParameterType().split(ApiGatewayConstant.COMMA);
                Object[] values = new Object[apiInfoDTO.getParameterName().split(ApiGatewayConstant.COMMA).length];
                for (String key : apiInfoDTO.getParameterName().split(ApiGatewayConstant.COMMA)) {
                    //keySet获取map集合key的集合  然后在遍历key即

                    try {
                        String para = reqParameters.get(key);
                        if (!StringUtils.isEmpty(para)) {
                            values[i] = para;
                        }
                    } catch (Exception e) {
                        Object para = reqParameters.get(key);
                        if (para != null) {
                            values[i] = para;
                        }
                    }

                    i++;
                }
                return invokeRemoteRpc(genericService, apiInfoDTO, values);
            }
        }
        return  null;
    }

    //TODO  remove
    private Object removeMapKeyIfClass(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Map) {
            //对象删除
            Map<Object, Object> objMap = (Map<Object, Object>) object;
            objMap.remove("class");
            Set<Object> keys = objMap.keySet();
            for (Object key : keys) {
                Object value = objMap.get(key);
                Object fixValue = removeMapKeyIfClass(value);
                objMap.put(key, fixValue);
            }
            return objMap;
        } else if (object instanceof Collection) {
            //集合删除
            Collection<Object> c = (Collection) object;
            for (Object obj : c) {
                removeMapKeyIfClass(obj);
            }
            return c;
        } else if (object.getClass().isArray()) {
            //数组删除
            Object[] objs = (Object[]) object;
            for (Object obj : objs) {
                removeMapKeyIfClass(obj);
            }
            return objs;
        } else {//其他直接返回
            return object;
        }
    }

    public Object setDefalutValue(String type, String source, Long userId) {
        String resultType = "java.lang.Object";
        if (type.equals("java.lang.String")) {
            return "";
        } else if (type.equals("int")) {
            resultType = "java.lang.Integer";
        } else if (type.equals("long")) {
            resultType = "java.lang.Long";
        } else if (type.equals("double")) {
            resultType = "java.lang.Double";
        } else if (type.equals("float")) {
            resultType = "java.lang.Float";
        } else if (type.equals("byte")) {
            resultType = "java.lang.Byte";
        } else if (type.equals("char")) {
            resultType = "java.lang.Character";
        } else if (type.equals("short")) {
            resultType = "java.lang.Short";
        }
        try {
            Object obj = Class.forName(resultType).newInstance();
            obj = ObjectUtils.CONST(obj);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isObject(String type) {
        if (StringUtils.isEmpty(type) || "java.util.List".equals(type)) {
            return false;
        }
        if (!(type.split(",").length > 1 || type.equals("java.lang.String")
                || type.equals("long")
                || type.equals("int")
                || type.equals("boolean")
                || type.equals("double")
                || type.equals("byte")
                || type.equals("char")
                || type.equals("short")
                || type.equals("float"))) {
            return !(type.equals("java.lang.String")
                    || type.equals("java.lang.Long")
                    || type.equals("java.lang.Integer")
                    || type.equals("java.lang.Boolean")
                    || type.equals("java.lang.Double")
                    || type.equals("java.lang.Byte")
                    || type.equals("java.lang.Character")
                    || type.equals("java.lang.Short")
                    || type.equals("java.lang.Float"));
        }
        return false;
    }
}
