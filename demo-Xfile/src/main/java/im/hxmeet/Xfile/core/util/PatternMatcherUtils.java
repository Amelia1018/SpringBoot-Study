package im.hxmeet.Xfile.core.util;

import cn.hutool.core.util.StrUtil;
import im.hxmeet.Xfile.core.constant.ZFileConstant;
import org.thymeleaf.util.StringUtils;

import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * 规则表达式工具类
 */
public class PatternMatcherUtils {
    //一个静态 Map，用于缓存 PathMatcher 实例，以避免每次匹配时都重复创建。
    private static final Map<String, PathMatcher> PATH_MATCHER_MAP = new HashMap<>();

    /**
     * 兼容模式的 glob 表达式匹配.
     * 默认的 glob 表达式是不支持以下情况的:<br>
     * <ul>
     * <li>pattern: /a/**</li>
     * <li>test1: /a</li>
     * <li>test2: /a/</li>
     * <ul>
     * <p>test1 和 test 2 均无法匹配这种情况, 此方法兼容了这种情况, 即对 test 内容后拼接 "/xx", 使其可以匹配上 pattern.
     * <p><strong>注意：</strong>但此方法对包含文件名的情况无效, 仅支持 test 为 路径的情况.
     *
     * @param 	pattern
     *			要匹配的 glob 表达式。
     *
     * @param 	test
     *			匹配内容，也就是进行匹配的测试路径
     *
     * @return 	是否匹配.
     */
    //用于测试给定的 glob 表达式（模式）是否与特定的路径（测试）相匹配。此方法处理了 glob 表达式在一些特殊情况下的兼容性问题。

    public static boolean testCompatibilityGlobPattern(String pattern, String test) {
        // 检查 pattern 开头是否有路径分隔符（/），如果没有，将其添加上，以确保始终以斜杠开头。
        if (!StrUtil.startWith(pattern, ZFileConstant.PATH_SEPARATOR)) {
            pattern = ZFileConstant.PATH_SEPARATOR + pattern;
        }

        // 兼容性处理.
        //将测试字符串 test 末尾附加一个斜杠，以确保Uniform Resource Identifier（URI）形式匹配。
        test = StringUtils.concat(test, ZFileConstant.PATH_SEPARATOR);
        //如果 pattern 以 /** 结尾，表示它可以接受任何子目录，因此在 test 的末尾添加一个默认字符串（如 “xxx”），以确保能匹配。
        if (StrUtil.endWith(pattern, "/**")) {
            test += "xxx";
        }
        return testGlobPattern(pattern, test);
    }

    /**
     * 测试密码规则表达式和文件路径是否匹配
     *
     * @param 	pattern
     *			glob 规则表达式
     *
     * @param 	test
     *			测试字符串
     */
    private static boolean testGlobPattern(String pattern, String test) {
        // 从缓存取出 PathMatcher, 防止重复初始化
       // 首先尝试从缓存中获取 PathMatcher，
       // 如果没有找到，则使用 FileSystems.getDefault().getPathMatcher("glob:" + pattern) 创建一个新的 PathMatcher 实例，并将其放入缓存中。
        PathMatcher pathMatcher = PATH_MATCHER_MAP.getOrDefault(pattern,  FileSystems.getDefault().getPathMatcher("glob:" + pattern));

        PATH_MATCHER_MAP.put(pattern, pathMatcher);
        //检查 pattern 是否与 test 相匹配；同时也比较 pattern 和 test 是否相等（使用 StrUtil.equals），以额外处理完全相等的情况。
        return pathMatcher.matches(Paths.get(test)) || StrUtil.equals(pattern, test);
    }

}
