package hqsc.ray.wcc2.repository;

import hqsc.ray.wcc2.entity.WccMcnInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccMcnInfoRepository extends JpaRepository<WccMcnInfo, Integer>, JpaSpecificationExecutor {

}
