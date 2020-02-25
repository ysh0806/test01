package com.yinshenghao.springboot.service;

import com.yinshenghao.springboot.model.Student;

import java.util.List;

/**
 * ClassName:StudentService
 * Package:com.yinshenghao.springboot.service
 * Description:
 *
 * @Date:2020/2/23 16:15
 * @Author:yl0806@163.com
 */
public interface StudentService {

    public List<Student> getAllstudents();

    public int update(Student student);

}
