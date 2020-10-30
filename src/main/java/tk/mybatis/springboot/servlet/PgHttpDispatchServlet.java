package tk.mybatis.springboot.servlet;

import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import tk.mybatis.springboot.dto.*;
import tk.mybatis.springboot.service.DubboInterfaceService;
import tk.mybatis.springboot.service.DubboMethodService;
import tk.mybatis.springboot.service.UpStreamInstanceService;
import tk.mybatis.springboot.service.UpStreamService;
import tk.mybatis.springboot.servlet.utils.DubboUtil;
import tk.mybatis.springboot.servlet.utils.HttpServletUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "pgHttpDispatchServlet", urlPatterns = "/api/http/*")
public class PgHttpDispatchServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(PgHttpDispatchServlet.class);

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Date.class, (JsonSerializer<Date>) (date, type, jsonSerializationContext) -> new JsonPrimitive(date.getTime()))
            .create();



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("GET the request uri [{}]", req.getRequestURI());
        }
        // 分段获取jlbApi标签配置的domain和url值
        List<UpstreamInstanceDTO> apiInfoResult = getApiInfo(req, resp);

        if ( apiInfoResult == null) {
            // 如果接口不存在（未上报).返回-99 errorCode.
            Map<String, String> result = new HashMap<>();
            result.put("code", "11000001");
            result.put("msg", "访问接口不存在");
            result.put("data", null);
            writeResponse(resp, result);
            return;
        }
        // 如果需要登录则从会话中获取用户ID
        // 根据查询到的接口信息配置泛化的接口信息。
//        DubboUtil dubboUtil = new DubboUtil();
//        // 获取前端传递参数列表
        StringBuffer params = new StringBuffer();
        Map<String, String> parameters = HttpServletUtil.getReqParasGet(req);
        int i = 0;
        for(String key:parameters.keySet()){//keySet获取map集合key的集合  然后在遍历key即可
            String value = parameters.get(key).toString();//
            if(i==0) {
                params.append("?");
            }else {
                params.append("&");
            }
                params.append(key);
                params.append("=");
                params.append(value);
        }
//        GenericService genericService = dubboUtil.getGenericService(apiInfoResult);
//        Object result = dubboUtil.genericCallRpc(genericService, getMethodInfo(req,resp), parameters);
        writeResponse(resp, JSON.parse(httpGet(apiInfoResult.get(0).getIpAddress(),params.toString())));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("POST the request uri [{}]", req.getRequestURI());
        }
        List<UpstreamInstanceDTO> apiInfoResult = getApiInfo(req, resp);
        if ( apiInfoResult == null) {
            // 如果接口不存在（未上报).返回-99 errorCode.
            Map<String, String> result = new HashMap<>();
            result.put("code", "11000001");
            result.put("msg", "访问接口不存在");
            result.put("data", null);
            writeResponse(resp, result);
            return;
        }
        // 根据查询到的接口信息配置泛化的接口信息。
        DubboMethodDTO methodDTO = getMethodInfo(req,resp);
        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        JSONObject json = JSONObject.parseObject(sb.toString());
//        String finalValue = "";
//        if (dubboUtil.isObject(methodDTO.getParameterType())) {
//            finalValue = "{ '" + methodDTO.getParameterName() + "':" + sb + "}";
//        }
//        if (!StringUtils.isEmpty(finalValue)) {
//            reqParameters = JSON.parseObject(finalValue, Map.class);
//        }
        writeResponse(resp, JSON.parse(HttpPostWithJson(apiInfoResult.get(0).getIpAddress(),json.toString())));
    }

    private List<UpstreamInstanceDTO> getApiInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());

        UpStreamService upStreamService = (UpStreamService)context.getBean("upStreamService");
        UpStreamInstanceService upStreamInstanceService = (UpStreamInstanceService)context.getBean("upStreamInstanceService");
        String[] urlParas = HttpServletUtil.getUrlPath(req);
        UpStreamDTO apiInfoDTO = new UpStreamDTO();
        apiInfoDTO.setUrl(urlParas[1]);
        Result mResult = upStreamService.getUrl(apiInfoDTO );
        if(!mResult.success()){
            return null;
        }
        UpStreamDTO upStreamDTO = null;
        // 查询domain和url值对应的接口信息
        if(mResult.getData()!=null) {
            upStreamDTO = (UpStreamDTO) mResult.getData();
            UpstreamInstanceDTO upstreamInstanceDTO = new UpstreamInstanceDTO();
            upstreamInstanceDTO.setUpStreamId(upStreamDTO.getId());
            Result result = upStreamInstanceService.getList(upstreamInstanceDTO);
            if(result!=null&&result.getData()!=null){
                       return  (List<UpstreamInstanceDTO>)result.getData();
            }
        }
        return null;
    }



    private DubboMethodDTO getMethodInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());

        DubboMethodService dubboMethodService = (DubboMethodService)context.getBean("dubboMethodService");
        DubboInterfaceService dubboInterfaceService = (DubboInterfaceService)context.getBean("dubboInterfaceService");
        String[] urlParas = HttpServletUtil.getUrlPath(req);
        DubboInterfaceDTO apiInfoDTO = new DubboInterfaceDTO();
        apiInfoDTO.setUrl(urlParas[1]);
        Result mResult =(Result) dubboMethodService.getByUrl(apiInfoDTO.getUrl());
        DubboMethodDTO dubboMethodDTO = null;
        // 查询domain和url值对应的接口信息
        if(mResult.getData()!=null) {
            dubboMethodDTO = (DubboMethodDTO) mResult.getData();
            return dubboMethodDTO;
        }
        return null;
    }





    private void writeResponse(HttpServletResponse resp, Object data) throws IOException {
        resp.setContentType("text/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        String json = GSON.toJson(data);
        out.println(json);
        out.flush();
        out.close();
    }


    public static String HttpPostWithJson(String url, String json) {
        String returnValue = "这是默认返回值，接口调用失败";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try{
            //第一步：创建HttpClient对象
            httpClient = HttpClients.createDefault();

            //第二步：创建httpPost对象
            HttpPost httpPost = new HttpPost(url);

            //第三步：给httpPost设置JSON格式的参数
            StringEntity requestEntity = new StringEntity(json,"utf-8");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(requestEntity);

            //第四步：发送HttpPost请求，获取返回值
            returnValue = httpClient.execute(httpPost,responseHandler); //调接口获取返回值时，必须用此方法

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //第五步：处理返回值
        return returnValue;
    }

    public static String httpGet(String url,String params){
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet httpGet = new HttpGet(url +  params);
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 配置信息
            RequestConfig requestConfig = RequestConfig.custom()
                    // 设置连接超时时间(单位毫秒)
                    .setConnectTimeout(5000)
                    // 设置请求超时时间(单位毫秒)
                    .setConnectionRequestTimeout(5000)
                    // socket读写超时时间(单位毫秒)
                    .setSocketTimeout(5000)
                    // 设置是否允许重定向(默认为true)
                    .setRedirectsEnabled(true).build();

            // 将上面的配置信息 运用到这个Get请求里
            httpGet.setConfig(requestConfig);

            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);

            String content = EntityUtils.toString(response.getEntity(), "UTF-8");

            return content;

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    return null;
    }



}


