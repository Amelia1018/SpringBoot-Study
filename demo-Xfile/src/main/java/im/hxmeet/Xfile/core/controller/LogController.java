package im.hxmeet.Xfile.core.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ZipUtil;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.util.Date;

/**
 * 获取系统日志接口

 */

/**
 * @Api(tags = "日志"): 这是 Swagger 的注解，用于描述 API 的标签。在生成 API 文档时，这个标签将用于分类显示与日志相关的接口。
 *
 * @ApiSort(8): 这是一个用于排序的注解，通常用于控制在 Swagger UI 中显示的顺序。数字越小，显示越靠前。
 *
 * @RestController: 这是 Spring MVC 的注解，表示该类是一个控制器，能够处理 HTTP 请求，并返回 JSON 或其他格式的响应。
 *
 * @RequestMapping("/admin"): 这个注解用于指定该控制器处理的基础 URL 路径。在这里，所有在该控制器中定义的请求都将以 /admin 开头。*/

@Api(tags = "日志")
@ApiSort(8)
@Slf4j
@ApiSort
@RequestMapping
public class LogController {

    /**
     * 这个注解用于从 Spring 的配置文件中读取名为 zfile.log.path 的配置项，并将其赋值给 zfileLogPath 字段。
     * 这个字段通常表示系统日志文件的存储路径。*/

    @Value("${zfile.log.path}")
    private String zfileLogPath;

    /** @GetMapping("/log/download")  这个注解表示该方法会处理 HTTP GET 请求，路径为 /admin/log/download。
     *  @ApiOperation(value = "下载系统日志"): 这是 Swagger 的注解，用于描述该接口的功能，即下载系统日志。*/

    @GetMapping("/log/download")
    @ApiOperation(value = "下载系统日志")
    public ResponseEntity<Resource> downloadLog() {
        if (log.isDebugEnabled()) {
            log.debug("下载诊断日志");
        }

        /**使用 ZipUtil.zip(zfileLogPath) 方法将指定路径下的日志文件进行压缩，返回一个压缩后的文件对象 fileZip。
         * ZipUtil 是一个工具类，用于处理文件压缩。*/

        File fileZip = ZipUtil.zip(zfileLogPath);

        /** 通过 DateUtil.format 方法将当前日期格式化为字符串，格式为 yyyy-MM-dd HH:mm:ss。这通常用于生成文件名时的时间戳。*/

        String currentDate = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");

        /**调用 FileResponseUtil.exportSingleThread 方法，传入压缩文件和生成的文件名，返回一个 ResponseEntity<Resource> 对象。
         * 这通常用于将文件作为响应返回给客户端，允许用户下载该文件。*/

        return FileResponseUtil.exportSingleThread(fileZip, "ZFile 诊断日志 - " + currentDate + ".zip");
    }

}
