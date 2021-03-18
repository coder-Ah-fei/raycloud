package hqsc.ray.wcc.controller.indexPage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hqsc.ray.core.auth.annotation.PreAuth;
import hqsc.ray.core.auth.annotation.UserAuth;
import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.common.entity.LoginUser;
import hqsc.ray.core.common.util.SecurityUtil;
import hqsc.ray.core.common.util.StringUtil;
import hqsc.ray.core.log.annotation.Log;
import hqsc.ray.core.web.controller.BaseController;
import hqsc.ray.wcc.entity.WccCircleInfo;
import hqsc.ray.wcc.entity.WccResponseDetails;
import hqsc.ray.wcc.entity.WccUserCircle;
import hqsc.ray.wcc.entity.WccUserConcern;
import hqsc.ray.wcc.form.WccReleaseInfoForm;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.dto.WccUserDto;
import hqsc.ray.wcc.jpa.form.WccUserConcernForm;
import hqsc.ray.wcc.jpa.service.WccUserCircleService;
import hqsc.ray.wcc.jpa.service.WccUserConcernService;
import hqsc.ray.wcc.service.*;
import hqsc.ray.wcc.vo.IndexCircleInfoVO;
import hqsc.ray.wcc.vo.IndexReferralsVO;
import hqsc.ray.wcc.vo.IndexVideoVO;
import hqsc.ray.wcc.vo.MyReleaseInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author
 */
@Controller
@RestController
@AllArgsConstructor
@RequestMapping({"/indexPage"})
@Api(value = "首页", tags = "首页接口")
public class IndexPageController extends BaseController {
	
	private final IWccReleaseInfoService wccReleaseInfoService;
	
	private final IWccUserCircleService iWccUserCircleService;
	
	private final IWccCircleInfoService wccCircleInfoService;
	
	
	private final IWccUserConcernService iWccUserConcernService;
	
	private final IWccUserService iWccUserService;
	private final WccUserConcernService wccUserConcernService;
	private final WccUserCircleService wccUserCircleService;
	
	private final IWccResponseDetailsService wccResponseDetailsService;
	
