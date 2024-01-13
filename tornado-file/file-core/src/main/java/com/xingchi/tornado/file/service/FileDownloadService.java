package com.xingchi.tornado.file.service;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件下载服务
 *
 * @author xingchi
 * @date 2023/11/3 21:10
 */
public interface FileDownloadService {

    void download(String path);

    void download(Long id);

    String getUrl(String path);

    String getUrl(Long id);

}
