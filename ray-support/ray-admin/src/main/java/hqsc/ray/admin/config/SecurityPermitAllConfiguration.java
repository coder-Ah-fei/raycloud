package hqsc.ray.admin.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.discovery.NacosWatch;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
//import hqsc.ray.admin.message.DingDingNotifier;

/**
 * Spring Security 自定义拦截器
 *
 * @author pangu
 */
@Configuration
public class SecurityPermitAllConfiguration extends WebSecurityConfigurerAdapter {

	private final String adminContextPath;

	public SecurityPermitAllConfiguration(AdminServerProperties adminServerProperties) {
		this.adminContextPath = adminServerProperties.getContextPath();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		successHandler.setTargetUrlParameter("redirectTo");
		successHandler.setDefaultTargetUrl(adminContextPath + "/");

		http.authorizeRequests()
				.antMatchers(adminContextPath + "/instances").permitAll()
				.antMatchers(adminContextPath + "/actuator/**").permitAll()
				.antMatchers(adminContextPath + "/assets/**").permitAll()
				.antMatchers(adminContextPath + "/login").permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler)
				.and()
				.logout().logoutUrl(adminContextPath + "/logout")
				.and()
				.httpBasic()
				.and()
				.csrf().disable();
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(value = "spring.cloud.nacos.discovery.watch.enabled", matchIfMissing = true)
	public NacosWatch nacosWatch(NacosServiceManager nacosServiceManager,
	                             NacosDiscoveryProperties properties,
	                             ObjectProvider<TaskScheduler> taskScheduler) {
		return new NacosWatch(nacosServiceManager, properties, taskScheduler);
	}

//	@Bean
//	@ConditionalOnMissingBean
//	@ConditionalOnProperty(value = "spring.boot.admin.notify.dingding.enabled", havingValue = "true")
//	public DingDingNotifier dingDingNotifier(InstanceRepository repository) {
//		return new DingDingNotifier(repository);
//	}

}
