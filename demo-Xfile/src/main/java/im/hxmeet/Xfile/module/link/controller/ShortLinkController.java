package im.hxmeet.Xfile.module.link.controller;


import cn.hutool.core.util.BooleanUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import im.hxmeet.Xfile.core.util.AjaxJson;
import im.hxmeet.Xfile.core.util.StringUtils;
import im.hxmeet.Xfile.module.config.model.dto.SystemConfigDTO;
import im.hxmeet.Xfile.module.config.service.SystemConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 短链接口
 * ShortLinkController 类实现了短链接的生成和跳转功能。
 * 它首先通过系统配置和请求参数生成短链接，然后根据短链接的 key 进行重定向。
 */
@Api(tags = "直短链模块")
@ApiSort(5)//指定该 API 的排序顺序，影响在 Swagger 文档中的显示位置。
@Controller//标识该类为一个 Spring MVC 控制器。
@Slf4j
public class ShortLinkController {

    @Resource
    private SystemConfigService systemConfigService;

    @Resource
    private ShortLinkService shortLinkService;

    @Resource
    private StorageSourceService storageSourceService;

    @Resource
    private LinkDownloadService linkDownloadService;


    @PostMapping("/api/short-link/batch/generate")//当客户端发送 POST 请求到这个路径时，Spring MVC 会调用这个方法。
    @ResponseBody //这个注解指示方法的返回值应该作为 HTTP 响应体直接返回，而不是解析为视图名。这意味着方法返回的数据会直接转换成 JSON 或其他格式的响应
    @ApiOperationSupport(order = 1) //这是一个 Swagger 注解，用于提供 API 文档生成的支持。order = 1 指定该操作在 API 文档中的顺序，控制显示的优先级。在 API 文档中，该操作会根据这个值进行排序，值越小，显示越靠前。
    @ApiOperation(value = "生成短链", notes = "对指定存储源的某文件路径生成短链") //这个注解是 Swagger 用于描述 API 操作的。value 属性为操作提供了一个简短的描述，这里是“生成短链”。notes 属性提供了更详细的信息，说明该操作的用途，在这里是“对指定存储源的某文件路径生成短链”。这些描述会在 API 文档中显示，帮助用户理解这个 API 的功能。
    public AjaxJson<List<BatchGenerateLinkResponse>> generatorShortLink(@RequestBody @Valid BatchGenerateLinkRequest batchGenerateLinkRequest) {
        List<BatchGenerateLinkResponse> result = new ArrayList<>();

        // 获取站点域名
        SystemConfigDTO systemConfig = systemConfigService.getSystemConfig();

        // 是否允许使用短链和短链，如果都不允许，则提示禁止生成.
        Boolean showShortLink = systemConfig.getShowShortLink();
        Boolean showPathLink = systemConfig.getShowPathLink();
        if ( BooleanUtil.isFalse(showShortLink) && BooleanUtil.isFalse(showPathLink)) {
            throw new IllegalDownloadLinkException("当前系统不允许使用直链和短链.");
        }

        String domain = systemConfig.getDomain();
        Long expireTime = batchGenerateLinkRequest.getExpireTime();
        String storageKey = batchGenerateLinkRequest.getStorageKey();
        Integer storageId = storageSourceService.findIdByKey(storageKey);

        for (String path : batchGenerateLinkRequest.getPaths()) {
            // 拼接全路径地址.
            String fullPath = StringUtils.concat(path);
            ShortLink shortLink = shortLinkService.generatorShortLink(storageId, fullPath, expireTime);
            String shortUrl = StringUtils.removeDuplicateSlashes(domain + "/s/" + shortLink.getShortKey());
            String pathLink = StringUtils.generatorPathLink(storageKey, fullPath);
            result.add(new BatchGenerateLinkResponse(shortUrl, pathLink));
        }
        return AjaxJson.getSuccessData(result);
    }


    @GetMapping("/s/{key}")// {key} 是短链接的 key。
    @ResponseStatus(HttpStatus.FOUND)//这是一个枚举值，表示 HTTP 状态码 302，通常用来指示重定向。当客户端请求该方法时，服务器会返回一个 302 状态码，并告知客户端请求的资源已经被移动到新的 URL。
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "跳转短链", notes = "根据短链 key 跳转（302 重定向）到对应的直链.")
    @ApiImplicitParam(paramType = "path", name = "key", value = "短链 key", required = true, dataTypeClass = String.class)//用于描述 API 的请求参数:
    // paramType = "path"：指定参数的类型为路径参数。这意味着这个参数将作为 URL 的一部分传递，例如 /s/{key} 中的 {key}。
    // name = "key"：指定参数的名称为 key，这是实际用作路径变量的名称
    //value = "短链 key"：为参数提供一个简短的描述，这里表示该参数是一个短链接的关键字。
    //required = true：指定该参数是必需的，这意味着在调用 API 时必须提供这个参数。如果缺少该参数，API 请求将无法成功。
    //dataTypeClass = String.class：指定参数的数据类型，这里是 String 类型。这有助于生成文档时识别该参数的类型。
    public void parseShortKey(@PathVariable String key) throws IOException {
        linkDownloadService.handlerShortLink(key);
    }

}
