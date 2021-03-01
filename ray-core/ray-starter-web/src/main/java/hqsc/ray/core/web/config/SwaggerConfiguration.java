package hqsc.ray.core.web.config;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.PathProvider;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.paths.DefaultPathProvider;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import hqsc.ray.core.common.constant.RayConstant;
import hqsc.ray.core.common.factory.YamlPropertySourceFactory;
import hqsc.ray.core.web.props.RaySwaggerProperties;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Swagger配置类，提供给WEB服务使用
 *
 * @author pangu
 * 2020-7-5
 */
@Configuration
@EnableOpenApi
@AllArgsConstructor
@Profile({"!prod"})
@Import(BeanValidatorPluginsConfiguration.class)
@EnableConfigurationProperties(RaySwaggerProperties.class)
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:ray-swagger.yml")
public class SwaggerConfiguration implements WebMvcConfigurer {

	private final RaySwaggerProperties swaggerProperties;

	@Bean
	public PathProvider pathProvider() {
		return new DefaultPathProvider() {
			@Override
			public String getOperationPath(String operationPath) {
				return super.getOperationPath(operationPath);
			}
		};
	}

	/**
	 * Swagger忽略的参数类型
	 */
	private final Class[] ignoredParameterTypes = new Class[]{
			ServletRequest.class,
			ServletResponse.class,
			HttpServletRequest.class,
			HttpServletResponse.class,
			HttpSession.class,
			ApiIgnore.class
	};

	@Bean(value = "userApi")
	public Docket createRestApi() {
		ApiSelectorBuilder apiSelectorBuilder = new Docket(DocumentationType.OAS_30)
				// 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
				.apiInfo(groupApiInfo())
				// 设置哪些接口暴露给Swagger展示
				.select();
		if (swaggerProperties.getBasePackage() == null) {
			// 扫描所有有注解的api，用这种方式更灵活
			apiSelectorBuilder.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class));
		} else {
			// 扫描指定的包
			apiSelectorBuilder.apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()));
		}
		return apiSelectorBuilder.paths(PathSelectors.any())
				.build()
				.enable(swaggerProperties.isEnable())
				.securitySchemes(securitySchemes())
				.securityContexts(securityContexts())
				.pathProvider(pathProvider())
				.ignoredParameterTypes(ignoredParameterTypes)
				.pathMapping("/");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");
		registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	private ApiInfo groupApiInfo() {
		return new ApiInfoBuilder()
				.title(swaggerProperties.getTitle())
				.description(swaggerProperties.getDescription())
				.license(swaggerProperties.getLicense())
				.termsOfServiceUrl(swaggerProperties.getServiceUrl())
				.contact(new Contact(swaggerProperties.getContactName(),
						swaggerProperties.getContactUrl(),
						swaggerProperties.getContactEmail()))
				.version(RayConstant.RAY_APP_VERSION)
				.build();
	}


	private List<SecurityScheme> securitySchemes() {
		List<ApiKey> apiKeyList = new ArrayList<>();
		apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
		apiKeyList.add(new ApiKey("Ray-Auth", "Ray-Auth", "header"));
		return Collections.unmodifiableList(apiKeyList);
	}

	/**
	 * swagger2 认证的安全上下文
	 */
	private List<SecurityContext> securityContexts() {
		List<SecurityContext> securityContexts = new ArrayList<>();
		securityContexts.add(
				SecurityContext.builder()
						.securityReferences(defaultAuth())
						.forPaths(PathSelectors.regex("^(?!auth).*$"))
						.build());
		return securityContexts;
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		List<SecurityReference> securityReferences = new ArrayList<>();
		securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
		return securityReferences;
	}
}
