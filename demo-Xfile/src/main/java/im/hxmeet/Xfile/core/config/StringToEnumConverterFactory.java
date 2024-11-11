package im.hxmeet.Xfile.core.config;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static jdk.internal.org.objectweb.asm.commons.Method.getMethod;

@Slf4j
public class StringToEnumConverterFactory implements ConverterFactory<String, Enum<?>> {

    //CONVERTER_MAP用于缓存不同枚举类型的转换器，以提高性能，避免重复创建转换器。
    private static final Map<Class<?>,Converter<String,?extends Enum<?>>> CONVERTER_MAP = new ConcurrentHashMap<>();
    //TABLE_METHOD_OF_ENUM_TYPES用于缓存枚举类中获取枚举值的方法，避免重复查找。
    private  static   final Map<Class<?>, Method> TABLE_METHOD_OF_ENUM_TYPES = new ConcurrentHashMap<>();

    @Override
    public <T extends Enum<?>>  Converter<String, T>  getConverter(Class<T> targetType) {

        Converter<String, T> converter = (Converter<String, T>) CONVERTER_MAP.get(targetType);
        //从 CONVERTER_MAP 中获取已缓存的转换器，如果不存在，则创建一个新的 StringToEnumConverter 实例并缓存。
        if (converter == null) {
            converter = new StringToEnumConverter<>(targetType);
            CONVERTER_MAP.put(targetType, converter);
        }
        return converter;
    }

    //内置类 StringToEnumConverter 实现了 Converter<String, T> 接口，用于将字符串转换为枚举类型
    static class StringToEnumConverter<T extends Enum<?>> implements Converter<String, T> {
        //用于缓存字符串和枚举对象的映射关系。
        private final Map<String, T> enumMap = new ConcurrentHashMap<>();

        //构造函数：
        //接收枚举类型，调用 getMethod获取 获取枚举值 的方法，并将枚举常量及其对应的字符串值存入 enumMap。
        StringToEnumConverter(Class<T> enumType) {
            Method method = getMethod(enumType);
            T[] enums = enumType.getEnumConstants();

            // 将值与枚举对象对应并缓存
            for (T e : enums) {
                try {
                    enumMap.put(method.invoke(e).toString(), e);
                } catch (IllegalAccessException | InvocationTargetException ex) {
                    log.error("获取枚举值错误!!! ", ex);
                }
            }
        }

        //convert 方法：
        //接收一个字符串 source，从 enumMap 中查找对应的枚举对象。如果找不到，抛出 IllegalArgumentException。
        @Override
        public T convert(@NotNull String source) {
            // 获取
            T t = enumMap.get(source);
            if (t == null) {
                throw new IllegalArgumentException("该字符串找不到对应的枚举对象 字符串:" + source);
            }
            return t;
        }
    }


    public static <T> Method getMethod(Class<T> enumType) {
        Method method;
        // 找到取值的方法    根据枚举类型判断是否实现了 IEnum 接口。如果实现了，尝试获取 getValue 方法。
        if (IEnum.class.isAssignableFrom(enumType)) {
            try {
                method = enumType.getMethod("getValue");
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException(String.format("类:%s 找不到 getValue方法",
                        enumType.getName()));
            }
            //如果没有实现 IEnum 接口，则查找带有 EnumValue 注解的字段，构造相应的 getter 方法名并查找该方法。
        } else {
            method = TABLE_METHOD_OF_ENUM_TYPES.computeIfAbsent(enumType, k -> {
                Field field =

                        dealEnumType(enumType).orElseThrow(() -> new IllegalArgumentException(String.format(
                                "类:%s 找不到 EnumValue注解", enumType.getName())));

                Class<?> fieldType = field.getType();
                String fieldName = field.getName();
                String methodName =  StringUtils.concatCapitalize(boolean.class.equals(fieldType) ? "is" : "get", fieldName);

                try {
                    return enumType.getDeclaredMethod(methodName);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                return null;
            });
        }
        return method;
    }

    //dealEnumType检查类是否为枚举类型，并查找是否有带有 EnumValue 注解的字段。如果找到，返回该字段的 Optional。
    private static Optional<Field> dealEnumType(Class<?> clazz) {
        return clazz.isEnum() ?
                Arrays.stream(clazz.getDeclaredFields())
                        .filter(field -> field.isAnnotationPresent(EnumValue.class))
                                .findFirst() : Optional.empty();
    }
}
