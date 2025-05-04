package com.fang.common.exception;

import com.fang.common.vo.R;
import com.fang.common.vo.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理类
 * @author 川川
 * @date 2022-02-15 15:53
 */
@RestControllerAdvice // 可以实现三个方面的功能：1、全局异常处理， 2、全局数据绑定， 3、全局数据预处理
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 通用异常处理
     * @author xinchao
     * @date 2022/2/15 15:55
     * @param e 全局异常
     * @return R
     */
    @ExceptionHandler(Exception.class)  // 用来指明异常处理类型
    public R error(Exception e) {
        e.printStackTrace();
        log.error("全局异常捕获：{}", e.getMessage());
        return R.fail().message(e.getMessage());
    }

    /**
     * 空指针异常处理
     * @author xinchao
     * @date 2022/2/15 16:01
     * @param e 空指针异常
     * @return R
     */
    @ExceptionHandler(NullPointerException.class)
    public R nullPointError(NullPointerException e) {
        e.printStackTrace();
        log.error("全局异常捕获：{}", e.getMessage());
        return R.setResult(ResultCodeEnum.NULL_POINT);
    }

    /** 
     * 数组下标越界异常处理
     * @author xinchao
     * @date 2022/2/15 16:04
     * @param e 数组下标越界异常
     * @return R 
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public R IndexOfBoundsError(IndexOutOfBoundsException e) {
        e.printStackTrace();
        log.error("全局异常捕获：{}", e.getMessage());
        return R.setResult(ResultCodeEnum.INDEX_OUT_OF_BOUNDS);
    }
}
