package com.group3;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lin
 * @ClassName JasyptTest
 * @date 2018/8/31 14:03
 * @description 参考https://www.cnblogs.com/zhangjianbing/p/9184083.html
 **/


@RunWith(SpringRunner.class)
@SpringBootTest
public class JasyptTest {

        @Autowired
        private StringEncryptor stringEncryptor;

        private String beforeEnc = "";
        private String afterEnc = "";


        @Test
        public void encryptPwd() {
            String result = stringEncryptor.encrypt(beforeEnc);
            System.out.println(result);
        }

        @Test
        public void decrypt(){
            String result = stringEncryptor.decrypt(afterEnc);
            System.out.println(result);
        }


}
