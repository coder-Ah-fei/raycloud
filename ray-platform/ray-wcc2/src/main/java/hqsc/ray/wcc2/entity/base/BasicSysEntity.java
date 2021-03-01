package hqsc.ray.wcc2.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.LocalDateTime;

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
public class BasicSysEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * jpa 版本控制
	 */
	@Version
	@JsonIgnore
	@Column(nullable = true, columnDefinition = "int default 0 comment 'jpa 版本控制'")
	private int version;
	
	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人")
	@Column(name = "create_by")
	private String createBy;
	
	/**
	 * 更新人
	 */
	@ApiModelProperty(value = "更新人")
	@Column(name = "update_by")
	private String updateBy;
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@Column(name = "create_time")
	private LocalDateTime createTime;
	
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	@Column(name = "update_time")
	private LocalDateTime updateTime;
	
}
