package hqsc.ray.wcc.jpa.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 描述：
 *
 * @author Administrator
 */
@Getter
@Setter
@Accessors(chain = true)
public class WccCourseChapterDto {
	
	
	@ApiModelProperty(value = "主键id")
	private Long id;
	
	@ApiModelProperty(value = "课程id")
	private Long courseId;
	private String courseTitle;
	@ApiModelProperty(value = "章节标题")
	private String chapterTitle;
	@ApiModelProperty(value = "图文内容")
	private String chapterContent;
	
	@ApiModelProperty(value = "对应的附件资源，视频附件")
	private Long attachmentId;
	/**
	 * 视频文件的截图路径
	 */
	@ApiModelProperty(value = "视频文件的截图路径")
	private String videoScreenshotPath;
	
	@ApiModelProperty(value = "视频文件的hls路径")
	private String videoHlsPath;
	/**
	 * 上传文件名
	 */
	@ApiModelProperty(value = "上传文件名")
	private String fileName;
	
	
	@ApiModelProperty(value = "章节类型（1、图文 2、视频）")
	private Integer chapterType;
	private String chapterTypeStr;
	
	@ApiModelProperty(value = "排序")
	private Integer sort;
}
