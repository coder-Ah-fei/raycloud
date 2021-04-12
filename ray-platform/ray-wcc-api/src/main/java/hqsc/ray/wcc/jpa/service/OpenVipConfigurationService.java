package hqsc.ray.wcc.jpa.service;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.OpenVipConfigurationDto;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.form.OpenVipConfigurationForm;

/**
 * 描述：
 *
 * @author yang
 * @date 2021-04-07
 */
public interface OpenVipConfigurationService {
	
	/**
	 * 获取开通会员的配置列表
	 *
	 * @param openVipConfigurationForm
	 * @return
	 */
	PageMap<OpenVipConfigurationDto> listOpenMembershipConfigurations(OpenVipConfigurationForm openVipConfigurationForm);
	
	/**
	 * 保存更新配置
	 *
	 * @param openVipConfigurationForm
	 * @return
	 */
	Result<?> save(OpenVipConfigurationForm openVipConfigurationForm);
}
