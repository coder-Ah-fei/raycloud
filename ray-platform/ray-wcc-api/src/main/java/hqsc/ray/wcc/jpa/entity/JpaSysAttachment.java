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
 * 附件表实体类
 *
 * @author pangu
 * @since 2020-08-09
 */
@ApiModel(value = "SysAttachment对象", description = "附件表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "ray_sys_attachment")
@org.hibernate.annotations.Table(appliesTo = "ray_sys_attachment", comment = "附件表")
public class JpaSysAttachment extends BasicSysEntity {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc.jpa.configs.MyInsertGenerator")
	private Long id;
	/**
	 * 存储ID
	 */
	@ApiModelProperty(value = "存储ID")
	@Column(name = "storage_id")
	private Long storageId;
	/**
	 * 组ID
	 */
	@ApiModelProperty(value = "组ID")
	@Column(name = "attachment_group_id")
	private Integer attachmentGroupId;
	/**
	 * 文件名称
	 */
	@ApiModelProperty(value = "文件名称")
	private String name;
	/**
	 * 文件大小
	 */
	@ApiModelProperty(value = "文件大小")
	private long size;
	/**
	 * 文件地址
	 */
	@ApiModelProperty(value = "文件地址")
	private String url;
	
	/**
	 * 文件路径
	 */
	@ApiModelProperty(value = "文件路径")
	private String path;
	
	/**
	 * 视频文件的截图路径
	 */
	@ApiModelProperty(value = "视频文件的截图路径")
	@Column(name = "video_screenshot_path")
	private String videoScreenshotPath;
	
	@ApiModelProperty(value = "视频文件的hls路径")
	@Column(name = "video_hls_path")
	private String videoHlsPath;
	
	/**
	 * 上传文件名
	 */
	@ApiModelProperty(value = "上传文件名")
	@Column(name = "file_name")
	private String fileName;
	/**
	 * 文件扩展名
	 *
	 * @mbg.generated
	 */
	@ApiModelProperty(value = "文件扩展名")
	@Column(name = "file_extend")
	private String fileExtend;
	
	/**
	 * 缩略图地址
	 */
	@ApiModelProperty(value = "缩略图地址")
	@Column(name = "thumb_url")
	private String thumbUrl;
	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型")
	private Integer type;
	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人")
	@Column(name = "create_by")
	private String createBy;
	/**
	 * 更新人
	 */
	@ApiModelProperty(value = "更新人")
	@Column(name = "update_by")
	private String updateBy;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@Column(name = "create_time")
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	@Column(name = "update_time")
	private LocalDateTime updateTime;
	/**
	 * 删除标识
	 */
	@ApiModelProperty(value = "删除标识")
	@Column(name = "is_deleted")
	private String isDeleted;
	/**
	 * 是否加入回收站 0.否|1.是
	 */
	@ApiModelProperty(value = "是否加入回收站 0.否|1.是")
	@Column(name = "is_recycle")
	private Boolean isRecycle;
	
	/**
	 * 是否需要权限访问 0.否|1.是
	 */
	@ApiModelProperty(value = "是否需要权限访问 0.否|1.是")
	private Boolean authority;
}
