package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.WccExamForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccExamService {

	/**
	 * 获取数据
	 *
	 * @param wccExamForm
	 * @return ResultMap
	 */
	ResultMap listWccExams(WccExamForm wccExamForm);

}
