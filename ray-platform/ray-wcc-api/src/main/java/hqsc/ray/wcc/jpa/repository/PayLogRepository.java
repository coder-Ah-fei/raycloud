package hqsc.ray.wcc.jpa.repository;

import hqsc.ray.wcc.jpa.entity.PayLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface PayLogRepository extends JpaRepository<PayLog, Long>, JpaSpecificationExecutor {
	
	
	/**
	 * 根据微信预支付id查询
	 *
	 * @param prePayId
	 * @return
	 */
	PayLog findByPrePayId(String prePayId);
	
}
