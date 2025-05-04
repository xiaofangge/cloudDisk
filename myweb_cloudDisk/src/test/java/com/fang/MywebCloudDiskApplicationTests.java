package com.fang;

import com.fang.file.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.UUID;

@SpringBootTest
class MywebCloudDiskApplicationTests {

    @Resource
    private FileUtil fileUtil;

    @Test
    void contextLoads() {
        fileUtil.isImageFile("aaa");

        System.out.println("1abkjk.jpg".lastIndexOf("."));
    }

    @Test
    void testStorage() {
        System.out.println((long)Math.pow(1024, 3) * 10);
    }

}
