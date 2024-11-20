package im.hxmeet.Xfile.core.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.function.Function;

/**
 * 获取 Request 工具类
 */
public class RequestHolder {

    /**
     * 获取 HttpServletRequest
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }


    /**
     * 获取 HttpServletResponse
     *
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse(){
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }


    /**
     * 向 response 写入文件流.
     *
     * @param   function
     *          文件输入流获取函数
     *
     * @param   path
     *          文件路径
     */
    public static void writeFile(Function<String, InputStream> function, String path){
       //使用 function.apply(path) 获取指定路径的 InputStream。
        try (InputStream inputStream = function.apply(path)) {
            //调用 RequestHolder.getResponse() 获取当前请求的 HttpServletResponse 对象，以便写入响应数据。
            HttpServletResponse response = RequestHolder.getResponse();
            String fileName = FileUtil.getName(path);

            //设置 Content-Disposition 响应头，以指示浏览器以附件形式下载文件，并指定文件名。使用 StringUtils.encodeAllIgnoreSlashes(fileName) 来对文件名进行编码，以处理可能的特殊字符和斜杠。
            response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + StringUtils.encodeAllIgnoreSlashes(fileName));
           //设置响应的内容类型为 application/octet-stream，表示这是一个二进制流，通常用于文件下载。
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

            OutputStream outputStream = response.getOutputStream();
            //将输入流中的数据复制到输出流中。
            IoUtil.copy(inputStream, outputStream);
            //调用 response.flushBuffer()，确保所有数据都被发送到客户端。
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}