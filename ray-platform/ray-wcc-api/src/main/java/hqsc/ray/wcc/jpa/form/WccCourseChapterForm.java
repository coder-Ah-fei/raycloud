package hqsc.ray.wcc.jpa.form;

import hqsc.ray.wcc.jpa.form.basic.BaseForm;
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
public class WccCourseChapterForm extends BaseForm {
	
	
	@ApiModelProperty(value = "当前登录的用户的id")
	private Long userId;
	
	@ApiModelProperty(value = "主键id")
	private Long id;
	
	@ApiModelProperty(value = "课程id")
	private Long courseId;
	
	@ApiModelProperty(value = "对应的附件资源，视频附件")
	private Long attachmentId;
	
	@ApiModelProperty(value = "章节标题")
	private String chapterTitle;
	@ApiModelProperty(value = "图文内容")
	private String chapterContent;
	
	@ApiModelProperty(value = "章节类型（1、图文 2、视频）")
	private Integer chapterType;
	
	@ApiModelProperty(value = "排序")
	private Integer sort;
}
