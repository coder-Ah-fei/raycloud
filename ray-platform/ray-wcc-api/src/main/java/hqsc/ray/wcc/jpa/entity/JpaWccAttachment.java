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

import hqsc.ray.wcc.jpa.entity.base.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 附件表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@ApiModel(value = "WccAttachment对象", description = "附件表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "wcc_attachment")
@org.hibernate.annotations.Table(appliesTo = "wcc_attachment", comment = "附件表实体类")
public class JpaWccAttachment extends BasicEntity {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ATTACHMENT_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc.jpa.configs.MyInsertGenerator")
	private Long id;
	
	/**
	 * 文件名
	 */
	@ApiModelProperty(value = "文件名")
	@Column(name = "FILE_NAME", columnDefinition = "varchar(100) null comment '文件名'")
	private String fileName;
	/**
	 * 文件路径
	 */
	@ApiModelProperty(value = "文件路径")
	@Column(name = "FILE_PATH", columnDefinition = "varchar(1024) null comment '文件路径'")
	private String filePath;
	/**
	 * 文件类型
	 */
	@ApiModelProperty(value = "文件类型")
	@Column(name = "FILE_TYPE", columnDefinition = "varchar(200) null comment '文件类型'")
	private String fileType;
	/**
	 * 文件长度
	 */
	@ApiModelProperty(value = "文件长度")
	@Column(name = "FILE_LENGTH")
	private BigDecimal fileLength;
	/**
	 * 文件大小
	 */
	@ApiModelProperty(value = "文件大小")
	@Column(name = "FILE_SIZE")
	private String fileSize;
	/**
	 * 文件拓展名
	 */
	@ApiModelProperty(value = "文件拓展名")
	@Column(name = "FILE_EXTEND")
	private String fileExtend;
	
}
