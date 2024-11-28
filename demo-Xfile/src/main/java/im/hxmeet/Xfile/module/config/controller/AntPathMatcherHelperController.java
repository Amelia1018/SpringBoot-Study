package im.hxmeet.Xfile.module.config.controller;

import cn.hutool.core.util.StrUtil;
import im.hxmeet.Xfile.core.util.AjaxJson;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
//这个注解用于为 API 文档提供信息，指定这个控制器的标识标签。
@Api(tags = "Ant Path Matcher 辅助 Controller")
@Slf4j
@RequestMapping("/admin")
@RestController//表示这是一个 RESTful 风格的控制器，返回的数据会以 JSON 格式呈现
public class AntPathMatcherHelperController {

    @Resource//表示 Spring 会自动注入 RefererCheckAspect 这个组件的实例。
    private RefererCheckAspect refererCheckAspect;

    @PostMapping("/ant-path-test")
    public AjaxJson<Boolean> clientIp(@RequestBody TestAntPathMatcherRequest testAntPathMatcherRequest) {
        if (testAntPathMatcherRequest == null) {
            return AjaxJson.getSuccessData(false);
        }
        String testPath = testAntPathMatcherRequest.getTestPath();
        String antPath = testAntPathMatcherRequest.getAntPath();
        if (StrUtil.isBlank(testPath) || StrUtil.isBlank(antPath)) {
            return AjaxJson.getSuccessData(false);
        }
        List<String> refererValueList = StrUtil.split(antPath, '\n');
        return AjaxJson.getSuccessData(refererCheckAspect.containsPathMatcher(refererValueList, testPath));
    }

}