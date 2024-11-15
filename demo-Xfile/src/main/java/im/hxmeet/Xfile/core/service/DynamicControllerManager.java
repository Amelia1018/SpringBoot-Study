package im.hxmeet.Xfile.core.service;

import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Server
public class DynamicControllerManager {

    @Resource
    private RequestMappingHandlerMapping requestMappingHandlerMapping;///负责将 HTTP 请求映射到相应的处理方法。

    private RequestMappingInfo shortLinkMappingInfo; ///shortLinkMappingInfo 用于存储当前的请求映射信息。
                                                    /// 这是一个 RequestMappingInfo 对象，包含了请求路径和其他相关信息。

    ///shortLinkHandler：存储与短链接相关的处理器对象（控制器）。
    /// shortLinkMethod：存储与短链接相关的处理方法（即控制器中的具体方法）。
    private Object shortLinkHandler;
    private Method shortLinkMethod;

    ///初始化直接链接前缀路径
    /// /*参数：
    /// path：请求路径的前缀。
    /// handler：处理请求的控制器实例。
    /// method：处理请求的方法。
    /// 逻辑：
    /// 首先检查 shortLinkMappingInfo 是否已被初始化。如果已经初始化，抛出异常，防止重复注册。
    /// 使用提供的路径、处理器和方法创建 RequestMappingInfo 对象，并将其注册到 requestMappingHandlerMapping 中。这使得指定的路径可以处理相应的请求。*/
    public void initDirectLinkPrefixPath(String path, Object handler, Method method){

        if(shortLinkMappingInfo != null){
            throw new RuntimeException("请勿重复初始化 DirectLinkPrefixPath.");
        }
        shortLinkMappingInfo = RequestMappingInfo.paths(path + "/{storageKey}/**").build();
        shortLinkHandler = handler;
        shortLinkMethod = method;
        requestMappingHandlerMapping.registerMapping(shortLinkMappingInfo, handler, method);

    }

    /**更改直接链接前缀路径参数：
     path：新的请求路径前缀。
     逻辑：
     如果 shortLinkMappingInfo 不为 null，则先注销当前的请求映射。
     创建新的 RequestMappingInfo 对象，使用新的路径前缀。
     重新注册处理器和方法，以便新的路径可以处理请求。*/
    public void changeDirectLinkPrefixPath(String path){
        if(shortLinkMappingInfo!=null){
            requestMappingHandlerMapping.unregisterMapping(shortLinkMappingInfo);
        }
        shortLinkMappingInfo = RequestMappingInfo.paths(path + "/**").build();
        requestMappingHandlerMapping.registerMapping(shortLinkMappingInfo, shortLinkHandler, shortLinkMethod);
    }


}
