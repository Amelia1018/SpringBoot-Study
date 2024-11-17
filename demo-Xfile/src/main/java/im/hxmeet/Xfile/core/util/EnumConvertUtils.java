package im.hxmeet.Xfile.core.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

import java.lang.reflect.Field;


/**
 * 枚举转换工具类

 */
public class EnumConvertUtils {


    /**
     * 根据枚举 class 和值获取对应的枚举对象
     *
     * @param   clazz
     *          枚举类 Class
     *
     * @param   value
     *          枚举值
     *
     * @return  枚举对象
     */
    public static Enum<?> convertStrToEnum(Class<?> clazz, Object value) {
        if (!ClassUtil.isEnum(clazz)) {
            return null;
        }

        Field[] fields = ReflectUtil.getFields(clazz);
        for (Field field : fields) {
            /**遍历每个字段来检查是否有 JsonValue 或 EnumValue 注解。
             JsonValue 注解通常用于表示将枚举值序列化为 JSON 的主要展示值。
             EnumValue 注解可以自定义枚举的值。*/
            boolean jsonValuePresent = field.isAnnotationPresent(JsonValue.class);
            boolean enumValuePresent = field.isAnnotationPresent(EnumValue.class);

            /**获取该枚举类的所有常量（使用 clazz.getEnumConstants()）。
             遍历这些常量，检查每个常量中被注解标记的字段与输入的 value 是否相等。如果相等，返回对应的枚举对象。
             返回结果: 如果遍历完所有常量仍未找到匹配值，则返回 null。*/
            if (jsonValuePresent || enumValuePresent) {
                Object[] enumConstants = clazz.getEnumConstants();

                for (Object enumObj : enumConstants) {
                    if (ObjectUtil.equal(value, ReflectUtil.getFieldValue(enumObj, field))) {
                        return (Enum<?>) enumObj;
                    }
                }
            }
        }
        return null;
    }


    /**
     * 转换枚举对象为字符串, 如果枚举对象没有定义 JsonValue 注解, 则使用 EnumValue 注解的值
     *
     * @param   enumObj
     *          枚举对象
     *
     * @return  字符串
     */
    public static String convertEnumToStr(Object enumObj) {
        Class<?> clazz = enumObj.getClass();
        if (!ClassUtil.isEnum(clazz)) {
            return null;
        }

        Field[] fields = ReflectUtil.getFields(clazz);
        for (Field field : fields) {
            boolean jsonValuePresent = field.isAnnotationPresent(JsonValue.class);
            boolean enumValuePresent = field.isAnnotationPresent(EnumValue.class);

            if (jsonValuePresent || enumValuePresent) {
                return Convert.toStr(ReflectUtil.getFieldValue(enumObj, field));
            }
        }

        return null;
    }

}