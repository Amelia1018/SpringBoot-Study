package im.hxmeet.Xfile.core.controller;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 处理前端首页 Controller
 */
@Controller
public class FrontIndexController {
//用于注入 SystemConfigService bean，显然是提供关于应用程序配置详细信息的。
    @Resource
    private SystemConfigService systemConfigService;

    /**
     * 所有未找到的页面都跳转到首页, 用户解决 vue history 直接访问 404 的问题
     * 同时, 读取 index.html 文件, 修改 title 和 favicon 后返回.
     *
     * @return  转发到 /index.html
     */
@RequestMapping(value = {"/**/{[path:[^\\.]*}", "/"})
@ResponseBody  //表示返回的值应绑定到 Web 响应体，意味着方法返回的内容将直接发送给客户端。
    public String redirect() throws IOException {
    // 读取 resources/static/index.html 文件修改 title 和 favicon 后返回
    ClassPathResource resource = new ClassPathResource("static/index.html");
    InputStream inputStream = resource.getInputStream();  //getInputStream() 方法用于获取一个 InputStream 来读取文件。
    String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8); //使用 IOUtils.toString() 将 HTML 文件的内容读取为一个字符串（content），它将 InputStream 转换为 UTF-8 编码的字符串。

    SystemConfigDTO systemConfig = systemConfigService.getSystemConfig();

    /**HTML 文档的标题被从 systemConfig 中检索的站点名称替换。如果站点名称可用且不为空，则将默认标题 (<title>ZFile</title>)
     * 替换为自定义标题 (<title>{siteName}</title>)。*/
    String siteName = systemConfig.getSiteName();
    if(StrUtil.isNotBlank(siteName)){
        content = content.replace("<title>ZFile</title>", "<title>" + siteName + "</title>");
    }

    /**如果提供了有效的 URL，则更新 favicon URL。它将默认的 favicon 路径 /favicon.svg 替换为从 systemConfig 获取的自定义 URL。*/
    String faviconUrl = systemConfig.getFaviconUrl();
    if (StrUtil.isNotBlank(faviconUrl)) {
        content = content.replace("/favicon.svg", faviconUrl);
    }

    return content;//修改后的 HTML 内容作为 HTTP 响应返回。这使得用户可以始终接收到主 Vue.js 应用程序页面，以及更新的标题和图标，而不论他们访问的具体路由是什么。
}

}