	/*
	 * 首页推荐
	 * */
	@Log(value = "首页推荐", exception = "首页推荐异常")
	@PostMapping(value = {"/indexReferrals"})
	@ApiOperation(value = "首页推荐", notes = "首页推荐")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form"),
			@ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form"),
	})
	public Result<?> indexReferrals(@RequestBody Page page) {
		if (page.getCurrent() < 1 || page.getSize() < 1) {
			return Result.fail("非法参数！");
		}
		LoginUser userInfo = new LoginUser();
		try {
			userInfo = SecurityUtil.getUsername(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<IndexReferralsVO> referrals = wccReleaseInfoService.findIndexReferrals(userInfo.getUserId(), page.getCurrent(), page.getSize());
		
		for (IndexReferralsVO referral : referrals) {
			referral.setAttachmentPath(StringUtil.isBlank(referral.getAttachmentPath()) ? "" : referral.getAttachmentPath().replace("static", ""));
			referral.setNickname(referral.getNickname() == null ? "" : StringUtil.toUnicode(referral.getNickname()));
		}
		
		return Result.data(referrals);
	}
	
	/*
	 * 首页推荐
	 * */
	@Log(value = "首页视频", exception = "首页视频异常")
	@PostMapping(value = {"/indexVideo"})
	@ApiOperation(value = "首页视频", notes = "首页视频")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form"),
			@ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form"),
	})
	public Result<?> indexVideo(@RequestBody Page page) {
		if (page.getCurrent() < 1 || page.getSize() < 1) {
			return Result.fail("非法参数！");
		}
		List<IndexVideoVO> records = wccReleaseInfoService.findIndexVideo(page.getCurrent(), page.getSize());
		for (IndexVideoVO record : records) {
			record.setUrl(StringUtil.isBlank(record.getUrl()) ? "" : record.getUrl().replace("static", ""));
		}
		return Result.data(records);
	}
	
	/*
	 * 查询我加入的圈子
	 * */
	@UserAuth
	@Log(value = "查询我加入的圈子", exception = "查询我加入的圈子异常")
	@PostMapping(value = {"/MyJoinedCircle"})
	@ApiOperation(value = "查询我加入的圈子", notes = "查询我加入的圈子")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form"),
			@ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form"),
	})
	public Result<?> MyJoinedCircle(@RequestBody Page page) {
		if (page.getCurrent() < 1 || page.getSize() < 1) {
			return Result.fail("非法参数！");
		}
		LambdaQueryWrapper<WccUserCircle> wrapper = Wrappers.lambdaQuery(new WccUserCircle());
		LoginUser userInfo = SecurityUtil.getUsername(req);
		wrapper.eq(WccUserCircle::getUserId, userInfo.getUserId());
		wrapper.eq(WccUserCircle::getStatus, 1);
		wrapper.eq(WccUserCircle::getIsDelete, 0);
		List<WccUserCircle> records = iWccUserCircleService.page(page, wrapper).getRecords();
		if (records == null || records.size() == 0) {
			return Result.success("您还没有加入的圈子，请先加入圈子！");
		}
		ArrayList<Long> ids = new ArrayList<>();
		for (WccUserCircle record : records) {
			ids.add(record.getCircleId());
		}
		List<WccCircleInfo> wccCircleInfos = wccCircleInfoService.listByIds(ids);
		return Result.data(wccCircleInfos);
	}
	
	/*
	 * 查询所有圈子
	 * */
	@Log(value = "查询所有圈子", exception = "查询所有圈子异常")
	@PostMapping(value = {"/findAllCircle"})
	@ApiOperation(value = "查询所有圈子", notes = "查询所有圈子")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form"),
			@ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form"),
	})
	public Result<?> findAllCircle(@RequestBody Page page) {
		if (page.getCurrent() < 1 || page.getSize() < 1) {
			return Result.fail("非法参数！");
		}
		LoginUser userInfo = new LoginUser();
		try {
			userInfo = SecurityUtil.getUsername(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<IndexCircleInfoVO> circleInfos = wccCircleInfoService.findIndexCircles(userInfo.getUserId(), page.getCurrent(), page.getSize());
		return Result.data(circleInfos);
	}
	
	
	/*
	 * 查询我关注的用户
	 * */
	@UserAuth
	@Log(value = "查询我关注的用户", exception = "查询我关注的用户异常")
	@PostMapping(value = {"/myConcernUser"})
	@ApiOperation(value = "查询我关注的用户", notes = "查询我关注的用户")
	@ApiImplicitParams({})
	public Result<?> myConcernUser(WccUserConcernForm wccUserConcernForm) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
//		List<Long> ids = this.getMyConcern(Long.parseLong(userInfo.getUserId()));
//
//		if (ids == null || ids.size() == 0) {
//			return Result.success("您还没有关注用户哦！");
//		}
		wccUserConcernForm.setPageNow(wccUserConcernForm.getCurrent());
		wccUserConcernForm.setPageSize(wccUserConcernForm.getSize());
		wccUserConcernForm.setUserId(Long.valueOf(userInfo.getUserId()));
		PageMap<WccUserDto> page = wccUserConcernService.listWccUserConcerns(wccUserConcernForm);
		
		return Result.data(page);
		
	}
	
	
	/*
	 * 关注用户的动态
	 * */
	@UserAuth
	@Log(value = "关注用户的动态", exception = "关注用户的动态异常")
	@PostMapping(value = {"/myConcern"})
	@ApiOperation(value = "关注用户的动态", notes = "关注用户的动态")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form"),
			@ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form"),
	})
	public Result<?> myConcern(@RequestBody WccReleaseInfoForm wccReleaseInfoForm) {
		if (wccReleaseInfoForm.getCurrent() < 1 || wccReleaseInfoForm.getSize() < 1) {
			return Result.fail("非法参数！");
		}
		LoginUser userInfo = SecurityUtil.getUsername(req);
		
		wccReleaseInfoForm.setUserId(Long.valueOf(userInfo.getUserId()));
		List<IndexReferralsVO> list = wccReleaseInfoService.listMyConcernReleaseInfos(wccReleaseInfoForm, wccReleaseInfoForm.getCurrent(), wccReleaseInfoForm.getSize());
		for (IndexReferralsVO indexReferralsVO : list) {
			indexReferralsVO.setAttachmentPath(StringUtil.isBlank(indexReferralsVO.getAttachmentPath()) ? "" : indexReferralsVO.getAttachmentPath().replace("static", ""));
			indexReferralsVO.setNickname(indexReferralsVO.getNickname() == null ? "" : StringUtil.toUnicode(indexReferralsVO.getNickname()));
		}
		
		return Result.data(list);
	}
	
	/*
	 * 查看话题评论
	 * */
	@UserAuth
	@Log(value = "查看话题评论", exception = "查看话题评论异常")
	@PostMapping(value = {"/getTopicDetails"})
	@ApiOperation(value = "查看话题评论", notes = "查看话题评论")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", required = true, value = "话题id", paramType = "form"),
			@ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form"),
			@ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form"),
	})
	public Result<?> getTopicDetails(@RequestParam String id, Page page) {
		if (page.getCurrent() < 1 || page.getSize() < 1) {
			return Result.fail("非法参数！");
		}
		MyReleaseInfoVO myReleaseInfoVO = wccReleaseInfoService.findById(id);
//		WccReleaseInfo releaseInfo = wccReleaseInfoService.getById(id);
		if (myReleaseInfoVO == null) {
			return Result.success("未查询到该条信息！");
		}
		LambdaQueryWrapper<WccResponseDetails> wrapper = Wrappers.lambdaQuery(new WccResponseDetails());
		wrapper.eq(WccResponseDetails::getBelongId, id);
		wrapper.eq(WccResponseDetails::getStatus, 1);
		wrapper.eq(WccResponseDetails::getIsDelete, 0);
		wrapper.orderByDesc(WccResponseDetails::getCreationDate);
		List records = wccResponseDetailsService.page(page, wrapper).getRecords();
		HashMap<String, Object> map = new HashMap<>();
		map.put("releaseInfo", myReleaseInfoVO);
		map.put("comment", records);
		return Result.data(map);
	}
	
	/*
	 * 发表评论
	 * */
	@UserAuth
	@Log(value = "发表评论", exception = "发表评论异常")
	@PostMapping(value = {"/comment"})
	@ApiOperation(value = "发表评论", notes = "发表评论")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "resBody", required = true, value = "回复内容", paramType = "form"),
			@ApiImplicitParam(name = "belongType", required = true, value = "所属类型(0发布信息1回复)", paramType = "form"),
			@ApiImplicitParam(name = "belongId", required = true, value = "所属发布信息id", paramType = "form"),
			@ApiImplicitParam(name = "resBody", required = false, value = "上级回复", paramType = "form"),
	})
	public Result<?> comment(@RequestParam(required = true, value = "resBody") String resBody,
	                         @RequestParam(required = true, value = "belongType") Integer belongType,
	                         @RequestParam(required = true, value = "belongId") Long belongId,
	                         @RequestParam(required = false, value = "parentId") Long parentId) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		WccResponseDetails comment = new WccResponseDetails();
		comment.setUserId(Long.parseLong(userInfo.getUserId()));
		comment.setResponseTime(LocalDateTime.now());
		comment.setResponseBody(resBody);
		comment.setBelongId(belongId);
		comment.setBelongType(belongType);
		comment.setParentId(parentId);
		comment.setCreatedBy(userInfo.getUserName());
		comment.setCreatedByUser(userInfo.getUserId());
		comment.setCreationDate(LocalDateTime.now());
		comment.setLastUpdatedBy(userInfo.getUserName());
		comment.setLastUpdatedByUser(userInfo.getUserId());
		boolean save = wccResponseDetailsService.save(comment);
		return Result.condition(save);
		
	}
	
	/*
	 * 查询我关注的用户的ids
	 * */
	private List<Long> getMyConcern(Long userId) {
		LambdaQueryWrapper<WccUserConcern> wrapper = Wrappers.lambdaQuery(new WccUserConcern());
		wrapper.eq(WccUserConcern::getUserId, userId);
		wrapper.eq(WccUserConcern::getStatus, 1);
		wrapper.eq(WccUserConcern::getIsDelete, 0);
		wrapper.orderByDesc(WccUserConcern::getAccessCount);
		List<WccUserConcern> userConcerns = iWccUserConcernService.list(wrapper);
		if (userConcerns == null || userConcerns.size() == 0) {
			return null;
		}
		ArrayList<Long> ids = new ArrayList<>();
		for (WccUserConcern wccUserConcern : userConcerns) {
			ids.add(wccUserConcern.getBelongId());
		}
		return ids;
	}
	
	
	/**
	 * 关注或者取消关注
	 */
	@UserAuth
	@Log(value = "关注或者取消关注", exception = "关注或者取消关注异常")
	@PostMapping(value = {"/concern"})
	@ApiOperation(value = "关注或者取消关注", notes = "关注或者取消关注")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", required = true, value = "关注或者取消关注的用户的id", paramType = "form"),
	})
	public Result<?> concern(@RequestParam(required = false, value = "userId") Long userId) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		
		WccUserConcern wccUserConcern = iWccUserConcernService.selectOne(Long.parseLong(userInfo.getUserId()), userId);
		if (wccUserConcern != null) {
			wccUserConcern.setStatus(wccUserConcern.getStatus() == 1 ? 0 : 1);
			iWccUserConcernService.updateById(wccUserConcern);
		} else {
			wccUserConcern = new WccUserConcern();
			wccUserConcern.setUserId(Long.parseLong(userInfo.getUserId()));
			wccUserConcern.setBelongId(userId);
			wccUserConcern.setAccessCount(0);
			wccUserConcern.setStatus(1);
			wccUserConcern.setIsDelete(0);
			iWccUserConcernService.save(wccUserConcern);
		}
		
		return Result.condition(true);
	}
	
	@PreAuth
	@UserAuth
	@Log(value = "加入圈子", exception = "加入圈子异常")
	@PostMapping(value = {"/joinCircle"})
	@ApiOperation(value = "加入圈子", notes = "加入圈子")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "circleId", required = true, value = "要加入的圈子的id", paramType = "form"),
	})
	public Result<?> joinCircle(@RequestParam(required = false, value = "circleId") Long circleId) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		
		
		LambdaQueryWrapper<WccUserCircle> wrapper = Wrappers.lambdaQuery(new WccUserCircle());
		wrapper.eq(WccUserCircle::getUserId, Long.valueOf(userInfo.getUserId()));
		wrapper.eq(WccUserCircle::getCircleId, circleId);
		wrapper.eq(WccUserCircle::getIsDelete, 0);
		WccUserCircle wccUserCircle = iWccUserCircleService.getOne(wrapper);
		
		if (wccUserCircle != null && wccUserCircle.getStatus() == 1) {
			return Result.fail(500, "您已经加入该圈子");
		}
		if (wccUserCircle == null) {
			wccUserCircle = new WccUserCircle();
			wccUserCircle.setUserId(Long.valueOf(userInfo.getUserId()));
			wccUserCircle.setCircleId(circleId);
			wccUserCircle.setStatus(1);
			wccUserCircle.setIsDelete(0);
			iWccUserCircleService.save(wccUserCircle);
			return Result.condition(true);
		}
		
		if (wccUserCircle != null) {
			wccUserCircle.setStatus(1);
			wccUserCircle.setIsDelete(0);
			iWccUserCircleService.updateById(wccUserCircle);
			return Result.condition(true);
		}
		return Result.fail(500, "服务器异常");
	}
	
	@UserAuth
	@Log(value = "取消加入圈子", exception = "取消加入圈子异常")
	@PostMapping(value = {"/cancelJoinCircle"})
	@ApiOperation(value = "加入圈子", notes = "加入圈子")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "circleId", required = true, value = "要加入的圈子的id", paramType = "form"),
	})
	public Result<?> cancelJoinCircle(@RequestParam(required = false, value = "circleId") Long circleId) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		
		Result result = wccUserCircleService.cancelJoinCircle(Long.valueOf(userInfo.getUserId()), circleId);
