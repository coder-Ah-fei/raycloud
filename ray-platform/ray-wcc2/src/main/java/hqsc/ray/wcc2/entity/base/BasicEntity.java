package hqsc.ray.wcc2.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 表基础信息
 *
 * @author yang
 */
@Getter
@Setter
@Accessors(chain = true)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BasicEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 状态（1正常 0禁用）
	 */
	@ApiModelProperty(value = "状态（1正常 0禁用）")
	@Column(name = "STATUS", columnDefinition = "char(1) default '' comment '状态（1正常 0禁用）'")
	private Integer status;
	/**
	 * 1 删除  0正常
	 */
	@ApiModelProperty(value = "1 删除  0正常")
	@Column(name = "IS_DELETE", columnDefinition = "char(1) default '' comment '删除标识（1 删除  0正常）'")
	private Integer isDelete;
	
	/**
	 * jpa 版本控制
	 */
	@Version
	@JsonIgnore
	@Column(nullable = true, columnDefinition = "int default 0 comment 'jpa 版本控制'")
	private int version;
	
	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "创建时间")
	@Column(name = "CREATION_DATE", columnDefinition = "datetime(0) default null comment '创建时间'")
	@CreatedDate
	private Date creationDate;
	
	/**
	 * 最新的更新时间
	 */
	@ApiModelProperty(value = "最后更新时间")
	@Column(name = "LAST_UPDATE_DATE", columnDefinition = "datetime null comment '最新的更新时间'")
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date lastUpdateDate;
	
	/**
	 * 创建用户
	 */
	@ApiModelProperty(value = "创建人")
	@CreatedBy
	@Column(name = "CREATED_BY", columnDefinition = "varchar(60) null comment '创建人'")
	private String createdBy;
	/**
	 * 创建人姓名
	 */
	@ApiModelProperty(value = "创建人姓名")
	@Column(name = "CREATED_BY_USER", columnDefinition = "varchar(60) null comment '创建人姓名'")
	private String createdByUser;
	
	
	/**
	 * 最后的更新用户
	 */
	@LastModifiedBy
	@ApiModelProperty(value = "最后更新人")
	@Column(name = "LAST_UPDATED_BY", columnDefinition = "varchar(60) null comment '最后更新人'")
	private String lastUpdatedBy;
	
	
	/**
	 * 修改人姓名
	 */
	@ApiModelProperty(value = "修改人姓名")
	@Column(name = "LAST_UPDATED_BY_USER", columnDefinition = "varchar(60) null comment '修改人姓名'")
	private String lastUpdatedByUser;
	
}
