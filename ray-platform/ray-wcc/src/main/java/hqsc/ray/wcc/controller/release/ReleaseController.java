package hqsc.ray.wcc.controller.release;


import hqsc.ray.core.auth.annotation.UserAuth;
import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.common.entity.LoginUser;
import hqsc.ray.core.common.util.SecurityUtil;
import hqsc.ray.core.log.annotation.Log;
import hqsc.ray.core.web.controller.BaseController;
import hqsc.ray.wcc.entity.WccReleaseInfo;
import hqsc.ray.wcc.service.IWccReleaseInfoService;
import hqsc.ray.wcc.utils.WechatMiniUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@AllArgsConstructor
@RequestMapping({"/release"})
@Api(value = "发布信息", tags = "发布信息接口")
public class ReleaseController extends BaseController {
	
	private final IWccReleaseInfoService wccReleaseInfoService;
	private final WechatMiniUtil wechatMiniUtil;
	
	/*
	 * 发布提问
	 * */
	@UserAuth
	@Log(value = "发布提问", exception = "发布提问异常")
	@PostMapping(value = {"/releaseQuestion"})
	@ApiOperation(value = "发布提问", notes = "发布提问")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "titel", required = true, value = "标题", paramType = "form"),
			@ApiImplicitParam(name = "content", required = false, value = "内容", paramType = "form"),
			@ApiImplicitParam(name = "attachmentid", required = false, value = "文件id", paramType = "form"),
			@ApiImplicitParam(name = "belongCircleId", required = false, value = "圈子id", paramType = "form"),
	})
	public Result<?> releaseQuestion(@RequestParam(required = true, value = "titel") String titel,
	                                 @RequestParam(required = false, defaultValue = "", value = "content") String content,
	                                 @RequestParam(required = false, value = "attachmentid") Long attachmentid,
	                                 @RequestParam(required = false, value = "belongCircleId") Long belongCircleId) {
		if ("".equals(titel) || titel == null || "null".equals(titel)) {
			return Result.fail("标题不能为空！");
		}
		return releaseInfo(titel, content, attachmentid, belongCircleId, 0L);
	}
	
	/*
	 * 发布话题
	 * */
	@UserAuth
	@Log(value = "发布话题", exception = "发布话题异常")
	@PostMapping(value = {"/releaseTopic"})
	@ApiOperation(value = "发布话题", notes = "发布话题")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "titel", required = true, value = "标题", paramType = "form"),
			@ApiImplicitParam(name = "content", required = false, value = "内容", paramType = "form"),
			@ApiImplicitParam(name = "attachmentid", required = false, value = "文件id", paramType = "form"),
			@ApiImplicitParam(name = "belongCircleId", required = false, value = "圈子id", paramType = "form"),
	})
	public Result<?> releaseTopic(@RequestParam(required = true, value = "titel") String titel,
	                              @RequestParam(required = false, defaultValue = "", value = "content") String content,
	                              @RequestParam(required = false, value = "attachmentid") Long attachmentid,
	                              @RequestParam(required = false, value = "belongCircleId") Long belongCircleId) {
		if ("".equals(titel) || titel == null || "null".equals(titel)) {
			return Result.fail("标题不能为空！");
		}
		return releaseInfo(titel, content, attachmentid, belongCircleId, 1L);
	}
	
	/*
	 * 发布文章
	 * */
	@UserAuth
	@Log(value = "发布文章", exception = "发布文章异常")
	@PostMapping(value = {"/releaseArticle"})
	@ApiOperation(value = "发布文章", notes = "发布文章")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "titel", required = true, value = "标题", paramType = "form"),
			@ApiImplicitParam(name = "content", required = true, value = "内容", paramType = "form"),
			@ApiImplicitParam(name = "attachmentid", required = false, value = "文件id", paramType = "form"),
			@ApiImplicitParam(name = "belongCircleId", required = false, value = "圈子id", paramType = "form"),
	})
	public Result<?> releaseArticle(@RequestParam(required = true, value = "titel") String titel,
	                                @RequestParam(required = true, defaultValue = "", value = "content") String content,
	                                @RequestParam(required = false, value = "attachmentid") Long attachmentid,
	                                @RequestParam(required = false, value = "belongCircleId") Long belongCircleId) {
		if ("".equals(titel) || titel == null || "null".equals(titel)) {
			return Result.fail("标题不能为空！");
		}
		if (belongCircleId <= 0) {
			belongCircleId = null;
		}
		return releaseInfo(titel, content, attachmentid, belongCircleId, 2L);
		
	}
	
	/*
	 * 发布视频
	 * */
	@UserAuth
	@Log(value = "发布视频", exception = "发布视频异常")
	@PostMapping(value = {"/releaseVideo"})
	@ApiOperation(value = "发布视频", notes = "发布视频")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "titel", required = true, value = "标题", paramType = "form"),
			@ApiImplicitParam(name = "content", required = false, value = "内容", paramType = "form"),
			@ApiImplicitParam(name = "attachmentid", required = true, value = "文件id", paramType = "form"),
			@ApiImplicitParam(name = "belongCircleId", required = false, value = "圈子id", paramType = "form"),
	})
	public Result<?> releaseVideo(@RequestParam(required = true, value = "titel") String titel,
	                              @RequestParam(required = false, defaultValue = "", value = "content") String content,
	                              @RequestParam(required = true, value = "attachmentid") Long attachmentid,
	                              @RequestParam(required = false, value = "belongCircleId") Long belongCircleId) {
		if ("".equals(titel) || titel == null || "null".equals(titel)) {
			return Result.fail("标题不能为空！");
		}
		return releaseInfo(titel, content, attachmentid, belongCircleId, 3L);
	}
	
	
	private Result<?> releaseInfo(String titel, String content, Long attachmentid, Long belongCircleId, Long type) {
		boolean msgSecCheck = wechatMiniUtil.msgSecCheck(titel + content);
		if (!msgSecCheck) {
			return Result.fail("您发布的标题或内容中可能包含敏感信息");
		}
		// 从content中提取连接
		List<String> imgStr = getImgStr(content);
		for (String s : imgStr) {
			boolean imgSecCheck = wechatMiniUtil.imgSecCheck(s);
			if (!imgSecCheck) {
				return Result.fail("您发布的标题或内容中可能包含敏感信息");
			}
		}
		LoginUser userInfo = SecurityUtil.getUsername(req);
		Long userid = Long.parseLong(userInfo.getUserId());
		WccReleaseInfo releaseInfo = new WccReleaseInfo();
		releaseInfo.setTitel(titel);
		releaseInfo.setContent(content);
		releaseInfo.setAttachmentId(attachmentid);
		releaseInfo.setType(type);
		if (type == 3) {
			releaseInfo.setApproveStatus(0);
		} else {
			releaseInfo.setApproveStatus(1);
			
		}
		releaseInfo.setBelongUserId(userid);
		releaseInfo.setBelongCircleId(belongCircleId);
		releaseInfo.setCreationDate(LocalDateTime.now());
		try {
			return wccReleaseInfoService.save(releaseInfo) ? Result.success("发布信息成功！") : Result.fail("发布信息失败！");
		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail("发布信息异常！");
		}
	}
	
	
	public static List<String> getImgStr(String htmlStr) {
		Matcher m = Pattern.compile("src=\"http://.*?\"").matcher(htmlStr);
		List<String> list = new ArrayList<>();
		
		while (m.find()) {
			String match = m.group();
			//Pattern.CASE_INSENSITIVE忽略'jpg'的大小写
			Matcher k = Pattern.compile("\"http://.*?\"", Pattern.CASE_INSENSITIVE).matcher(match);
			if (k.find()) {
				String group = k.group();
				list.add(group.substring(1, group.length() - 1));
			}
		}
		return list;
	}
}
