package im.hxmeet.Xfile.core.util;

import cn.hutool.core.util.StrUtil;

/**
 * url 相关工具类
 */
public class UrlUtils {

    /**
     * 给 url 拼接参数
     *
     * @param 	url
     * 			原始 URL
     *
     * @param 	name
     * 			参数名称
     *
     * @param 	value
     * 			参数值
     *
     * @return	拼接后的 URL
     */
    public static String concatQueryParam(String url, String name, String value) {
       //检查 URL 中是否存在问号 ?，问号通常表示查询参数的开始。
        if (StrUtil.contains(url, "?")) {
            //通过 & 将新的查询参数添加到现有参数后面，格式为 &name=value。
          /**若原 URL 为 http://example.com/page?param1=value1，添加的参数为 param2=value2，
           * 则结果为 http://example.com/page?param1=value1&param2=value2。*/
            return url + "&" + name + "=" + value;
        } else {
            //使用 ? 开始新的查询参数
            /**若原 URL 为 http://example.com/page，添加的参数为 param1=value1，
             * 结果将是 http://example.com/page?param1=value1。*/
            return url + "?" + name + "=" + value;
        }
    }

}
