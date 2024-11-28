package im.hxmeet.Xfile.core.filter;

import cn.hutool.core.util.ObjectUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsUtils;

import java.io.IOException;

/**
 * 开启跨域支持. 一般用于开发环境, 或前后端分离部署时开启.
 */
@WebFilter(urlPatterns = "/*")
public class CorsFilter {
    @Override
    /**这个方法是执行过滤器逻辑的核心。当请求到达服务器时，doFilter 方法会被调用。
     参数：
     ServletRequest request: 代表客户端请求。
     ServletResponse response: 代表服务器响应。
     FilterChain chain: 提供了继续处理请求的能力。*/
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)throws IOException, ServletException{
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        //从请求中获取 Origin 头，表示请求来源。
        String header = httpServletRequest.getHeader(HttpHeaders.ORIGIN);
        //设置 Access-Control-Allow-Origin 头，如果 Origin 不为空则使用它，否则允许所有来源（使用 *）。
        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, ObjectUtil.defaultIfNull(header, "*"));
       //指定允许的请求头。在这里列出了多个头部信息，包括 Origin、Content-Type 等。
        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Origin, X-Requested-With, Content-Type, Accept, zfile-token, axios-request");
        //指定允许的 HTTP 请求方法，包括 GET、POST、PUT、DELETE 和 OPTIONS。
        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE, OPTIONS");
       //设置是否允许凭证（如 Cookies）的跨域请求，这里设置为不允许。
        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "false");
        //设置预检请求结果的有效期为 600 秒，表示浏览器在这段时间内可以缓存结果。
        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "600");

        /**CorsUtils.isPreFlightRequest() 方法检查当前请求是否为预检请求（即使用 OPTIONS 方法的请求），这是浏览器在发送跨域请求前进行的检查。
         *
         * OPTIONS请求即预检请求，可用于检测服务器允许的http方法。当发起跨域请求时，由于安全原因，触发一定条件时浏览器会在正式请求之前自动先发起OPTIONS请求，即CORS预检请求，服务器若接受该跨域请求，浏览器才继续发起正式请求。
         * PreFlight，一个cors预检请求，属于options请求。该请求会在浏览器认为即将要执行的请求可能会对服务器造成不可预知的影响时，由浏览器自动发出。
         * 利用预检请求，浏览器能够知道当前的服务器是否允许执行即将要进行的请求，只有获得了允许，浏览器才会真正执行接下来的请求。
         *
         * 如果不是预检请求，就调用 chain.doFilter(...) 来继续处理请求链，传递给下一个过滤器或目标资源。
         * 如果是预检请求，则不调用 chain.doFilter(...)，即直接返回，不继续请求处理。*/
        if(!CorsUtils.isPreFlightRequest(httpServletRequest)){
            chain.doFilter(httpServletRequest, httpServletResponse);
        }

    }
}