//		LambdaQueryWrapper<WccUserCircle> wrapper = Wrappers.lambdaQuery(new WccUserCircle());
//		wrapper.eq(WccUserCircle::getUserId, Long.valueOf(userInfo.getUserId()));
//		wrapper.eq(WccUserCircle::getCircleId, circleId);
//		wrapper.eq(WccUserCircle::getIsDelete, 0);
//		WccUserCircle wccUserCircle = wccUserCircleService.getOne(wrapper);
//
//		if (wccUserCircle != null && wccUserCircle.getStatus() == 1) {
//			return Result.fail(500, "您已经加入该圈子");
//		}
//		if (wccUserCircle == null) {
//			wccUserCircle = new WccUserCircle();
//			wccUserCircle.setUserId(Long.valueOf(userInfo.getUserId()));
//			wccUserCircle.setCircleId(circleId);
//			wccUserCircle.setStatus(1);
//			wccUserCircle.setIsDelete(0);
//			wccUserCircleService.save(wccUserCircle);
//			return Result.condition(true);
//		}
//
//		if (wccUserCircle != null) {
//			wccUserCircle.setStatus(1);
//			wccUserCircle.setIsDelete(0);
//			wccUserCircleService.updateById(wccUserCircle);
//			return Result.condition(true);
//		}
		return result;
	}
	
}
