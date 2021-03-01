package hqsc.ray.wcc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "MCNOrganizationInfo对象", description = "mcn机构对象")
public class MCNOrganizationInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ORGANIZATION_ID", type = IdType.AUTO)
    private Long organizationId;
    /**
     * 机构图标
     */
    @ApiModelProperty(value = "机构图标")
    @TableField("ICON")
    private Long icon;
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    @TableField("O_NAME")
    private String oName;
    /**
     * 机构坐标
     */
    @ApiModelProperty(value = "机构坐标")
    @TableField("ADDRESS")
    private String address;
    /**
     * 领域
     */
    @ApiModelProperty(value = "领域")
    @TableField("FIELD")
    private String field;

    /**
     * 粉丝数量
     */
    @ApiModelProperty(value = "粉丝数量")
    @TableField("FANS")
    private String fansCount;

    /**
     * 红人数量
     */
    @ApiModelProperty(value = "红人数量")
    @TableField("CELEBRITYCOUNT")
    private String celebrityCount;

}
