package hqsc.ray.wcc.jpa.repository;

import hqsc.ray.wcc.jpa.entity.JpaSysRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface SysRouteRepository extends JpaRepository<JpaSysRoute, Long>, JpaSpecificationExecutor {

}
