package im.hxmeet.Xfile.core.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
// PageQueryRequest 用于表达客户端对分页请求的需求
public class PageQueryRequest {

    ///来自 Swagger（OpenAPI）库的注解，用于描述 API 模型属性。在生成 API 文档时，它提供属性的说明。
    @ApiModelProperty(value="分页页数")
    ///定义了一个 Integer 类型的属性 page，默认为 1。它表示请求的当前页数。
    private Integer page = 1;

    @ApiModelProperty(value="每页条数")
    private Integer limit = 10; ///表示每页显示的记录数，即请求的最大数据条数。


    @ApiModelProperty(value="排序字段")
    private String orderBy = "create_date"; ///表示用于排序的字段名

    @ApiModelProperty(value="排序顺序")
    private String orderDirection = "desc"; ///表示排序的方向，通常 "asc" 表示升序，"desc" 表示降序。

}