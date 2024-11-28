package im.hxmeet.Xfile.module.config.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统设置 entity
 */
@Data
@ApiModel(description = "系统设置")
@TableName(value = "system_config")//指明了该实体类与数据库表 system_config 之间的映射关系，意味着这个类对应数据库中的 system_config 表。
public class SystemConfig implements Serializable {

    public static final String DIRECT_LINK_PREFIX_NAME = "directLinkPrefix";

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "ID, 新增无需填写", example = "1")//提供了额外的信息，描述了这个字段的用途及示例值。
    private Integer id;


    @TableField(value = "name")
    @ApiModelProperty(value = "系统设置名称", example = "siteName")
    private String name;


    @TableField(value = "`value`")
    @ApiModelProperty(value = "系统设置值", example = "ZFile 演示站")
    private String value;


    @TableField(value = "title")
    @ApiModelProperty(value = "系统设置描述", example = "站点名称")
    private String title;

}