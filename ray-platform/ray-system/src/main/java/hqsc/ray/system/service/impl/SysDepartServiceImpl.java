package hqsc.ray.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import hqsc.ray.core.web.tree.ForestNodeMerger;
import hqsc.ray.system.entity.SysDepart;
import hqsc.ray.system.mapper.SysDepartMapper;
import hqsc.ray.system.poi.SysDepartPOI;
import hqsc.ray.system.service.ISysDepartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import hqsc.ray.system.vo.SysDepartVO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 组织机构表 服务实现类
 * </p>
 *
 * @author xuzf
 * @since 2020-06-28
 */
@Service
public class SysDepartServiceImpl extends ServiceImpl<SysDepartMapper, SysDepart> implements ISysDepartService {

    @Override
    public List<SysDepartVO> tree() {
        return baseMapper.tree();
    }

    @Override
    public List<SysDepartVO> searchList(Map<String, Object> search) {
        String keyword = String.valueOf(search.get("keyword"));
        String startDate = String.valueOf(search.get("startDate"));
        String endDate = String.valueOf(search.get("endDate"));
        LambdaQueryWrapper<SysDepart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(startDate) && !startDate.equals("null")) {
            lambdaQueryWrapper.between(SysDepart::getCreateTime, startDate, endDate);
        }
        if (StringUtils.isNotBlank(keyword) && !keyword.equals("null")) {
            lambdaQueryWrapper.like(SysDepart::getName, keyword);
            lambdaQueryWrapper.or();
            lambdaQueryWrapper.like(SysDepart::getId, keyword);
        }
        List<SysDepart> sysDeparts = this.baseMapper.selectList(lambdaQueryWrapper);
        List<SysDepartVO> sysDepartVOS = sysDeparts.stream().map(sysDepart -> {
            SysDepartVO sysDepartVO = new SysDepartVO();
            BeanUtils.copyProperties(sysDepart, sysDepartVO);
            return sysDepartVO;
        }).collect(Collectors.toList());
        return ForestNodeMerger.merge(sysDepartVOS);
    }

    @Override
    public List<SysDepartPOI> export() {
        LambdaQueryWrapper<SysDepart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDepart::getIsDeleted, 0);
        List<SysDepart> sysDeparts = this.baseMapper.selectList(queryWrapper);
        return sysDeparts.stream().map(sysDepart -> {
            SysDepartPOI sysDepartPOI = new SysDepartPOI();
            BeanUtils.copyProperties(sysDepart, sysDepartPOI);
            return sysDepartPOI;
        }).collect(Collectors.toList());
    }
}
