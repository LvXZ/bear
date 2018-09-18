package com.group3.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 * @ClassName:
 * @Description:
 * @author: lvxz
 * @create: 2018-09-01 16:27
 */


public class ParamsJSONUtil {

    public static Integer getInteger(String params, String objectName) {
        return Integer.valueOf(getString(params,objectName));
    }

    public static String getString(String params, String objectName) {

        if (params != null){
            params = params.trim();
        }else{
            return null;
        }
        if ("".equals(params)){
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode paramJson = null;
        try {
            paramJson = objectMapper.readTree(params);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        String getObject = paramJson.get(objectName).textValue();
        return getObject;
    }
}
