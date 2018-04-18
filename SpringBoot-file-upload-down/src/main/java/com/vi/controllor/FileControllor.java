package com.vi.controllor;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

@RestController
public class FileControllor {

    //存储路径
    private static final String FILE_PATH = "E://test/";

    /**
     * 不支持大文件下载会oom
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "{fileName}/download2")
    public ResponseEntity<byte[]> download(@PathVariable(value = "fileName") String fileName) throws Exception {
        File file = new File(FILE_PATH + fileName);
        HttpHeaders headers = new HttpHeaders();
        //以附件的形式下载
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }

    /**
     * 文件下载
     *
     * @param response
     * @return
     */
    @RequestMapping("{fileName}/download1")
    public String downloadFile(@PathVariable(value = "fileName") String fileName, HttpServletResponse response) {
        if (fileName != null) {
            File file = new File(FILE_PATH + fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                //以附件的形式下载
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024 * 100];
                try (FileInputStream fis = new FileInputStream(file); BufferedInputStream bis = new BufferedInputStream(fis)) {
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    os.flush();
                    return "下载成功";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            return "文件不存在！";
        }
        return "下载失败";
    }

    /**
     * 单一或多个文件上传，上传后的文件存储根据需要自己扩展,一批中有失败的文件暂不删除。
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "fileUpload", method = RequestMethod.POST)
    public String handleFileUpload(MultipartHttpServletRequest request) {
        List<MultipartFile> files = request.getFiles("file");
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    //文件存放路径
                    File dest = new File(FILE_PATH + file.getOriginalFilename());
                    if (!dest.getParentFile().exists()) {
                        dest.getParentFile().mkdirs();
                    }
                    file.transferTo(dest);
                } catch (Exception e) {
                    //之前和之后的文件都不处理
                    return file.getOriginalFilename() + "上传失败！";
                }
            } else {
                //之前和之后的文件都不处理
                return file.getOriginalFilename() + "是空文件!";
            }
        }
        return "批量上传成功！";
    }
}
