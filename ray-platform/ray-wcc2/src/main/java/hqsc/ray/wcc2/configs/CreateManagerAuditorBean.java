//package hqsc.ray.wcc2.configs;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Optional;
//
///**
// * 描述：实现jpa自动插入数据创建人字段的配置
// *
// * @author Administrator
// * @date 2020-10-29
// */
//@Configuration
//public class CreateManagerAuditorBean implements AuditorAware<String> {
//
//	@Override
//	public Optional<String> getCurrentAuditor() {
//		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//		// 定时任务更新数据的时候无法获取request，就不做数据的更新记录了
//		if (servletRequestAttributes == null) {
//			return Optional.of("");
//		}
//		HttpServletRequest request = servletRequestAttributes.getRequest();
//		Manager managerUser = Call.getManagerUser(request);
//		if (managerUser == null) {
//			return Optional.of("");
//		}
//		return Optional.of(String.valueOf(managerUser.getId()));
//	}
//}
