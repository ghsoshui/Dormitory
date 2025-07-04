package com.example.springboot.service.impl;

import com.example.springboot.ExceptionHandler.BusinessException;
import com.example.springboot.entity.DormBuild;
import com.example.springboot.entity.DormRoom;
import com.example.springboot.entity.Student;
import com.example.springboot.mapper.StudentMapper;
import com.example.springboot.service.StudentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentMapper studentMapper;

    @Override
    public Student stuLogin(String username, String password) {
        return studentMapper.findByUsernameAndPassword(username, password);
    }

    @Override
    public int addNewStudent(Student student) {
        try {
            return studentMapper.insert(student);
        } catch (DuplicateKeyException e) {
            // Spring 的 DuplicateKeyException，需 import org.springframework.dao.DuplicateKeyException
            throw new BusinessException("用户名已存在，请更换用户名！");
        } catch (Exception e) {
            throw new BusinessException("添加失败，系统异常！");
        }

    }

    @Override
    public PageInfo<Student> find(Integer pageNum, Integer pageSize, String search) {
        PageHelper.startPage(pageNum, pageSize);
        List<Student> studentList = studentMapper.findByCriteria(search);
        if (studentList == null) {
            studentList = Collections.emptyList();
        }
        return new PageInfo<>(studentList);
    }

    @Override
    public int updateNewStudent(Student student) {
        // Ensure student object has username, as it's used in WHERE clause of mapper
        if (student.getUsername() == null || student.getUsername().isEmpty()) {
            return 0; // Or throw an exception
        }
        return studentMapper.update(student);
    }

    @Override
    public int deleteStudent(String username) {
        return studentMapper.deleteByUsername(username);
    }

    @Override
    public Long stuNum() {
        return studentMapper.countAll();
    }

    @Override
    public Student stuInfo(String username) {
        return studentMapper.findByUsername(username);
    }

    @Override
    public Student getStudentByUsername(String username) {
        return studentMapper.findByUsername(username);
    }

    @Override
    public Student registerStudent(Student student) {
        // 检查用户名是否已存在
        Student existingStudent = studentMapper.findByUsername(student.getUsername());
        if (existingStudent != null) {
            return null;
        }
        
        // 创建新的学生对象
        Student studentToInsert = new Student();
        studentToInsert.setUsername(student.getUsername());
        studentToInsert.setPassword(student.getPassword());
        studentToInsert.setName(student.getName());
        studentToInsert.setGender(student.getGender());
        studentToInsert.setAge(student.getAge());
        studentToInsert.setPhoneNum(student.getPhoneNum());
        studentToInsert.setEmail(student.getEmail());
        studentToInsert.setAvatar(student.getAvatar());

        int result = studentMapper.insert(studentToInsert);
        if (result > 0) {
            return studentToInsert;
        } else {
            return null;
        }
    }

    @Override
    public String assignDorm(Student student) {
        // 得到学生的性别和学号
        String gender = student.getGender();
        String username = student.getUsername();
        gender = gender+"宿舍";


        // 在数据库中搜索符合性别且根据楼栋id寻找宿舍，遍历宿舍人数不为4的宿舍取第一条，如果没有更改返回信息
        // 查询楼栋
        List<DormBuild> dormBuilds = studentMapper.findByGender(gender);
        // 使用楼栋id查询宿舍，遍历宿舍人数不为4的宿舍取第一条
        DormRoom dormRoom = studentMapper.findDormRoomsByBuildingId(dormBuilds);
        // 获取宿舍id，成功就开始查询并更新宿舍，获取失败就返回错误信息
        if(dormRoom != null)
        {
            int result = studentMapper.updateDormRoom(username, dormRoom);
            return "寝室分配成功!";
        }


        return "请联系管理员分配寝室！";
    }
}
