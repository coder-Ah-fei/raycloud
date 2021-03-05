package hqsc.ray.wcc.jpa.entity;

import hqsc.ray.wcc.jpa.entity.base.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * @author yang
 */
@ApiModel(value = "WccMcnInfo对象", description = "mcn机构对象")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "wcc_mcn_info")
@org.hibernate.annotations.Table(appliesTo = "wcc_mcn_info", comment = "mcn机构")
public class JpaWccMcnInfo extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "MCN_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc.jpa.configs.MyInsertGenerator")
	private Long id;
	/**
	 * 机构图标
	 */
	@ApiModelProperty(value = "机构图标")
	@OneToOne
	@JoinColumn(name = "ICON", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private JpaSysAttachment icon;
	/**
	 * 标题
	 */
	@ApiModelProperty(value = "标题")
	@Column(name = "O_NAME")
	private String oName;
	/**
	 * 机构坐标
	 */
	@ApiModelProperty(value = "机构坐标")
	@Column(name = "ADDRESS")
	private String address;
	/**
	 * 领域
	 */
	@ApiModelProperty(value = "领域")
	@Column(name = "FIELD")
	private String field;
	
	/**
	 * 粉丝数量
	 */
	@ApiModelProperty(value = "粉丝数量")
	@Column(name = "FANS")
	private Integer fansCount;
	
	/**
	 * 红人数量
	 */
	@ApiModelProperty(value = "红人数量")
	@Column(name = "CELEBRITYCOUNT")
	private Integer celebrityCount;
	
}
