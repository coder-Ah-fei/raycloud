package hqsc.ray.wcc.jpa.service;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.WccReleaseInfoForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccReleaseInfoService {
	
	/**
	 * 获取数据
	 *
	 * @param wccReleaseInfoForm
	 * @return ResultMap
	 */
	ResultMap listWccReleaseInfos(WccReleaseInfoForm wccReleaseInfoForm);
	
	/**
	 * 发布内容审批
	 *
	 * @param wccReleaseInfoForm
	 * @return
	 */
	Result<?> approve(WccReleaseInfoForm wccReleaseInfoForm);
	
	/**
	 * 保存新增/编辑的发布内容
	 *
	 * @param releaseInfoForm
	 * @return
	 */
	Result<?> saveOrUpdate(WccReleaseInfoForm releaseInfoForm);
}
