package com.fang.service;

import com.fang.pojo.File;

import java.util.List;

/**
 * @author 川川
 * @date 2022-02-18 16:00
 */

public interface FileService {
    List<File> listByIdentifer(String identifier);

    File selectByPrimaryKey(Long fileId);
}
