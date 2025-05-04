package com.fang.service.impl;

import com.fang.mapper.StorageMapper;
import com.fang.pojo.Storage;
import com.fang.service.StorageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 川川
 * @date 2022-02-24 15:34
 */
@Service
public class StorageServiceImpl implements StorageService {

    @Resource(name = "storageMapper")
    private StorageMapper storageMapper;

    @Override
    public Storage getStorageInfo(Long userId) {
        return storageMapper.getStorageInfo(userId);
    }
}
