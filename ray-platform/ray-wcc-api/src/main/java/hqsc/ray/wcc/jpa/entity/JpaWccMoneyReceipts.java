/*
 * Copyright 2020-2030, RayCloud, DAOTIANDI Technology Inc All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Author: pangu(7333791@qq.com)
 */
package hqsc.ray.wcc.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hqsc.ray.wcc.jpa.entity.base.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 赏金收入表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@ApiModel(value = "WccMoneyReceipts对象", description = "赏金收入表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "wcc_money_receipts")
@org.hibernate.annotations.Table(appliesTo = "wcc_money_receipts", comment = "赏金收入表")
public class JpaWccMoneyReceipts extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "主键id")
	@Id
	@Column(name = "MONEY_RECEIPTS_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc.jpa.configs.MyInsertGenerator")
	private Long id;
	/**
	 * 收入用户id
	 */
	@ApiModelProperty(value = "收入用户id")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	@JsonIgnore
	private JpaWccUser jpaWccUser;
	/**
	 * 支付用户id
	 */
	@ApiModelProperty(value = "支付用户id")
	@Column(name = "PAY_USER_ID")
	private String payUserId;
	/**
	 * 收入类型
	 */
	@ApiModelProperty(value = "收入类型")
	@Column(name = "RECEIPTS_TYPE")
	private String receiptsType;
	/**
	 * 收入时间
	 */
	@ApiModelProperty(value = "收入时间")
	@Column(name = "RECEIPTS_TIME")
	private Date receiptsTime;
	/**
	 * 金额
	 */
	@ApiModelProperty(value = "金额")
	@Column(name = "MONEY")
	private BigDecimal money;
}
