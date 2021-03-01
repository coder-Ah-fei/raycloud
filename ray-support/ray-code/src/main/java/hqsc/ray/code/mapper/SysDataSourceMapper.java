package hqsc.ray.code.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hqsc.ray.code.entity.SysDataSource;
import hqsc.ray.code.vo.SysDataSourceVO;

import java.util.List;

/**
 * <p>
 * 数据源表 Mapper 接口
 * </p>
 *
 * @author xuzf
 * @since 2020-07-07
 */
public interface SysDataSourceMapper extends BaseMapper<SysDataSource> {

    List<SysDataSourceVO> optionList();

}
