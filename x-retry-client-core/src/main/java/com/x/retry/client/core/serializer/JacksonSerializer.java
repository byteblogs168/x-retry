package com.x.retry.client.core.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.x.retry.client.core.RetryArgSerializer;
import com.x.retry.common.core.util.JsonUtil;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author: www.byteblogs.com
 * @date : 2022-03-07 15:08
 */
@Component
public class JacksonSerializer implements RetryArgSerializer {

    @Override
    public String serialize(Object serializeInfo) {
        return JsonUtil.toJsonString(serializeInfo);
    }

    @Override
    public Object deSerialize(String infoStr, Class tClass, Method method) throws JsonProcessingException {

        Type[] paramTypes = method.getGenericParameterTypes();

        Object[] params = new Object[paramTypes.length];

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = JsonUtil.toJson(infoStr);
        for (int i = 0; i < paramTypes.length; i++) {
            params[i] = mapper.readValue(jsonNode.get(i).toString(), mapper.constructType(paramTypes[i]));
        }

        return params;
    }
}
