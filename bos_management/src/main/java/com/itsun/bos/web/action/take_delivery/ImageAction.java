package com.itsun.bos.web.action.take_delivery;

import com.itsun.bos.utils.UUIDUtils;
import com.itsun.bos.web.action.base.comman.BaseAction;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author SY
 * @date 2017-07-29
 * on BOSV20
 * on 下午 11:14
 */
@ParentPackage("json-default")
@Namespace("/")
@Scope("prototype")
@Controller
public class ImageAction extends BaseAction<Object> {

    private File imgFile;
    private String imgFileFileName;
    private String imgFileContentType;

    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }

    public void setImgFileFileName(String imgFileFileName) {
        this.imgFileFileName = imgFileFileName;
    }

    public void setImgFileContentType(String imgFileContentType) {
        this.imgFileContentType = imgFileContentType;
    }

    @Action(value = "image_upload", results = {@Result(type = "json")})
    public String imageUpload() throws IOException {
        System.out.println(imgFile);
        System.out.println(imgFileFileName);
        System.out.println(imgFileContentType);

        String savePath = ServletActionContext.getServletContext().getRealPath("/upload/");
        String saveUrl = ServletActionContext.getRequest().getContextPath() + "/upload/";

        String randomFilename = UUIDUtils.getFileUUID(imgFileFileName);

        FileUtils.copyFile(imgFile, new File(savePath + "/" + randomFilename));

        //设置文件上传成功！

        Map<String, Object> map = new HashMap<>();
        map.put("error", 0);
        map.put("url", saveUrl + randomFilename);

        ServletActionContext.getContext().getValueStack().push(map);

        return SUCCESS;
    }

    @Action(value = "image_manage", results = {@Result(type = "json")})
    public String manage() {
        // 根目录路径，可以指定绝对路径，比如 d:/xxx/upload/xxx.jpg
        String rootPath = ServletActionContext.getServletContext().getRealPath(
                "/")
                + "upload/";
        // 根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
        String rootUrl = ServletActionContext.getRequest().getContextPath()
                + "/upload/";

        // 遍历目录取的文件信息
        List<Map<String, Object>> fileList = new ArrayList<>();
        // 当前上传目录
        File currentPathFile = new File(rootPath);
        // 图片扩展名
        String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};

        if (currentPathFile.listFiles() != null) {
            for (File file : currentPathFile.listFiles()) {
                Map<String, Object> hash = new HashMap<>();
                String fileName = file.getName();
                if (file.isDirectory()) {
                    hash.put("is_dir", true);
                    hash.put("has_file", (file.listFiles() != null));
                    hash.put("filesize", 0L);
                    hash.put("is_photo", false);
                    hash.put("filetype", "");
                } else if (file.isFile()) {
                    String fileExt = fileName.substring(
                            fileName.lastIndexOf(".") + 1).toLowerCase();
                    hash.put("is_dir", false);
                    hash.put("has_file", false);
                    hash.put("filesize", file.length());
                    hash.put("is_photo", Arrays.<String>asList(fileTypes)
                            .contains(fileExt));
                    hash.put("filetype", fileExt);
                }
                hash.put("filename", fileName);
                hash.put("datetime",
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file
                                .lastModified()));
                fileList.add(hash);
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("moveup_dir_path", "");
        result.put("current_dir_path", rootPath);
        result.put("current_url", rootUrl);
        result.put("total_count", fileList.size());
        result.put("file_list", fileList);
        ActionContext.getContext().getValueStack().push(result);

        return SUCCESS;
    }

}
