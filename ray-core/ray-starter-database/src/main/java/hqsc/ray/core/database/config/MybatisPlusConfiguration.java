package hqsc.ray.core.database.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import hqsc.ray.core.database.handler.RayMetaObjectHandler;
import hqsc.ray.core.database.props.TenantProperties;
import hqsc.ray.core.mybatis.injector.RaySqlInjector;
import hqsc.ray.core.mybatis.interceptor.SqlLogInterceptor;
import hqsc.ray.core.mybatis.props.RayMybatisProperties;
import lombok.AllArgsConstructor;
import org.apache.ibatis.type.EnumTypeHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import hqsc.ray.core.common.factory.YamlPropertySourceFactory;

/**
 * mybatis plus配置中心
 *
 * @author xuzhanfu
 * @author L.cm
 */
@Configuration
@AllArgsConstructor
@EnableTransactionManagement
@EnableConfigurationProperties(RayMybatisProperties.class)
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:ray-db.yml")
@MapperScan("hqsc.ray.**.mapper.**")
public class MybatisPlusConfiguration {

	private final TenantProperties tenantProperties;

	private final TenantLineInnerInterceptor tenantLineInnerInterceptor;

	/**
	 * 单页分页条数限制(默认无限制,参见 插件#handlerLimit 方法)
	 */
	private static final Long MAX_LIMIT = 1000L;

	/**
	 * sql 注入
	 */
	@Bean
	public ISqlInjector sqlInjector() {
		return new RaySqlInjector();
	}

	/**
	 * sql 日志
	 */
	@Bean
	@ConditionalOnProperty(value = "mybatis-plus.sql-log.enable", matchIfMissing = true)
	public SqlLogInterceptor sqlLogInterceptor() {
		return new SqlLogInterceptor();
	}

	/**
	 * 新的分页插件,一缓和二缓遵循mybatis的规则,
	 * 需要设置 MybatisConfiguration#useDeprecatedExecutor = false
	 * 避免缓存出现问题(该属性会在旧插件移除后一同移除)
	 */
	@Bean
	public MybatisPlusInterceptor paginationInterceptor() {
		boolean enableTenant = tenantProperties.getEnable();
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		if (enableTenant) {
			interceptor.addInnerInterceptor(tenantLineInnerInterceptor);
		}
		//分页插件: PaginationInnerInterceptor
		PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
		paginationInnerInterceptor.setMaxLimit(MAX_LIMIT);
		//防止全表更新与删除插件: BlockAttackInnerInterceptor
		BlockAttackInnerInterceptor blockAttackInnerInterceptor = new BlockAttackInnerInterceptor();
		interceptor.addInnerInterceptor(paginationInnerInterceptor);
		interceptor.addInnerInterceptor(blockAttackInnerInterceptor);
		return interceptor;
	}

	/**
	 * 自动填充数据
	 */
	@Bean
	@ConditionalOnMissingBean(RayMetaObjectHandler.class)
	public RayMetaObjectHandler rayMetaObjectHandler() {
		return new RayMetaObjectHandler();
	}


	/**
	 * IEnum 枚举配置
	 */
	@Bean
	public ConfigurationCustomizer configurationCustomizer() {
		return new MybatisPlusCustomizers();
	}

	/**
	 * 自定义配置
	 */
	public static class MybatisPlusCustomizers implements ConfigurationCustomizer {

		@Override
		public void customize(MybatisConfiguration configuration) {
			configuration.setDefaultEnumTypeHandler(EnumTypeHandler.class);
		}
	}

	/**
	 * mybatis-plus 乐观锁拦截器
	 */
	@Bean
	public OptimisticLockerInnerInterceptor optimisticLockerInterceptor() {
		return new OptimisticLockerInnerInterceptor();
	}
}
