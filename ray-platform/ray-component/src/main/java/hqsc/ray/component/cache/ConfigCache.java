package hqsc.ray.component.cache;

import hqsc.ray.component.service.ISysConfigService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * OSS配置文件初始化
 * 将数据库的配置信息读取到redis作为缓存数据
 *
 * @author pangu
 */
@Slf4j
@Service
@AllArgsConstructor
public class ConfigCache {

	private final ISysConfigService sysConfigService;

	@PostConstruct
	public void init() {
		sysConfigService.clearOss();
		//加载OSS配置文件
		sysConfigService.getOssProperties();
	}

	@PreDestroy
	public void destroy() {
		//系统运行结束
		sysConfigService.clearOss();
	}

	@Scheduled(cron = "0 0 0/2 * * ?")
	public void taskInit() {
		//每2小时执行一次缓存
		init();
	}
}
