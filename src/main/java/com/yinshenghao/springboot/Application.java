package com.yinshenghao.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * ClassName:Application
 * Package:com.yinshenghao.springboot
 * Description:
 *
 * @Date:2020/2/23 16:07
 * @Author:yl0806@163.com
 */
@SpringBootApplication
@EnableTransactionManagement
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
