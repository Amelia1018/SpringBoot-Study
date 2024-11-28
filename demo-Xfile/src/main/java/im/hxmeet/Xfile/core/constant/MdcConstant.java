package im.hxmeet.Xfile.core.constant;

/**
 * Slf4j mdc 常量
 * ----------------------
 * MDC 常量的概念
 * MDC（Mapped Diagnostic Context）:
 *
 * MDC 是 SLF4J 和 Logback 等日志记录框架提供的一种功能，允许用户将上下文信息（例如用户标识、请求 ID 等）附加到日志记录中。
 * 这使得在分布式系统中进行故障排查和性能分析变得更加容易。通过 MDC，您可以在日志中附加动态生成的键值对，当记录日志时可以额外附带这些信息，帮助定位问题。
 */

//定义了一个常量类 MdcConstant，用于存储与 Slf4j MDC（Mapped Diagnostic Context）相关的常量
public class MdcConstant {
    public static final String TRACE_ID = "traceID";
    public static final String IP = "ip";
    public static final String USER = "user";
}

