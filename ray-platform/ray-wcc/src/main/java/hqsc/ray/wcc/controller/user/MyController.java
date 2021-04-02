package hqsc.ray.wcc.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.binarywang.java.emoji.EmojiConverter;
import hqsc.ray.core.auth.annotation.UserAuth;
import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.common.entity.LoginUser;
import hqsc.ray.core.common.util.SecurityUtil;
import hqsc.ray.core.common.util.StringUtil;
import hqsc.ray.core.log.annotation.Log;
import hqsc.ray.core.redis.core.RedisService;
import hqsc.ray.core.web.controller.BaseController;
import hqsc.ray.wcc.entity.*;
import hqsc.ray.wcc.form.WccReleaseInfoForm;
import hqsc.ray.wcc.form.WccResponseDetailsForm;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.service.WccReleaseInfoService;
import hqsc.ray.wcc.jpa.service.WccUserService;
import hqsc.ray.wcc.service.*;
import hqsc.ray.wcc.vo.MyReleaseInfoVO;
import hqsc.ray.wcc.vo.WccResponseDetailsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping({"/user"})
@Api(value = "我的", tags = "我的接口")
public class MyController extends BaseController {
	
	private IWccUserService iWccUserService;
	
	private IWccReleaseInfoService iWccReleaseInfoService;
	
	private IWccResponseDetailsService wccResponseDetailsService;
	
	private IWccPraiseFavoriteService wccPraiseFavoriteService;
	
	private IWccUserConcernService wccUserConcernService;
	
	private IWccUserCircleService wccUserCircleService;
	
	private final WccReleaseInfoService releaseInfoService;
	private final RedisService redisService;
	private final WccUserService wccUserService;
	
	/*
	 * 获取用户信息
	 * */
	@UserAuth
	@Log(value = "获取用户信息", exception = "获取用户信息异常")
	@PostMapping(value = {"/getMyUserInfo"})
	@ApiOperation(value = "获取用户信息", notes = "获取用户信息")
	@ApiImplicitParams({})
	public Result<?> getUserInfo() {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		HashMap<String, Object> map = new HashMap<>();
		WccUser wccUser;
		try {
			wccUser = iWccUserService.getById(userInfo.getUserId());
			EmojiConverter emojiConverter = EmojiConverter.getInstance();
			wccUser.setNickname(emojiConverter.toUnicode(wccUser.getNickname()));
			wccUser.setPhone(StringUtil.hideString(wccUser.getPhone()));
		} catch (Exception e) {
			return Result.fail("查询用户信息异常");
		}
		
		// 查询用户的关注数，
		LambdaQueryWrapper<WccUserConcern> wccUserConcernWrapper = Wrappers.lambdaQuery(new WccUserConcern());
		wccUserConcernWrapper.eq(WccUserConcern::getUserId, Long.parseLong(userInfo.getUserId()));
		int attentionCount = wccUserConcernService.count(wccUserConcernWrapper);
		
		// 查询用户的粉丝数
		wccUserConcernWrapper = Wrappers.lambdaQuery(new WccUserConcern());
		wccUserConcernWrapper.eq(WccUserConcern::getBelongId, Long.parseLong(userInfo.getUserId()));
		int fansCount = wccUserConcernService.count(wccUserConcernWrapper);
		
		// 查询用户的获赞数
		Long praisedCount = wccPraiseFavoriteService.praisedCountForUser(Long.parseLong(userInfo.getUserId()));
		
		map.put("wccUser", wccUser);
		map.put("attentionCount", attentionCount);
		map.put("fansCount", fansCount);
		map.put("praisedCount", praisedCount);
		
		return Result.data(map);
	}
	
