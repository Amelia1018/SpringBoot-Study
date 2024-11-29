package im.hxmeet.Xfile.module.config.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户修改密码请求参数类
 */
@Data
@ApiModel(description = "用户修改密码请求参数类")
public class UpdateUserNameAndPasswordRequest {

    @ApiModelProperty(value = "用户名", required = true, example = "admin")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码", required = true, example = "123456")
    @NotBlank(message = "密码不能为空")
    private String password;

}
