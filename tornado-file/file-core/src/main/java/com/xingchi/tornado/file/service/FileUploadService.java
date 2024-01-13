package com.xingchi.tornado.file.service;

import com.xingchi.tornado.file.model.dto.FileUploadDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;

/**
 * 文件上传服务接口
 *
 * @author xingchi
 * @date 2023/10/15 17:32
 * @modified xingchi
 */
public interface FileUploadService {

    FileUploadDTO upload(MultipartFile file);

    FileUploadDTO upload(byte[] source, String fileName);

    FileUploadDTO upload(InputStream inputStream, String fileName);

    FileUploadDTO upload(URL url, String fileName);

    FileUploadDTO upload(File file);

    FileUploadDTO upload(Path path);

    FileUploadDTO upload(Path path, String fileName);

    FileUploadDTO upload(Path first, Path path);

}
