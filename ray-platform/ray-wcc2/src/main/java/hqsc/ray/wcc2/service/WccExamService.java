package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.WccExamForm;

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
