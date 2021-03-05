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
 * 角色权限表
 * </p>
 *
 * @author xuzf
 * @since 2020-07-02
 */
@ApiModel(value = "SysRolePermission对象", description = "角色权限表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "ray_sys_role_permission")
@org.hibernate.annotations.Table(appliesTo = "ray_sys_role_permission", comment = "角色权限表")
public class JpaSysRolePermission extends BasicSysEntity {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc.jpa.configs.MyInsertGenerator")
	private Long id;
	
	/**
	 * 菜单id
	 */
	@ApiModelProperty(value = "菜单id")
	@Column(name = "menu_id")
	private Long menuId;
	/**
	 * 角色id
	 */
	@ApiModelProperty(value = "角色id")
	@Column(name = "role_id")
	private Long roleId;
	
	
}
