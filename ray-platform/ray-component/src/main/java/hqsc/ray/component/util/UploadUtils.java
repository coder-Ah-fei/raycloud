package hqsc.ray.component.util;

import hqsc.ray.core.common.util.StringUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 文件上传工具类
 *
 * @author yangdc
 * @date Apr 18, 2012
 * <p>
 * <pre>
 * </pre>
 */
public class UploadUtils {
    /**
     * 表单字段常量
     */
    public static final String FORM_FIELDS = "form_fields";
    /**
     * 文件域常量
     */
    public static final String FILE_FIELDS = "file_fields";

    // 定义允许上传的文件扩展名
    private Map<String, String> extMap = new HashMap<String, String>();
    // 文件保存目录相对路径
    private String basePath = "resources/upload";
    // 文件的目录名
    private String dirName = "images";
    // 上传临时路径
    private static final String TEMP_PATH = "/temp";
    private String tempPath = basePath + TEMP_PATH;
    // 若不指定则文件名默认为 yyyyMMddHHmmss_xyz
    private String fileName;

    // 文件保存目录路径
    private String savePath;
    // 文件保存目录url
    private String saveUrl;

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public void setSaveUrl(String saveUrl) {
        this.saveUrl = saveUrl;
    }

    // 文件最终的url包括文件名
    private String fileUrl;

    public UploadUtils() {
        // 其中images,flashs,medias,files,对应文件夹名称,对应dirName
        // key文件夹名称
        // value该文件夹内可以上传文件的后缀名
        extMap.put("images", "gif,jpg,jpeg,png,bmp");
        extMap.put("flashs", "swf,flv");
        extMap.put("medias", "swf,flv,mp3,wav,wma,mp4,wmv,mid,avi,mpg,asf,rm,rmvb");
        extMap.put("files", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
    }

    /**
     * 保存文件
     *
     * @return
     */
    public String[] saveFileFromUrl(String imgUrl) {
        String fileExt = "jpg";
        String[] infos = new String[3];
        infos[0] = "false";
        URL url = null;
        try {
            url = new URL(imgUrl);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            // 创建文件夹
            savePath += dirName + "/";
            saveUrl += dirName + "/";
            File saveDirFile = new File(savePath);
            if (!saveDirFile.exists()) {
                saveDirFile.mkdirs();
            }
            // .../basePath/dirName/yyyyMMdd/
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String ymd = sdf.format(new Date());
            savePath += ymd + "/";
            saveUrl += ymd + "/";
            File dirFile = new File(savePath);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            String newFileName=df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
            String imageName = savePath+ newFileName;
            FileOutputStream fileOutputStream = new FileOutputStream(new File(imageName));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            byte[] context = output.toByteArray();
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
            fileUrl = saveUrl + newFileName;
            infos[0] = "true";
            infos[1] = fileUrl;
            infos[2] = newFileName;
        } catch (Exception e) {
            e.printStackTrace();
            infos[1]=e.getMessage();
        }

        return infos;
    }


    /**
     * *********************get/set方法*********************************
     */

    public String getSavePath() {
        return savePath;
    }

    public String getSaveUrl() {
        return saveUrl;
    }

    public Map<String, String> getExtMap() {
        return extMap;
    }

    public void setExtMap(Map<String, String> extMap) {
        this.extMap = extMap;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
        tempPath = basePath + TEMP_PATH;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public String getTempPath() {
        return tempPath;
    }

    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public static boolean isNotEmpty(String s) {
        return s != null && s.length() > 0;
    }


}
