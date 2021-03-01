package hqsc.ray.component.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hqsc.ray.component.config.AttachmentConfig;
import hqsc.ray.component.entity.SysAttachment;
import hqsc.ray.component.service.ISysAttachmentService;
import hqsc.ray.core.auth.annotation.PreAuth;
import hqsc.ray.core.auth.annotation.UserAuth;
import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.common.entity.LoginUser;
import hqsc.ray.core.common.util.SecurityUtil;
import hqsc.ray.core.common.util.StringPool;
import hqsc.ray.core.common.util.StringUtil;
import hqsc.ray.core.database.entity.Search;
import hqsc.ray.core.log.annotation.Log;
import hqsc.ray.core.web.controller.BaseController;
import hqsc.ray.core.web.util.CollectionUtil;
import hqsc.ray.core.web.util.OssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 附件表 前端控制器
 * </p>
 *
 * @author pangu
 * @since 2020-08-09
 */
@RestController
@AllArgsConstructor
@RequestMapping("/attachment")
@Api(tags = "附件管理")
public class SysAttachmentController extends BaseController {
    @Autowired
    AttachmentConfig attachmentConfig;

    private final ISysAttachmentService sysAttachmentService;

    /**
     * 附件分页
     * @param page　分页参数
     * @param search　关键词
     * @return Result
     */
    @PreAuth
    @Log(value = "附件分页", exception = "附件分页请求异常")
    @GetMapping("/page")
    @ApiOperation(value = "附件分页", notes = "附件分页，根据query查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form"),
            @ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form"),
            @ApiImplicitParam(name = "keyword", required = true, value = "模糊查询关键词", paramType = "form"),
            @ApiImplicitParam(name = "startDate", required = true, value = "创建开始日期", paramType = "form"),
            @ApiImplicitParam(name = "endDate", required = true, value = "创建结束日期", paramType = "form"),
    })
    public Result<?> page(Page page, Search search) {
        return Result.data(sysAttachmentService.listPage(page, search));
    }

    /**
     * 附件上传
     * @param file　文件
     * @return Result
     */
    @UserAuth
    @Log(value = "附件上传", exception = "附件上传请求异常")
    @ApiOperation(value = "附件上传", notes = "附件上传")
//    @PostMapping("/upload")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result<?> upload(@RequestParam("file") MultipartFile file) {
        String fileName = UUID.randomUUID().toString().replace("-", "")
            + StringPool.DOT + FilenameUtils.getExtension(file.getOriginalFilename());
        LoginUser userInfo = SecurityUtil.getUsername(req);
        Map<String, Object> result = new HashMap<>(4);
        if (null != file) {
            byte[] buffer=new byte[(int)file.getSize()];
            try {
                DataInputStream in = new DataInputStream(file.getInputStream());
                in.read(buffer);
                in.close();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            String maxSize = attachmentConfig.getMaxSize();
            if(StringUtil.isNotBlank(maxSize)){
                if (file.getSize() >(Long.valueOf(maxSize) * 1024 * 1024)) {
                    return Result.fail("上传文件大小超过限制");
                }
            }
            Long size = file.getSize();
            String uploadPath = attachmentConfig.getUploadPath();
            File saveDirFile = new File(uploadPath);
            if (!saveDirFile.exists()) {
                saveDirFile.mkdirs();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String ymd = sdf.format(new Date());
            String saveUrl = "static/upload/"+ymd + "/";
            String savePath = uploadPath+saveUrl;
            File dirFile = new File(savePath);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            try {
                file.transferTo(new File(savePath+fileName));
            } catch (Exception e) {
                e.printStackTrace();
                return Result.fail(e.getMessage());
            }
            SysAttachment att = new SysAttachment();
            att.setName(fileName);
            att.setUrl(saveUrl+fileName);
            att.setPath(savePath+fileName);
            att.setFileName(fileName);
            att.setSize(size);
            att.setType(OssUtil.getFileType(fileName));
            att.setCreateBy(userInfo.getUserName());
            att.setCreateTime(LocalDateTime.now());
            att.setUpdateBy(userInfo.getUserName());
            att.setUpdateTime(LocalDateTime.now());
            sysAttachmentService.save(att);
            result.put("id", att.getId());
            result.put("filePath", att.getUrl());
        } else {
            return Result.fail("上传图片不能为空");
        }
        return Result.data(result);
    }

    @ApiOperation(value = "查看附件")
    @RequestMapping(value = "/attachment", method = RequestMethod.GET)
    public Object attachment(@RequestParam(required = false, defaultValue = "", value = "id") String id) {
        try {
            if(!StringUtil.isEmpty(id)&&!id.equals("undefined")) {
                SysAttachment attachment=sysAttachmentService.getById(id);
                if(attachment!=null){
                    String fileUrl= attachmentConfig.getUploadPath();
                    File file = new File(fileUrl+attachment.getUrl());
                    BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));
                    // 清空response
                    res.reset();
                    OutputStream toClient = new BufferedOutputStream(res.getOutputStream());
                    try {
                        String fname = attachment.getFileName();
                        // 设置response的Header
                        res.addHeader("Content-Disposition", "attachment;filename=" + new String(fname.getBytes()));
                        res.setContentType("multipart/form-data");
                        int b = 0;
                        byte[] buffer = new byte[100];
                        while (b != -1) {
                            b = bin.read(buffer);
                            if (b != -1) {
                                toClient.write(buffer, 0, b);//4.写到输出流(out)中
                            }

                        }
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }finally{
                        try {
                            if (bin != null) {
                                bin.close();
                            }
                            toClient.flush();
                            toClient.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 删除文件
     * @param ids id多个采用逗号分隔
     * @return
     */
    @PreAuth
    @Log(value = "删除文件", exception = "删除文件请求异常")
    @ApiOperation(value = "删除文件", notes = "删除文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", required = true, value = "多个用,号隔开", paramType = "form")
    })
    @PostMapping("/del")
    public Result<?> del(@RequestParam String ids) {
        Collection collection = CollectionUtil.stringToCollection(ids);
        for (Iterator<Long> it = collection.iterator(); it.hasNext(); ) {
            long id = it.next();
            sysAttachmentService.delete(id);
        }
        return Result.success("删除成功");
    }
}