	/*
	 * 编辑资料
	 * */
	@UserAuth
	@Log(value = "编辑资料", exception = "编辑资料异常")
	@PostMapping(value = {"/editInfo"})
	@ApiOperation(value = "编辑资料", notes = "编辑资料")
	@ApiImplicitParams({})
	public Result<?> editInfo(@RequestParam(required = false, value = "headimg") Long headimg,
	                          @RequestParam(required = false, value = "nickname") String nickname,
	                          @RequestParam(required = false, value = "userintroduce") String userintroduce,
	                          @RequestParam(required = false, value = "gender") Integer gender,
	                          @RequestParam(required = false, value = "phone") String phone) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		WccUser wccUser = iWccUserService.getById(userInfo.getUserId());
		if (headimg != null) {
			wccUser.setHeadPortrait(headimg);
		}
		if (nickname != null) {
			wccUser.setNickname(nickname);
		}
		if (userintroduce != null) {
			wccUser.setUserIntroduce(userintroduce);
		}
		if (gender != null) {
			wccUser.setGender(gender);
		}
		if (phone != null) {
			wccUser.setPhone(phone);
		}
		try {
			wccUser.setLastUpdateDate(LocalDateTime.now());
			iWccUserService.updateById(wccUser);
			return Result.success("编辑资料成功！");
		} catch (Exception e) {
			return Result.fail("编辑资料异常！");
		}
	}
	
	/**
	 * 用户提问
	 * 过时的，请改用用 WccReleaseInfoController 中的  listWccReleaseInfos
	 */
	@Deprecated
	@UserAuth
	@Log(value = "用户提问", exception = "用户提问异常")
	@PostMapping(value = {"/getUserQuestion"})
	@ApiOperation(value = "用户提问", notes = "用户提问")
	public Result<?> getUserQuestion(hqsc.ray.wcc.jpa.form.WccReleaseInfoForm wccReleaseInfoForm) {
//		HashMap<String, Object> map = new HashMap<>();
//		LoginUser userInfo = SecurityUtil.getUsername(req);
//		WccReleaseInfoForm wccReleaseInfoForm = new WccReleaseInfoForm();
//
//		wccReleaseInfoForm.setBelongUserId(Long.valueOf(userInfo.getUserId()))
//				.setType(0);
//
//		List<MyReleaseInfoVO> myQuestion = iWccReleaseInfoService.findMyReleaseInfo(wccReleaseInfoForm, page.getCurrent(), page.getSize());
//		LambdaQueryWrapper<WccReleaseInfo> wrapper = Wrappers.lambdaQuery(new WccReleaseInfo());
//		wrapper.eq(WccReleaseInfo::getBelongUserId, Long.parseLong(userInfo.getUserId()));
//		wrapper.eq(WccReleaseInfo::getStatus, 1);
//		wrapper.eq(WccReleaseInfo::getIsDelete, 0);
//		wrapper.eq(WccReleaseInfo::getType, 0);
//		int count = iWccReleaseInfoService.count(wrapper);
//		map.put("myQuestion", myQuestion);
//		map.put("count", count);
//		return Result.data(map);
		
		LoginUser userInfo = SecurityUtil.getUsername(req);
		wccReleaseInfoForm
				.setBelongUserId(Long.valueOf(userInfo.getUserId()))
				.setType(0L)
				.setStatus(1)
				.setIsDelete(0);
		ResultMap resultMap = releaseInfoService.listWccReleaseInfos(wccReleaseInfoForm);
		return Result.data(resultMap);
	}
	
	/*
	 * 用户话题
	 * */
	@UserAuth
	@Log(value = "用户话题", exception = "用户话题异常")
	@PostMapping(value = {"/getUserTopic"})
	@ApiOperation(value = "用户话题", notes = "用户话题")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form"),
			@ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form"),
	})
	public Result<?> getUserTopic(@RequestBody Page page) {
		HashMap<String, Object> map = new HashMap<>();
		LoginUser userInfo = SecurityUtil.getUsername(req);
		WccReleaseInfoForm wccReleaseInfoForm = new WccReleaseInfoForm();
		wccReleaseInfoForm.setBelongUserId(Long.valueOf(userInfo.getUserId()))
				.setType(1);
		List<MyReleaseInfoVO> myTopic = iWccReleaseInfoService.findMyReleaseInfo(wccReleaseInfoForm, page.getCurrent(), page.getSize());
		LambdaQueryWrapper<WccReleaseInfo> wrapper = Wrappers.lambdaQuery(new WccReleaseInfo());
		wrapper.eq(WccReleaseInfo::getBelongUserId, Long.parseLong(userInfo.getUserId()));
		wrapper.eq(WccReleaseInfo::getStatus, 1);
		wrapper.eq(WccReleaseInfo::getIsDelete, 0);
		wrapper.eq(WccReleaseInfo::getType, 1);
		int count = iWccReleaseInfoService.count(wrapper);
		map.put("myTopic", myTopic);
		map.put("count", count);
		return Result.data(map);

//		LoginUser userInfo = SecurityUtil.getUsername(req);
//		wccReleaseInfoForm
//				.setBelongUserId(Long.valueOf(userInfo.getUserId()))
//				.setType(3L)
//				.setStatus(1)
//				.setIsDelete(0);
//		ResultMap resultMap = releaseInfoService.listWccReleaseInfos(wccReleaseInfoForm);
//		return Result.data(resultMap);
	
	}
	
	/*
	 * 用户文章
	 * */
	@UserAuth
	@Log(value = "用户文章", exception = "用户文章异常")
	@PostMapping(value = {"/getUserArticle"})
	@ApiOperation(value = "用户文章", notes = "用户文章")
	public Result<?> getUserArticle(hqsc.ray.wcc.jpa.form.WccReleaseInfoForm wccReleaseInfoForm) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		wccReleaseInfoForm
				.setUserId(Long.valueOf(userInfo.getUserId()))
				.setType(2L)
				.setStatus(1)
				.setIsDelete(0);
		ResultMap resultMap = releaseInfoService.listWccReleaseInfos(wccReleaseInfoForm);
		return Result.data(resultMap);
	}
	
	/**
	 * 用户视频
	 */
	@Log(value = "用户视频", exception = "用户视频异常")
	@PostMapping(value = {"/getUserVideo"})
	@ApiOperation(value = "用户视频", notes = "用户视频")
	public Result<?> getUserVideo(hqsc.ray.wcc.jpa.form.WccReleaseInfoForm wccReleaseInfoForm) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		wccReleaseInfoForm
				.setBelongUserId(Long.valueOf(userInfo.getUserId()))
				.setType(3L)
				.setStatus(1)
				.setIsDelete(0);
		ResultMap resultMap = releaseInfoService.listWccReleaseInfos(wccReleaseInfoForm);
		return Result.data(resultMap);
	}
	
	/*
	 * 话题详情
	 * */
	@Log(value = "话题详情", exception = "话题详情异常")
	@PostMapping(value = {"/getTopicDetails"})
	@ApiOperation(value = "话题详情", notes = "话题详情")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", required = true, value = "标题", paramType = "form"),
			@ApiImplicitParam(name = "current", required = false, value = "当前页", paramType = "form"),
			@ApiImplicitParam(name = "size", required = false, value = "每页显示数据", paramType = "form"),
	})
	public Result<?> getTopicDetails(@RequestParam String id, Page page) {
		if (page.getCurrent() < 1 || page.getSize() < 1 || id == null || "".equals(id) || "null".equals(id)) {
			return Result.fail("非法参数！");
		}
		List<WccResponseDetailsVO> wccResponseDetailsVOList = null;
//		WccReleaseInfo releaseInfo = null;
		MyReleaseInfoVO myReleaseInfoVO = null;
		HashMap<String, Object> map = new HashMap<>();
		try {
//			releaseInfo = wccReleaseInfoService.getById(id);
			
			myReleaseInfoVO = iWccReleaseInfoService.findById(id);
//			wccResponseDetailsIPage = wccResponseDetailsService.getTopicDetails(page, Long.parseLong(id));
			
			wccResponseDetailsVOList = wccResponseDetailsService.listResponseDetails(Long.parseLong(id), 0, null);
			
			for (WccResponseDetailsVO wccResponseDetailsVO : wccResponseDetailsVOList) {
				List<WccResponseDetailsVO> childList2 = new ArrayList<>();
				arrangeWccResponseDetailsVOList(childList2, wccResponseDetailsVO);
				wccResponseDetailsVO.setChildList2(childList2);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail("查询异常！");
		}
		map.put("releaseInfo", myReleaseInfoVO);
		map.put("responseDetails", wccResponseDetailsVOList);
		return Result.data(map);
	}
	
	/**
	 * 整理List<WccResponseDetailsVO>回复数据，递归变为非递归
	 *
	 * @param childList2
	 * @param wccResponseDetailsVO
	 */
	private void arrangeWccResponseDetailsVOList(List<WccResponseDetailsVO> childList2, WccResponseDetailsVO wccResponseDetailsVO) {
		if (wccResponseDetailsVO.getChildList() == null) {
			return;
		}
		for (WccResponseDetailsVO detailsVO : wccResponseDetailsVO.getChildList()) {
			detailsVO.setRepliedUserNickname(wccResponseDetailsVO.getUserNickname() == null ? "" : StringUtil.toUnicode(wccResponseDetailsVO.getUserNickname()));
			if (detailsVO.getChildList() != null && detailsVO.getChildList().size() > 0) {
				arrangeWccResponseDetailsVOList(childList2, detailsVO);
			}
			childList2.add(detailsVO);
		}
	}
	
	/*
	 *我的收藏
	 * */
	@UserAuth
	@Log(value = "我的收藏", exception = "我的收藏异常")
	@PostMapping(value = {"/getMyFavorite"})
	@ApiOperation(value = "我的收藏", notes = "我的收藏")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form"),
			@ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form"),
	})
	public Result<?> getMyFavorite(WccReleaseInfoForm wccReleaseInfoForm, Page page) {
		if (page.getCurrent() < 1 || page.getSize() < 1) {
			return Result.fail("非法参数！");
		}
		LoginUser userInfo = SecurityUtil.getUsername(req);
		wccReleaseInfoForm.setFavoritesUserId(Long.valueOf(userInfo.getUserId()));
		List<MyReleaseInfoVO> myFavorite = null;
		try {
			myFavorite = iWccReleaseInfoService.getMyFavorite(wccReleaseInfoForm, page.getCurrent(), page.getSize());
		} catch (Exception e) {
			return Result.fail("查询异常！");
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("myFavorite", myFavorite);
		return Result.data(map);
	}
	
	/*
	 * 发布信息的回答信息
	 * */
	@UserAuth
	@Log(value = "发布信息详情", exception = "发布信息详情异常")
	@PostMapping(value = {"/getResponseInfo"})
	@ApiOperation(value = "发布信息详情", notes = "发布信息详情")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", required = true, value = "标题", paramType = "form"),
			@ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form"),
			@ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form"),
	})
	public Result<?> getResponseInfo(@RequestParam String id, Page page) {
		if (page.getCurrent() < 1 || page.getSize() < 1 || id == null || "".equals(id) || "null".equals(id)) {
			return Result.fail("非法参数！");
		}
		List<WccResponseDetails> wccResponseDetailsIPage = null;
		HashMap<String, Object> map = new HashMap<>();
		try {
			wccResponseDetailsIPage = wccResponseDetailsService.getTopicDetails(page, Long.parseLong(id));
		} catch (Exception e) {
			return Result.fail("查询异常！");
		}
		map.put("responseDetails", wccResponseDetailsIPage);
		return Result.data(map);
	}
	
	/**
	 * 我的回答
	 */
	@UserAuth
	@Log(value = "我的回答", exception = "我的回答异常")
	@PostMapping(value = {"/getMyResponse"})
	@ApiOperation(value = "我的回答", notes = "我的回答")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form"),
			@ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form"),
			@ApiImplicitParam(name = "type", required = true, value = "发布信息的类型", paramType = "form"),
	})
	public Result<?> getMyResponse(@RequestParam Integer type, Page page) {
		if (page.getCurrent() < 1 || page.getSize() < 1) {
			return Result.fail("非法参数！");
		}
		LoginUser userInfo = SecurityUtil.getUsername(req);
		HashMap<String, Object> map = new HashMap<>();
//		List<WccResponseDetails> myResponse;
//		try {
//			myResponse = wccResponseDetailsService.getMyResponse(page, Long.parseLong(userInfo.getUserId()));
//		} catch (Exception e) {
//			return Result.fail("查询异常");
//		}
//		map.put("myResponse", myResponse);
		
		List<MyReleaseInfoVO> myReleaseInfoVOList;
		try {
			myReleaseInfoVOList = iWccReleaseInfoService.listWccReleaseInfosForNewestComment(page.getCurrent(), page.getSize(), Long.parseLong(userInfo.getUserId()), type);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail("查询异常");
		}
		map.put("myReleaseInfoVOList", myReleaseInfoVOList);
		
		return Result.data(map);
	}
	
	/**
	 * @Description:用户收藏功能
	 * @Parameter:
	 * @Return:Result<?>
	 * @Author:yzd
	 * @Date:2021/1/16
	 **/
