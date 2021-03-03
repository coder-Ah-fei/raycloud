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
package hqsc.ray.wcc2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hqsc.ray.wcc2.entity.base.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户消息表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@ApiModel(value = "WccUserMessage对象", description = "用户消息表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "wcc_user_message")
@org.hibernate.annotations.Table(appliesTo = "wcc_user_message", comment = "用户消息表")
public class WccUserMessage extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	@Id
	@Column(name = "MESSAGE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc2.configs.MyInsertGenerator")
	private Long id;
	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	@JsonIgnore
	private WccUser wccUser;
	/**
	 * 消息类型(0评论1回答2赞同3系统消息)
	 */
	@ApiModelProperty(value = "消息类型(0评论1回答2赞同3系统消息)")
	@Column(name = "MESSAGE_TYPE")
	private Integer messageType;
	/**
	 * 消息内容
	 */
	@ApiModelProperty(value = "消息内容")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "MESSAGE_CONTENT")
	private String messageContent;
	/**
	 * 消息时间
	 */
	@ApiModelProperty(value = "消息时间")
	@Column(name = "MESSAGE_TIME")
	private Date messageTime;
	
	
	@ApiModelProperty(value = "1 已读  0 未读")
	@Column(name = "IS_READ", columnDefinition = "char(1) default 0 comment '已读标识（1 已读  0 未读）'")
	private Integer isRead;
	
}
