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
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.List;

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
	 * 简称
	 */
	@ApiModelProperty(value = "简称")
	@Column(name = "O_NAME")
	private String oname;
	
	
	/**
	 * 全称
	 */
	@ApiModelProperty(value = "全称")
	@Column(name = "FULL_NAME")
	private String fullName;
	
	/**
	 * 结算方式
	 */
	@ApiModelProperty(value = "结算方式")
	@Column(name = "SETTLE_TYPE")
	private String settleType;
	
	/**
	 * 平台网址
	 */
	@ApiModelProperty(value = "平台网址")
	@Column(name = "INTERNET_SITE")
	private String internetSite;
	
	/**
	 * 平台介绍
	 */
	@ApiModelProperty(value = "平台介绍")
	@Column(name = "SUMMARY")
	@Lazy
	@Lob
	private String summary;
	
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
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "jpaWccMcnInfo")
	private List<JpaWccCelebrityInfo> celebrityInfoList;
}