//	@UserAuth
//	@Log(value = "用户收藏", exception = "用户收藏异常")
//	@PostMapping(value = {"/favorite"})
//	@ApiOperation(value = "用户收藏", notes = "用户收藏")
//	@ApiImplicitParams({
//			@ApiImplicitParam(name = "releaseInfoId", required = true, value = "发布信息id", paramType = "form"),
//	})
//	public Result<?> favorite(@RequestParam Long releaseInfoId) {
//		LoginUser userInfo = SecurityUtil.getUsername(req);
//		WccPraiseFavorite wccPraiseFavorite = new WccPraiseFavorite();
//		wccPraiseFavorite.setUserId(Long.parseLong(userInfo.getUserId()));
//		wccPraiseFavorite.setBelongId(releaseInfoId);
//		wccPraiseFavorite.setType(1);
//		wccPraiseFavorite.setCreationDate(LocalDateTime.now());
//		boolean save;
//		try {
//			save = wccPraiseFavoriteService.save(wccPraiseFavorite);
//			return save ? Result.success("收藏成功！") : Result.fail("收藏失败！");
//		} catch (Exception e) {
//			return Result.success("收藏异常！");
//		}
//	}
	
	/**
	 * @Description:用户取消收藏
	 * @Parameter:userid:用户ID，releaseInfo所点赞信息id
	 * @Return:Result<?>
	 * @Author:yzd
	 * @Date:2021/1/16
	 **/
	@UserAuth
	@Log(value = "用户取消收藏", exception = "用户取消收藏异常")
	@PostMapping(value = {"/cancelFavorite"})
	@ApiOperation(value = "用户取消收藏", notes = "用户取消收藏")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "releaseInfo", required = true, value = "发布信息id", paramType = "form"),
	})
	public Result<?> cancelFavorite(@RequestParam Long releaseInfo) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		LambdaQueryWrapper<WccPraiseFavorite> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(WccPraiseFavorite::getUserId, Long.parseLong(userInfo.getUserId()));
		wrapper.eq(WccPraiseFavorite::getBelongId, releaseInfo);
		wrapper.eq(WccPraiseFavorite::getStatus, 1);
		wrapper.eq(WccPraiseFavorite::getIsDelete, 0);
		boolean save;
		try {
			WccPraiseFavorite favorite = wccPraiseFavoriteService.getOne(wrapper);
			favorite.setStatus(0);
			save = wccPraiseFavoriteService.updateById(favorite);
		} catch (Exception e) {
			return Result.success("取消收藏异常！");
		}
		return save ? Result.success("取消收藏成功！") : Result.fail("取消收藏失败！");
	}
	
	/**
	 * @Description:用户关注功能
	 * @Parameter:
	 * @Return:Result<?>
	 * @Author:yzd
	 * @Date:2021/2/20
	 **/
	@UserAuth
	@Log(value = "用户关注", exception = "用户关注异常")
	@PostMapping(value = {"/concern"})
	@ApiOperation(value = "用户关注", notes = "用户关注")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "belongId", required = true, value = "关注的用户id", paramType = "form"),
	})
	public Result<?> concern(@RequestParam Long belongId) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		WccUserConcern wccUserConcern = new WccUserConcern();
		wccUserConcern.setUserId(Long.parseLong(userInfo.getUserId()));
		wccUserConcern.setBelongId(belongId);
		wccUserConcern.setAccessCount(0);
		wccUserConcern.setStatus(1);
		wccUserConcern.setIsDelete(0);
		wccUserConcern.setCreationDate(LocalDateTime.now());
		wccUserConcern.setLastUpdateDate(LocalDateTime.now());
		boolean save;
		try {
			save = wccUserConcernService.save(wccUserConcern);
			return save ? Result.success("关注成功！") : Result.fail("关注失败！");
		} catch (Exception e) {
			return Result.success("关注异常！");
		}
	}
	
	/**
	 * @Description:用户取消关注
	 * @Parameter:userid:用户ID，belongId关注的用户id
	 * @Return:Result<?>
	 * @Author:yzd
	 * @Date:2021/1/16
	 **/
	@UserAuth
	@Log(value = "用户取消关注", exception = "用户取消关注异常")
	@PostMapping(value = {"/cancelConcern"})
	@ApiOperation(value = "用户取消关注", notes = "用户取消关注")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "belongId", required = true, value = "关注的用户id", paramType = "form"),
	})
	public Result<?> cancelConcern(@RequestParam Long belongId) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		LambdaQueryWrapper<WccUserConcern> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(WccUserConcern::getUserId, Long.parseLong(userInfo.getUserId()));
		wrapper.eq(WccUserConcern::getBelongId, belongId);
		wrapper.eq(WccUserConcern::getStatus, 1);
		wrapper.eq(WccUserConcern::getIsDelete, 0);
		boolean save;
		try {
			WccUserConcern wccUserConcern = wccUserConcernService.getOne(wrapper);
			wccUserConcern.setStatus(0);
			wccUserConcern.setIsDelete(1);
			wccUserConcern.setLastUpdateDate(LocalDateTime.now());
			save = wccUserConcernService.updateById(wccUserConcern);
		} catch (Exception e) {
			return Result.success("取消关注异常！");
		}
		return save ? Result.success("取消关注成功！") : Result.fail("取消关注失败！");
	}
	
	/**
	 * @Description:用户加入圈子功能
	 * @Parameter:
	 * @Return:Result<?>
	 * @Author:yzd
	 * @Date:2021/2/20
	 **/
	@UserAuth
	@Log(value = "用户加入圈子", exception = "用户加入圈子异常")
	@PostMapping(value = {"/joinCircle"})
	@ApiOperation(value = "用户加入圈子", notes = "用户加入圈子")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "circleId", required = true, value = "圈子id", paramType = "form"),
	})
	public Result<?> joinCircle(@RequestParam Long circleId) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		WccUserCircle wccUserCircle = new WccUserCircle();
		wccUserCircle.setUserId(Long.parseLong(userInfo.getUserId()));
		wccUserCircle.setCircleId(circleId);
		wccUserCircle.setStatus(1);
		wccUserCircle.setIsDelete(0);
		wccUserCircle.setCreationDate(LocalDateTime.now());
		wccUserCircle.setLastUpdateDate(LocalDateTime.now());
		boolean save;
		try {
			save = wccUserCircleService.save(wccUserCircle);
			return save ? Result.success("加入圈子成功！") : Result.fail("加入圈子失败！");
		} catch (Exception e) {
			return Result.success("加入圈子异常！");
		}
	}
	
	/**
	 * @Description:用户退出圈子
	 * @Parameter:userid:用户ID，circleId圈子id
	 * @Return:Result<?>
	 * @Author:yzd
	 * @Date:2021/1/16
	 **/
	@UserAuth
	@Log(value = "用户退出圈子", exception = "用户退出圈子异常")
	@PostMapping(value = {"/quitCircle"})
	@ApiOperation(value = "用户退出圈子", notes = "用户退出圈子")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "circleId", required = true, value = "圈子id", paramType = "form"),
	})
	public Result<?> quitCircle(@RequestParam Long circleId) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		LambdaQueryWrapper<WccUserCircle> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(WccUserCircle::getUserId, Long.parseLong(userInfo.getUserId()));
		wrapper.eq(WccUserCircle::getCircleId, circleId);
		wrapper.eq(WccUserCircle::getStatus, 1);
		wrapper.eq(WccUserCircle::getIsDelete, 0);
		boolean save;
		try {
			WccUserCircle wccUserCircle = wccUserCircleService.getOne(wrapper);
			wccUserCircle.setStatus(0);
			wccUserCircle.setIsDelete(1);
			wccUserCircle.setLastUpdateDate(LocalDateTime.now());
			save = wccUserCircleService.updateById(wccUserCircle);
		} catch (Exception e) {
			return Result.success("退出圈子异常！");
		}
		return save ? Result.success("退出圈子成功！") : Result.fail("退出圈子失败！");
	}
	
	/*
	 * 发表评论
	 * 过时的改到 ray-wcc2/wcc-response-details/saveWccResponseDetails
	 * */
	@Deprecated
	@UserAuth
	@Log(value = "发表评论", exception = "发表评论异常")
	@PostMapping(value = {"/comment"}, produces = {"application/json; charset=UTF-8"})
	@ApiOperation(value = "发表评论", notes = "发表评论")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "resBody", required = true, value = "回复内容", paramType = "form"),
