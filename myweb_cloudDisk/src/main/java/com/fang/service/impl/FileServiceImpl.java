package com.fang.service.impl;

import com.fang.mapper.FileMapper;
import com.fang.pojo.File;
import com.fang.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 川川
 * @date 2022-02-18 16:01
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Resource(name = "fileMapper")
    private FileMapper fileMapper;

    @Override
    public List<File> listByIdentifer(String identifier) {
        return fileMapper.listByIdentfer(identifier);
    }

    @Override
    public File selectByPrimaryKey(Long fileId) {
        return fileMapper.selectByPrimaryKey(fileId);
    }
}
