package im.hxmeet.Xfile.module.config.controller;

import cn.hutool.extra.servlet.ServletUtil;
import im.hxmeet.Xfile.core.util.AjaxJson;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "IP 地址辅助 Controller")
@Slf4j
@RequestMapping("/admin")
@RestController
public class IpHelperController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @GetMapping("clientIp")
    public AjaxJson<String> clientIp() {
        //调用 ServletUtil 的 getClientIP 方法，使用注入的 httpServletRequest 对象获取客户端的 IP 地址。这通常涉及从请求头中读取 IP 地址，支持多种代理情况。
        String clientIp = ServletUtil.getClientIP(httpServletRequest);
        //将获取到的客户端 IP 封装在 AjaxJson 对象中并返回。
        return AjaxJson.getSuccessData(clientIp);
    }

}