package hqsc.ray.wcc.controller.findCelebrity;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hqsc.ray.core.auth.annotation.UserAuth;
import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.common.util.StringUtil;
import hqsc.ray.core.log.annotation.Log;
import hqsc.ray.core.web.controller.BaseController;
import hqsc.ray.wcc.entity.MCNOrganizationInfo;
import hqsc.ray.wcc.entity.WccCelebrityInfo;
import hqsc.ray.wcc.entity.WccMerchantBrandDetail;
import hqsc.ray.wcc.entity.WccOrganizationDetail;
import hqsc.ray.wcc.service.IWccCelebrityInfoService;
import hqsc.ray.wcc.service.IWccMerchantBrandDetailService;
import hqsc.ray.wcc.service.IWccOrganizationDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/findCelebrity")
@Api(value = "找红人", tags = "找红人接口")
public class FindCelebrityController extends BaseController {

    private IWccOrganizationDetailService wccOrganizationDetailService;

    private IWccCelebrityInfoService wccCelebrityInfoService;

    private IWccMerchantBrandDetailService wccMerchantBrandDetailService;

    /*
     * Param  机构类型
     * 查询机构list信息
     */
    @UserAuth
    @Log(value = "根据机构类型查找机构", exception = "根据机构类型查找机构异常")
    @PostMapping(value = {"/findOrganizationByType"})
    @ApiOperation(value = "根据机构类型查找机构", notes = "根据机构类型查找机构")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form"),
            @ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form"),
            @ApiImplicitParam(name = "type", required = true, value = "机构类型", paramType = "form"),
    })
    public Result<?> findOrganizationByType(@RequestBody Map<String, Object> search){
        if(Long.parseLong(search.get("current").toString())<1||Long.parseLong(search.get("size").toString())<1||search.get("type")==null||"".equals(search.get("type"))){
            return Result.fail("非法参数！");
        }
        Page<Object> page = new Page<>();
        page.setCurrent(Long.parseLong(search.get("current").toString()));
        page.setSize(Long.parseLong(search.get("size").toString()));
        HashMap<String, Object> map = new HashMap<>();
        map.put("TYPE",search.get("type"));
        List<WccOrganizationDetail> organizationDetailIPage = null;
        try {
            organizationDetailIPage = wccOrganizationDetailService.listPageByType(page, map);
        }catch (Exception e){
            return Result.fail("查询机构信息异常！");
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("oganizations",organizationDetailIPage);
        return Result.data(hashMap);
    }

    /*
     * Param  机构坐标地址
     * 查询机构list信息
     */
    @UserAuth
    @Log(value = "根据机构坐标查找机构", exception = "根据机构坐标查找机构异常")
    @PostMapping(value = {"/findOrganizationByAddress"})
    @ApiOperation(value = "根据机构坐标查找机构", notes = "根据机构坐标查找机构")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "address", required = true, value = "机构类型", paramType = "form"),
            @ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form"),
            @ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form"),
    })
    public Result<?> findOrganizationByAddress(@RequestBody Map<String, Object> search){
        if(Long.parseLong(search.get("current").toString())<1||Long.parseLong(search.get("size").toString())<1||search.get("address").toString()==null||"".equals(search.get("address").toString())||"null".equals(search.get("address").toString())){
            return Result.fail("非法参数！");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("ADDRESS",search.get("address").toString());
        Page<Object> page = new Page<>();
        page.setCurrent(Long.parseLong(search.get("current").toString()));
        page.setSize(Long.parseLong(search.get("size").toString()));
        List<WccOrganizationDetail> organizationDetailIPage = null;
        try {
            organizationDetailIPage = wccOrganizationDetailService.listPageByAddress(page, map);
        }catch (Exception e){
            return Result.fail("查询机构信息异常！");
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("oganizations",organizationDetailIPage);
        return Result.data(hashMap);
    }

    /*
     * Param  机构领域
     * 查询机构list信息
     */
    @UserAuth
    @Log(value = "根据机构领域查找机构", exception = "根据机构领域查找机构异常")
    @PostMapping(value = {"/findOrganizationByField"})
    @ApiOperation(value = "根据机构领域查找机构", notes = "根据机构领域查找机构")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "field", required = true, value = "机构类型", paramType = "form"),
            @ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form"),
            @ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form"),
    })
    public Result<?> findOrganizationByField(@RequestBody Map<String, Object> search){
        if(Long.parseLong(search.get("current").toString())<1||Long.parseLong(search.get("size").toString())<1||search.get("field").toString()==null||"".equals(search.get("field").toString())||"null".equals(search.get("field").toString())){
            return Result.fail("非法参数！");
        }
        Page<Object> page = new Page<>(Long.parseLong(search.get("current").toString()), Long.parseLong(search.get("size").toString()));
        HashMap<String, Object> map = new HashMap<>();
        map.put("FIELD",search.get("field").toString());
        List<WccOrganizationDetail> organizationDetailIPage = null;
        try {
            organizationDetailIPage = wccOrganizationDetailService.listPageByField(page, map);
        }catch (Exception e){
            return Result.fail("查询机构信息异常！");
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("oganizations",organizationDetailIPage);
        return Result.data(hashMap);
    }

    /*
     * Param  红人id
     * 查询红人信息
     */
    @UserAuth
    @Log(value = "根据红人id查找红人", exception = "根据红人id查找红人异常")
    @PostMapping(value = {"/getCelebrityInfo"})
    @ApiOperation(value = "根据红人id查找红人", notes = "根据红人id查找红人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "红人id", paramType = "form"),
    })
    public Result<?> getCelebrityInfo(@RequestParam(required = true, value = "id") String id){
        if("".equals(id)||id==null){
            return Result.fail("非法参数！");
        }
        WccCelebrityInfo wccCelebrityInfo = null;
        try {
            wccCelebrityInfo = wccCelebrityInfoService.getById(id);
            HashMap<String, Object> map = new HashMap<>();
            map.put("wccCelebrityInfo",wccCelebrityInfo);
            return Result.data(map);
        }catch (Exception e){
            return Result.fail("查询红人信息异常！");
        }
    }

    /*
    * Param  机构id
    * 查询机构详细信息
    * */
    @UserAuth
    @Log(value = "根据机构id查找机构", exception = "根据机构id查找机构异常")
    @PostMapping({"/getOrganizationInfo"})
    @ApiOperation(value = "根据机构id查找机构", notes = "根据机构id查找机构")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "organizationid", required = true, value = "红人id", paramType = "form"),
    })
    public Result<?> getOrganizationInfo(@RequestParam(required = true, value = "organizationid") String organizationid){
        if("".equals(organizationid)||organizationid==null){
            return Result.fail("非法参数！");
        }
        WccOrganizationDetail wccOrganizationDetail = null;
        try {
            wccOrganizationDetail = wccOrganizationDetailService.getById(organizationid);
            HashMap<String, Object> map = new HashMap<>();
            map.put("organizationDetail",wccOrganizationDetail);
            return Result.data(map);
        }catch (Exception e){
            return Result.fail("查询机构信息异常！");
        }
    }

    /*
    * Param  商家名称，平台，商家联系方式，备注
    * 商户临时注册
    * */
    @UserAuth
    @Log(value = "商户临时注册", exception = "商户临时注册异常")
    @PostMapping(value = {"/addMerchant"})
    @ApiOperation(value = "商户临时注册", notes = "商户临时注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", required = true, value = "商家名称", paramType = "form"),
            @ApiImplicitParam(name = "platform", required = false, value = "平台", paramType = "form"),
            @ApiImplicitParam(name = "phone", required = true, value = "手机号", paramType = "form"),
            @ApiImplicitParam(name = "remark", required = false, value = "备注", paramType = "form"),
    })
    public Result<?> addMerchant(@RequestParam(required = true, value = "name") String name,
                               @RequestParam(required = false, defaultValue = "", value = "platform") String platform,
                               @RequestParam(required = true, value = "phone") String phone,
                               @RequestParam(required = false, defaultValue = "", value = "remark") String remark){
        LambdaQueryWrapper<WccMerchantBrandDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!ObjectUtils.isEmpty(phone),WccMerchantBrandDetail::getPhone,phone);
        if(wccMerchantBrandDetailService.count(wrapper) >= 1) return Result.fail("该手机号已注册！");
        WccMerchantBrandDetail wccMerchantBrandDetail = new WccMerchantBrandDetail();
        wccMerchantBrandDetail.setName(name);
        wccMerchantBrandDetail.setPlatform(platform);
        wccMerchantBrandDetail.setPhone(phone);
        wccMerchantBrandDetail.setRemark(remark);
        boolean save = false;
        try {
            save = wccMerchantBrandDetailService.save(wccMerchantBrandDetail);
            return save ? Result.success("注册成功！") : Result.fail("注册失败！");
        }catch (Exception e){
            return Result.fail("添加商户异常");
        }
    }

    /*
    *  根据粉丝量排名的机构信息
    * */
    @UserAuth
    @Log(value = "根据粉丝量排名查找机构", exception = "根据粉丝量排名查找机构异常")
    @PostMapping(value = {"/findOrganizationByFansRank"})
    @ApiOperation(value = "根据粉丝量排名查找机构", notes = "根据粉丝量排名查找机构")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form"),
            @ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form"),
    })
    public Result<?> findOrganizationByFansRank(@RequestBody Page page){
        if(page.getCurrent()<1||page.getSize()<1){
            return Result.fail("非法参数！");
        }
        ArrayList<Long> ids = new ArrayList<>();
        List<MCNOrganizationInfo> fansRank = null;
        try {
            fansRank = wccCelebrityInfoService.findOrganizationByFansRank(page.getCurrent(),page.getSize());
            for (int i = 0; i < fansRank.size(); i++) {
                ids.add(fansRank.get(i).getOrganizationId());
            }
            List<WccOrganizationDetail> wccOrganizationDetails = wccOrganizationDetailService.listByIds(ids);
            for (int i = 0; i < wccOrganizationDetails.size(); i++) {
                fansRank.get(i).setOName(wccOrganizationDetails.get(i).getOName());
                fansRank.get(i).setAddress(wccOrganizationDetails.get(i).getAddress());
                fansRank.get(i).setIcon(wccOrganizationDetails.get(i).getIcon());
                fansRank.get(i).setField(wccOrganizationDetails.get(i).getField());
            }
        }catch (Exception e){
            return Result.fail("查询机构排名异常");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("fansRank",fansRank);
        return Result.data(map);
    }


    /**
    *@Description:根据粉丝量查找红人信息
    *@Parameter:Page分页条件
    *@Return:Result<?>
    *@Author:yzd
    *@Date:2021/1/15
    **/
    @UserAuth
    @Log(value = "根据粉丝量查找红人信息", exception = "根据粉丝量查找红人信息异常")
    @PostMapping(value = {"/findCelebrityRankByFans"})
    @ApiOperation(value = "根据粉丝量查找红人信息", notes = "根据粉丝量查找红人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form"),
            @ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form"),
    })
    public Result<?> findCelebrityByFansRank(@RequestBody Page page){
        if(page.getCurrent()<1||page.getSize()<1){
            return Result.fail("非法参数！");
        }
        LambdaQueryWrapper<WccCelebrityInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WccCelebrityInfo::getStatus,1);
        wrapper.eq(WccCelebrityInfo::getIsDelete,0);
        wrapper.orderByDesc(WccCelebrityInfo::getFans);
        Page celebrityInfoPage;
        try {
            celebrityInfoPage = wccCelebrityInfoService.page(page, wrapper);
        }catch (Exception e){
            return Result.fail("查询红人排名异常");
        }
        HashMap<String, Object> map = new HashMap<>();
        List records = celebrityInfoPage.getRecords();
        map.put("celebrityInfoList",records);
        return Result.data(map);
    }

    /**
     *@Description:根据平台和粉丝量查找红人信息
     *@Parameter:PlatformPageEntity分页条件
     *@Return:Result<?>
     *@Author:yzd
     *@Date:2021/1/15
     **/
    @UserAuth
    @Log(value = "根据平台和粉丝量查找红人信息", exception = "根据平台和粉丝量查找红人信息异常")
    @PostMapping(value = {"/findCelebrityRankByPlatform"})
    @ApiOperation(value = "根据平台和粉丝量查找红人信息", notes = "根据平台和粉丝量查找红人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "platform", required = true, value = "平台", paramType = "form"),
            @ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form"),
            @ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form"),
    })
    public Result<?> findCelebrityByPlatform(@RequestBody Map<String, Object> search){
        if(Long.parseLong(search.get("current").toString())<1||Long.parseLong(search.get("size").toString())<1||search.get("platform").toString()==null||"".equals(search.get("platform").toString())||"null".equals(search.get("platform").toString())){
            return Result.fail("非法参数！");
        }
        Page page = new Page<>(Long.parseLong(search.get("current").toString()), Long.parseLong(search.get("size").toString()));
        LambdaQueryWrapper<WccCelebrityInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WccCelebrityInfo::getStatus,1);
        wrapper.eq(WccCelebrityInfo::getIsDelete,0);
        wrapper.eq(StringUtil.isNotBlank(search.get("platform").toString()),WccCelebrityInfo::getPlatform,search.get("platform").toString());
        wrapper.orderByDesc(WccCelebrityInfo::getFans);
        Page celebrityInfoPage;
        try {
            celebrityInfoPage = wccCelebrityInfoService.page(page, wrapper);
        }catch (Exception e){
            return Result.fail("查询红人排名异常");
        }
        HashMap<String, Object> map = new HashMap<>();
        List records = celebrityInfoPage.getRecords();
        map.put("celebrityInfoList",records);
        return Result.data(map);
    }


    /**
    *@Description:根据短视频报价查找红人信息
    *@Parameter:Page分页条件
    *@Return:Result<?>
    *@Author:yzd
    *@Date:2021/1/15
    **/
    @UserAuth
    @Log(value = "根据短视频报价查找红人", exception = "根据短视频报价查找红人异常")
    @PostMapping(value = {"/findCelebrityByShortVideoPrice"})
    @ApiOperation(value = "根据短视频报价查找红人", notes = "根据短视频报价查找红人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form"),
            @ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form"),
    })
    public Result<?> findCelebrityByShortVideoPriceDesc(@RequestBody Page page){
        if(page.getCurrent()<1||page.getSize()<1){
            return Result.fail("非法参数！");
        }
        LambdaQueryWrapper<WccCelebrityInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WccCelebrityInfo::getStatus,1);
        wrapper.eq(WccCelebrityInfo::getIsDelete,0);
        wrapper.orderByDesc(WccCelebrityInfo::getVideo);
        Page celebrityInfoPage;
        try {
            celebrityInfoPage = wccCelebrityInfoService.page(page, wrapper);
        }catch (Exception e){
            return Result.fail("查询红人排名异常");
        }
        HashMap<String, Object> map = new HashMap<>();
        List records = celebrityInfoPage.getRecords();
        map.put("celebrityInfoList",records);
        return Result.data(map);
    }

    /**
     *@Description:根据直播报价查找红人信息
     *@Parameter:Page分页条件
     *@Return:Result<?>
     *@Author:yzd
     *@Date:2021/1/15
     **/
    @UserAuth
    @Log(value = "根据直播报价查找红人", exception = "根据直播报价查找红人异常")
    @PostMapping(value = {"/findCelebrityByLivePrice"})
    @ApiOperation(value = "根据直播报价查找红人", notes = "根据直播报价查找红人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form"),
            @ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form"),
    })
    public Result<?> findCelebrityByLivePriceDesc(@RequestBody Page page){
        if(page.getCurrent()<1||page.getSize()<1){
            return Result.fail("非法参数！");
        }
        LambdaQueryWrapper<WccCelebrityInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WccCelebrityInfo::getStatus,1);
        wrapper.eq(WccCelebrityInfo::getIsDelete,0);
        wrapper.orderByDesc(WccCelebrityInfo::getLivePrice);
        Page celebrityInfoPage;
        try {
            celebrityInfoPage = wccCelebrityInfoService.page(page, wrapper);
        }catch (Exception e){
            return Result.fail("查询红人排名异常");
        }
        HashMap<String, Object> map = new HashMap<>();
        List records = celebrityInfoPage.getRecords();
        map.put("celebrityInfoList",records);
        return Result.data(map);
    }


    /**
    *@Description:根据带货领域查找红人信息
    *@Parameter:ScopePageEntity
    *@Return:Result<?>
    *@Author:yzd
    *@Date:2021/1/15
    **/
    @UserAuth
    @Log(value = "根据带货领域查找红人", exception = "根据带货领域查找红人异常")
    @PostMapping(value = {"/findCelebrityByScope"})
    @ApiOperation(value = "根据带货领域查找红人", notes = "根据带货领域查找红人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "scope", required = true, value = "带货领域", paramType = "form"),
            @ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form"),
            @ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form"),
    })
    public Result<?> findCelebrityByScopeDesc(@RequestBody Map<String, Object> search){
        if(Long.parseLong(search.get("current").toString())<1||Long.parseLong(search.get("size").toString())<1||search.get("scope").toString()==null||"".equals(search.get("scope").toString())||"null".equals(search.get("scope").toString())){
            return Result.fail("非法参数！");
        }
        Page page = new Page<>(Long.parseLong(search.get("current").toString()), Long.parseLong(search.get("size").toString()));
        LambdaQueryWrapper<WccCelebrityInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WccCelebrityInfo::getStatus,1);
        wrapper.eq(WccCelebrityInfo::getIsDelete,0);
        wrapper.eq(!ObjectUtils.isEmpty(search.get("scope").toString()),WccCelebrityInfo::getScope,search.get("scope").toString());
        wrapper.orderByDesc(WccCelebrityInfo::getFans);
        Page celebrityInfoPage;
        try {
            celebrityInfoPage = wccCelebrityInfoService.page(page, wrapper);
        }catch (Exception e){
            return Result.fail("查询红人排名异常");
        }
        HashMap<String, Object> map = new HashMap<>();
        List records = celebrityInfoPage.getRecords();
        map.put("celebrityInfoList",records);
        return Result.data(map);
    }
}
