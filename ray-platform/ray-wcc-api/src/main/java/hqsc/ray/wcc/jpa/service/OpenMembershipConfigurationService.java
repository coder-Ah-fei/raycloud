package hqsc.ray.wcc.jpa.service;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.OpenMembershipConfigurationDto;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.form.OpenMembershipConfigurationForm;

/**
 * 描述：
 *
 * @author yang
 * @date 2021-04-07
 */
public interface OpenMembershipConfigurationService {
	
	/**
	 * 获取开通会员的配置列表
	 *
	 * @param openMembershipConfigurationForm
	 * @return
	 */
	PageMap<OpenMembershipConfigurationDto> listOpenMembershipConfigurations(OpenMembershipConfigurationForm openMembershipConfigurationForm);
	
	/**
	 * 保存更新配置
	 *
	 * @param openMembershipConfigurationForm
	 * @return
	 */
	Result<?> save(OpenMembershipConfigurationForm openMembershipConfigurationForm);
}
