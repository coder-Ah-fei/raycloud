package hqsc.ray.wcc.jpa.entity;

import hqsc.ray.wcc.jpa.entity.base.BasicSysEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author xuzf
 * @since 2020-06-18
 */
@ApiModel(value = "SysUser对象", description = "系统用户表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "ray_sys_user")
@org.hibernate.annotations.Table(appliesTo = "ray_sys_user", comment = "系统用户表")
public class JpaSysUser extends BasicSysEntity {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc.jpa.configs.MyInsertGenerator")
	private Long id;
	/**
	 * 租户ID
	 */
	@ApiModelProperty(value = "租户ID")
	@Column(name = "tenant_id")
	private String tenantId;
	
	/**
	 * 账号
	 */
	@ApiModelProperty(value = "账号")
	private String account;
	
	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	private String password;
	
	/**
	 * 昵称
	 */
	@ApiModelProperty(value = "昵称")
	private String name;
	
	/**
	 * 真名
	 */
	@ApiModelProperty(value = "真名")
	@Column(name = "real_name")
	private String realName;
	
	/**
	 * 头像
	 */
	@ApiModelProperty(value = "头像")
	private String avatar;
	
	/**
	 * 邮箱
	 */
	@ApiModelProperty(value = "邮箱")
	private String email;
	
	/**
	 * 手机
	 */
	@ApiModelProperty(value = "手机")
	private String telephone;
	
	/**
	 * 生日
	 */
	@ApiModelProperty(value = "生日")
	private LocalDateTime birthday;
	
	/**
	 * 性别
	 */
	@ApiModelProperty(value = "性别")
	private Integer sex;
	
	/**
	 * 角色id
	 */
	@ApiModelProperty(value = "角色id")
	@Column(name = "role_id")
	private Long roleId;
	
	/**
	 * 部门id
	 */
	@ApiModelProperty(value = "部门id")
	@Column(name = "depart_id")
	private Long departId;
	
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
	
	
}