//            @ApiImplicitParam(name = "belongType", required = true, value = "所属类型(0提问回复1话题回复2文章回复3视频回复)", paramType = "form"),
//            @ApiImplicitParam(name = "belongId", required = true, value = "所属发布信息id", paramType = "form"),
//            @ApiImplicitParam(name = "resBody", required = false, value = "上级回复", paramType = "form"),
//    })
	public Result<?> comment(WccResponseDetailsForm wccResponseDetailsForm) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		
		WccResponseDetails comment = new WccResponseDetails();
		BeanUtils.copyProperties(wccResponseDetailsForm, comment);
		comment.setUserId(Long.parseLong(userInfo.getUserId()));
		comment.setResponseTime(LocalDateTime.now());
		comment.setCreatedBy(userInfo.getUserName());
		comment.setCreatedByUser(userInfo.getUserId());
		comment.setCreationDate(LocalDateTime.now());
		comment.setLastUpdatedBy(userInfo.getUserName());
		comment.setLastUpdatedByUser(userInfo.getUserId());
		comment.setStatus(1);
		comment.setIsDelete(0);
		boolean save = wccResponseDetailsService.save(comment);
		return Result.condition(save);
		
	}
	
	@UserAuth
	@Log(value = "绑定手机号", exception = "绑定手机号异常")
	@PostMapping(value = {"/bindMobilePhone"}, produces = {"application/json; charset=UTF-8"})
	@ApiOperation(value = "绑定手机号", notes = "绑定手机号")
	public Result<?> bindMobilePhone(
			@RequestParam("encryptedData") String encryptedData,
			@RequestParam("iv") String iv
	) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		return wccUserService.bindMobilePhone(encryptedData, iv, userInfo.getUserId());
	}
	
	
	/*
	 * 查询用户已发布信息资料方法
	 * */
	private List<WccReleaseInfo> getUserReleaseInfo(String userid, int type, Page page) {
		LambdaQueryWrapper<WccReleaseInfo> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(WccReleaseInfo::getBelongUserId, userid);
		wrapper.eq(WccReleaseInfo::getType, type);
		wrapper.eq(WccReleaseInfo::getStatus, 1);
		wrapper.eq(WccReleaseInfo::getIsDelete, 0);
		wrapper.orderByDesc(WccReleaseInfo::getCreationDate);
		return iWccReleaseInfoService.page(page, wrapper).getRecords();
	}
	
}
