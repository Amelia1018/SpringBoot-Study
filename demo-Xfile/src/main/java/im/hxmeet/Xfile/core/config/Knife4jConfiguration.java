package im.hxmeet.Xfile.core.config;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

//@Configuration: 该注解标记这个类为一个Spring配置类，Spring会在上下文中注册这个类中的Bean。
//@EnableSwagger2: 启用Swagger 2的特性，使得Swagger能够生成API文档。
@Configuration
@EnableSwagger2
public class Knife4jConfiguration {

    private final OpenApiExtensionResolver openApiExtensionResolver;
    @Autowired
    public Knife4jConfiguration(OpenApiExtensionResolver openApiExtensionResolver) {
        this.openApiExtensionResolver = openApiExtensionResolver;
    }

    //此方法定义了一个名为baseApi的Docket Bean，它为“前台功能”生成Swagger文档。
    //Docket是Swagger的核心配置类，用于定制API文档的生成。
    @Bean(value = "baseApi")
    public Docket baseApi(){
        String groupName = "前台功能";
        return new Docket(DocumentationType.SWAGGER_2)
                //设置API信息（apiInfo()），如标题、描述等。
                //使用RequestHandlerSelectors选择基础包路径为im.hxm.xfile.module，包含此包下的所有API。
                //使用PathSelectors排除以/admin/**路径开头的请求。
                //建立Docket，并绑定相关的扩展。
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("im.hxmeet.Xfile.module"))
                .paths(PathSelectors.ant("/admin/**").negate())
                .build()

                .groupName(groupName)
                .extensions(openApiExtensionResolver.buildExtensions(groupName));
    }

    @Bean(value = "adminApi")
    public Docket adminApi() {
        String groupName = "管理员功能";
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                // 全局请求参数
                .globalRequestParameters(generateRequestParameters())
                .select()
                .apis(RequestHandlerSelectors.basePackage("im.hxmeet.Xfile.module"))
                .paths(PathSelectors.ant("/admin/**"))
                .build()
                .groupName(groupName)
                .extensions(openApiExtensionResolver.buildExtensions(groupName));
    }

    //该方法用于生成全局请求参数列表。
    //创建一个名为xfile-token的请求头参数，该参数是必须的，且用于身份验证。
    private List<RequestParameter> generateRequestParameters() {
        RequestParameterBuilder token = new RequestParameterBuilder();
        List<RequestParameter> parameters = new ArrayList<RequestParameter>();
        token.name("xfile-token").description("token").in(In.HEADER.toValue()).required(true).build();
        parameters.add(token.build());
        return parameters;
    }
//该方法用于构建和返回API的基本信息，包括标题、描述、联系信息等。
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("XFILE文档")
                .description("# 这是 XFILE Restful 接口文档展示页面")
                .termsOfServiceUrl("https://www.xfile.vip")
                .contact(new Contact("hxm","https://www.xfile.vip","admin@xfile.vip"))
                .version("1.0")
                .build();
    }


}
