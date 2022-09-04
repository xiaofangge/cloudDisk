package com.fang.service;

import com.fang.pojo.Storage;

/**
 * @author 川川
 * @date 2022-02-24 15:33
 */

public interface StorageService {

    Storage getStorageInfo(Long userId);
}
