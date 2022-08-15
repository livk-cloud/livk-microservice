package com.livk.common.core.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * RequestUtils
 * </p>
 *
 * @author livk
 * @date 2022/8/15
 */
@UtilityClass
public class RequestUtils {

    public HttpServletRequest getRequest() {
        var requestAttributes = RequestContextHolder.getRequestAttributes();
        var servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        Assert.notNull(servletRequestAttributes, "attributes not null!");
        return servletRequestAttributes.getRequest();
    }

    public HttpSession getSession() {
        return RequestUtils.getRequest().getSession();
    }

    public String getParameter(String name) {
        return RequestUtils.getRequest().getParameter(name);
    }

    public String getHeader(String headerName) {
        return RequestUtils.getRequest().getHeader(headerName);
    }

    public Map<String, String> getHeaders() {
        var request = RequestUtils.getRequest();
        var map = new LinkedHashMap<String, String>();
        Iterator<String> iterator = request.getHeaderNames().asIterator();
        while (iterator.hasNext()) {
            var key = iterator.next();
            var value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    public Map<String, String> getParamMap(CharSequence delimiter) {
        Set<Map.Entry<String, String[]>> entrySet = RequestUtils.getRequest()
                .getParameterMap()
                .entrySet();
        return entrySet.stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> String.join(delimiter, entry.getValue())));
    }
}
