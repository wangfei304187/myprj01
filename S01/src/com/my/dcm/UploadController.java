package com.my.dcm;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/upload")
public class UploadController {

    private String pictureUploadPath = "/home/wf/";
    private DateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");

    // upload single file
    // https://blog.csdn.net/ethan__xu/article/details/89283685
    // https://studygolang.com/articles/25586

    @RequestMapping(value = "/picture", consumes = "multipart/form-data", method = RequestMethod.POST)
    public void picture(@RequestParam("fileUpload") /*CommonsMultipartFile*/ MultipartFile file) {

        if (!file.isEmpty()) {
            String pathname = pictureUploadPath + "/" + yyyyMMddHHmmss.format(new Date()) + "/";
            File dir = new File(pathname);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            pathname += file.getOriginalFilename();
            File localFile = new File(pathname);
            try {
                file.transferTo(localFile);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    // upload multiple files
    @RequestMapping(value = "/pictures", consumes = "multipart/form-data", method = RequestMethod.POST)
    public String pictures(HttpServletRequest request) {

        String pathname = pictureUploadPath + "/" + yyyyMMddHHmmss.format(new Date()) + "/";
        File file = new File(pathname);
        if (!file.exists()) {
            file.mkdirs();
        }

        MultipartHttpServletRequest muti = (MultipartHttpServletRequest) request;
        System.out.println(muti.getMultiFileMap().size());

        MultiValueMap<String, MultipartFile> map = muti.getMultiFileMap();
        for (Map.Entry<String, List<MultipartFile>> entry : map.entrySet()) {

            List<MultipartFile> list = entry.getValue();
            for (MultipartFile multipartFile : list) {
                try {
                    multipartFile.transferTo(new File(pathname + multipartFile.getOriginalFilename()));
                } catch (IllegalStateException | IOException e) {
                    e.printStackTrace();
                    return "{\"result\": \"fail\"}";
                }
            }
        }

        return "{\"result\": \"Succ\"}";
    }
}