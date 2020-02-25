package com.yinshenghao.springboot.service;

import com.yinshenghao.springboot.mapper.StudentMapper;
import com.yinshenghao.springboot.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName:StudentServiceImpl
 * Package:com.yinshenghao.springboot.service
 * Description:
 *
 * @Date:2020/2/23 16:17
 * @Author:yl0806@163.com
 */
@Service("studentService")
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    /*注入springboot自动配置好的RedisTemplate*/
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public /*synchronized*/ List<Student> getAllstudents() {

        // 字符串序列化器
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);

        // 在高并发条件下，此处有点问题：缓存穿透
        // 查询缓存
        List<Student> studentList = (List<Student>) redisTemplate.opsForValue().get("allStudents");

        // 双重检测锁
        if (studentList == null) {
            synchronized (this) {
                // 从Redis获取以下
                studentList = (List<Student>) redisTemplate.opsForValue().get("allStudents");

                if(studentList == null) {
                    System.out.println("查询数据库---------");
                    // 缓存为空，查询一遍数据库
                    studentList = studentMapper.selectAllStudent();
                    // 把数据库查询出来的数据，放入Redis
                    redisTemplate.opsForValue().set("allStudents", studentList);
                } else {
                    System.out.println("查询缓存（内）---------");
                }
            }
        } else {
            System.out.println("查询缓存（外）---------");
        }
        return studentList;
    }

    @Override
    @Transactional
    public int update(Student student) {
        int result = studentMapper.updateByPrimaryKeySelective(student);
        System.out.println("更新结果：" + result);
        // double some = 7/0;
        return result;
    }
}
