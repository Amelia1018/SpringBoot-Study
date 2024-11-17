package im.hxmeet.Xfile.core.util;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * 将文件输出对象
 */
@Slf4j
public class FileResponseUtil {


    /**
     * 文件下载，单线程，不支持断点续传
     *
     * @param   file
     *          文件对象
     *
     * @param   fileName
     *          要保存为的文件名
     *
     * @return  文件下载对象
     */
    public static ResponseEntity<Resource> exportSingleThread(File file, String fileName) {
        if (!file.exists()) {
            ByteArrayResource byteArrayResource = new ByteArrayResource("文件不存在或异常，请联系管理员.".getBytes(StandardCharsets.UTF_8));
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(byteArrayResource);
        }

        ///默认的媒体类型设置为 MediaType.APPLICATION_OCTET_STREAM，适用于二进制文件下载。
        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;

        /**创建一个 HttpHeaders 对象用于设置 HTTP 响应头。
         如果 fileName 为空，则使用文件对象的名称 (file.getName())。
         使用 StringUtils.encodeAllIgnoreSlashes(fileName) 对文件名进行编码，并设置 Content-Disposition 头，以指示浏览器以附件形式下载该文件。*/
        HttpHeaders headers = new HttpHeaders();

        if (StrUtil.isEmpty(fileName)) {
            fileName = file.getName();
        }

        headers.setContentDispositionFormData("attachment", StringUtils.encodeAllIgnoreSlashes(fileName));

        /**使用 ResponseEntity.ok() 方法构建响应：
         设置响应头。
         定义内容长度为文件的长度。
         设置内容类型为之前定义的媒体类型。
         使用 InputStreamResource 将文件的输入流作为响应体。
         */
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(mediaType)
                .body(new InputStreamResource(FileUtil.getInputStream(file)));
    }

}