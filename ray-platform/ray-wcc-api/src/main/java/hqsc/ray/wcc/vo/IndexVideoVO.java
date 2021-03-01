package hqsc.ray.wcc.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import hqsc.ray.wcc.entity.WccReleaseInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "IndexVideoVO对象", description = "首页视频VO")
public class IndexVideoVO extends WccReleaseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "附件url")
    private String url;
}
