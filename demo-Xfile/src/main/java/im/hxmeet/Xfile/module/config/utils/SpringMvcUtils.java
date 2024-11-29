package im.hxmeet.Xfile.module.config.utils;

import im.hxmeet.Xfile.core.util.RequestHolder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerMapping;

public class SpringMvcUtils {

    public static String getExtractPathWithinPattern() {
        HttpServletRequest httpServletRequest = RequestHolder.getRequest();
        String path = (String) httpServletRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);//这是当前请求路径在处理程序映射中对应的路径，通常是实际的请求 URL 路径
        String bestMatchPattern = (String) httpServletRequest.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);//这是与当前请求路径最佳匹配的处理程序映射模式（pattern）。这可以是之前定义的 URL 映射模式，如 /users/{id}。

        AntPathMatcher apm = new AntPathMatcher();
        return apm.extractPathWithinPattern(bestMatchPattern, path);
    }

}

