package com.fang.file.operation.delete;

import com.fang.file.operation.pojo.DeleteFile;

/**
 * 文件删除抽象类接口
 * @author 川川
 * @date 2022-02-20 19:13
 */
public abstract class Deleter {

    public abstract void delete(DeleteFile deleteFile);

}
