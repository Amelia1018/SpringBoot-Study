package im.hxmeet.Xfile.module.link.model.result;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@ApiModel(description = "批量生成直链结果类")
@AllArgsConstructor
public class BatchGenerateLinkResponse {

    private String shortLink;

    private String pathLink;

}

