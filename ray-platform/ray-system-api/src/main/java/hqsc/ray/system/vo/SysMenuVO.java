package hqsc.ray.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import hqsc.ray.system.entity.MenuMeta;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单视图对象
 *
 * @author xuzhanfu
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysMenuVO implements Serializable {

	private static final long serialVersionUID = -8036122418979736148L;

	/**
	 * 子节点
	 */
	private List<SysMenuVO> children;

	/**
	 * 菜单ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	private Long id;

	/**
	 * 菜单标题
	 */
	private String name;

	/**
	 * 菜单权限
	 */
	private String permission;

	/**
	 * 路径
	 */
	private String path;

	/**
	 * 父菜单ID
	 */
	private Long parentId;

	/**
	 * 菜单图标
	 */
	private String icon;

	/**
	 * 菜单类型
	 */
	private String type;

	/**
	 * 排序值
	 */
	private Integer sort;

	private MenuMeta meta;

	private String component;

	private Boolean hidden = false;

	private String redirect;

	private Boolean alwaysShow = false;

	private String typeName;

//    private String label;

	public void addChildren(SysMenuVO tree) {
		this.children.add(tree);
	}

}
