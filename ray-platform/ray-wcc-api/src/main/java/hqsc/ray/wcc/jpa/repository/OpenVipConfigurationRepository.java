package hqsc.ray.wcc.jpa.repository;

import hqsc.ray.wcc.jpa.entity.OpenVipConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface OpenVipConfigurationRepository extends JpaRepository<OpenVipConfiguration, Long>, JpaSpecificationExecutor {

}
