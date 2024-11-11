package im.hxmeet.Xfile.core.config;


import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/*
@Slf4j：这是 Lombok 提供的注解，用于自动生成一个名为 log 的日志记录器。你可以通过这个记录器来打印日志信息。
@Setter：Lombok 提供的另一个注解，自动为类中的属性生成 setter 方法。
@JsonComponent：这是 Jackson 提供的注解，表明该类是一个 JSON 组件，可以被 Jackson 管理。
*/
@Slf4j
@Setter
@JsonComponent
public class JacksonEnumDeserializer extends JsonDeserializer<Enum<?>> implements ContextualDeserializer {
    private Class<?> clazz;//用于存储待反序列化的枚举的 Class 对象。

    /**
     * 反序列化操作
     *
     * @param   jsonParser
     *          json 解析器
     *
     * @param   ctx
     *          反序列化上下文
     *
     * @return  反序列化后的枚举值
     * @throws  IOException  反序列化异常
     */

    @Override
   // 定义一个枚举类型，需要使用 enum 关键字，后面跟着枚举类型的名称，
    public  Enum<?>deserialize(JsonParser jsonParser, DeserializationContext ctx)throws IOException {
        Class<?>enumType = clazz;
        if(Objects.isNull(enumType)||enumType.isEnum()){
            return null;
        }
        //读取 JSON 中的文本值 text。
        String text = jsonParser.getText();
        //遍历枚举常量，使用 StringToEnumConverterFactory 中的方法转换字符串值，判断是否与 text 相匹配。如果找到匹配的枚举值，则返回该枚举对象。
        Method[] method = StringToEnumConverterFactory.getMethod(clazz);
        Enum<?>[] enumConstants = (Enum<?>[]) enumType.getEnumConstants();

        for (Enum<?> e : enumConstants) {
            try{
                if(Objects.equals(method.invoke(e).toString(),text)){
                    return e;
                }
            }catch (IllegalAccessException | InvocationTargetException ex){
                log.error("获取枚举值错误",ex);
            }
        }
        return null;

    }
    /**
     * 为不同的枚举获取合适的解析器
     *
     * @param   ctx
     *          反序列化上下文
     *
     * @param   property
     *          property
     */
    @Override
    public  JsonDeserializer<Enum<?>> createContextual(DeserializationContext ctx, BeanProperty property){
     //从上下文中获取要反序列化的枚举的原始类 rawCls。
        Class<?>rawCls = ctx.getContextualType().getRawClass();
        // 创建一个新的 JacksonEnumDeserializer 实例，并设置其 clazz 属性为 rawCls。
        JacksonEnumDeserializer converter = new JacksonEnumDeserializer();
        converter.setClazz(rawCls);
        return converter;
    }

}

