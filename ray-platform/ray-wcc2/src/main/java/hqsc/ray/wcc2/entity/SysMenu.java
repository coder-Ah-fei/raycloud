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
 * 菜单权限表
 * </p>
 *
 * @author xuzf
 * @since 2020-06-18
 */
@ApiModel(value = "SysMenu对象", description = "菜单权限表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "ray_sys_menu")
@org.hibernate.annotations.Table(appliesTo = "ray_sys_menu", comment = "菜单权限表")
public class SysMenu extends BasicSysEntity {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc2.configs.MyInsertGenerator")
	private Long id;
	/**
	 * 菜单标题
	 */
	@ApiModelProperty(value = "菜单标题")
	private String name;
	/**
	 * 菜单权限
	 */
	@ApiModelProperty(value = "菜单权限")
	private String permission;
	/**
	 * 路径
	 */
	@ApiModelProperty(value = "路径")
	private String path;
	/**
	 * 父菜单ID
	 */
	@ApiModelProperty(value = "父菜单ID")
	@Column(name = "parent_id")
	private Long parentId;
	/**
	 * 菜单图标
	 */
	@ApiModelProperty(value = "菜单图标")
	private String icon;
	/**
	 * 排序值
	 */
	@ApiModelProperty(value = "排序值")
	private Integer sort;
	/**
	 * 是否缓存该页面: 1:是  0:不是
	 */
	@ApiModelProperty(value = "是否缓存该页面: 1:是  0:不是")
	@Column(name = "keep_alive")
	private String keepAlive;
	/**
	 * 菜单类型
	 */
	@ApiModelProperty(value = "菜单类型")
	private String type;
	/**
	 * 状态
	 */
	@ApiModelProperty(value = "状态")
	private String status;
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
	private Long tenantId;
	
}
