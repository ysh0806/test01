package com.yinshenghao.springboot.controller;

import com.yinshenghao.springboot.model.Student;
import com.yinshenghao.springboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLOutput;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ClassName:MybatisController
 * Package:com.yinshenghao.springboot.controller
 * Description:
 *
 * @Date:2020/2/23 16:10
 * @Author:yl0806@163.com
 */
@RestController
@RequestMapping("/boot")
public class MybatisController {

    @Autowired
    @Qualifier("studentService")
    private StudentService studentService;

    @GetMapping("/students")
    public Object students() {

        // 线程，该线程调用底层查询所有学生的方法
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                studentService.getAllstudents();
            }
        };

        // 多线程测试一下缓存穿透问题
        ExecutorService executorService = Executors.newFixedThreadPool(25);
        for (int i = 0; i < 10000; i++) {
            executorService.submit(runnable);
        }

        return studentService.getAllstudents();
    }

    @GetMapping("/update")
    public Object update() {
        Student student = new Student();
        student.setId(2);
        student.setName("王五");
        student.setAge(25);
        return studentService.update(student);
    }

}
