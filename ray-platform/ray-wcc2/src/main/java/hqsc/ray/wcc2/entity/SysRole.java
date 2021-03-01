package hqsc.ray.wcc2.entity;

import hqsc.ray.wcc2.entity.base.BasicSysEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author xuzf
 * @since 2020-06-28
 */
@ApiModel(value = "SysRole对象", description = "角色表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "ray_sys_role")
@org.hibernate.annotations.Table(appliesTo = "ray_sys_role", comment = "角色表")
public class SysRole extends BasicSysEntity {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc2.configs.MyInsertGenerator")
	private Long id;
	/**
	 * 角色名称
	 */
	@ApiModelProperty(value = "角色名称")
	@Column(name = "role_name")
	private String roleName;
	/**
	 * 角色编码
	 */
	@ApiModelProperty(value = "角色编码")
	@Column(name = "role_code")
	private String roleCode;
	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String description;
	
	/**
	 * 删除标识
	 */
	@ApiModelProperty(value = "删除标识")
	@Column(name = "is_deleted")
	private String isDeleted;
	/**
	 * 租户ID
	 */
	@ApiModelProperty(value = "租户ID")
	@Column(name = "tenant_id")
	private Integer tenantId;
}
