package hqsc.ray.wcc.jpa.repository;

import hqsc.ray.wcc.jpa.entity.JpaWccBrandMerchantAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccBrandMerchantAuthRepository extends JpaRepository<JpaWccBrandMerchantAuth, Long>, JpaSpecificationExecutor {

}
