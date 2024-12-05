package im.hxmeet.Xfile.module.link.model.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Referer 防盗链类型枚举
 */
@Getter
@AllArgsConstructor
public enum RefererTypeEnum {

    /**
     * 不启用 Referer 防盗链
     */
    OFF("off"),

    /**
     * 启用白名单模式: 在这种模式下，只有在白名单中列出的 Referer 才会被接受，所有其他的请求都会被拒绝。
     */
    WHITE_LIST("white_list"),

    /**
     * 启用黑名单模式
     */
    BLACK_LIST("black_list");

    @EnumValue
    @JsonValue
    private final String value;

}