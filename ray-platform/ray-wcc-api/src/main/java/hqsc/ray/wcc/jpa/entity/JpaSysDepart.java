package hqsc.ray.wcc.jpa.entity;

import hqsc.ray.wcc.jpa.entity.base.BasicSysEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <p>
 * 组织机构表
 * </p>
 *
 * @author xuzf
 * @since 2020-06-28
 */
@ApiModel(value = "SysDepart对象", description = "组织机构表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "ray_sys_depart")
@org.hibernate.annotations.Table(appliesTo = "ray_sys_depart", comment = "组织机构表")
public class JpaSysDepart extends BasicSysEntity {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc.jpa.configs.MyInsertGenerator")
	private Long id;
	/**
	 * 部门名称
	 */
	@ApiModelProperty(value = "部门名称")
	private String name;
	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	private Integer sort;
	/**
	 * 删除标识
	 */
	@ApiModelProperty(value = "删除标识")
	@Column(name = "is_deleted")
	private String isDeleted;
	/**
	 * 上级ID
	 */
	@ApiModelProperty(value = "上级ID")
	@Column(name = "parent_id")
	private Long parentId;
	/**
	 * 租户ID
	 */
	@ApiModelProperty(value = "租户ID")
	@Column(name = "tenant_id")
	private Long tenantId;
	
}
