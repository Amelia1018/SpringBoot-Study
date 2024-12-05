package im.hxmeet.Xfile.module.link.model.request;

import lombok.Data;

import java.util.List;

@Data
public class BatchDeleteRequest {

    private List<Integer> ids;

}