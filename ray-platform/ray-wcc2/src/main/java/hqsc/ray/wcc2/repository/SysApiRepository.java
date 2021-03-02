package hqsc.ray.wcc2.repository;

import hqsc.ray.wcc2.entity.SysApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface SysApiRepository extends JpaRepository<SysApi, Long>, JpaSpecificationExecutor {

}
