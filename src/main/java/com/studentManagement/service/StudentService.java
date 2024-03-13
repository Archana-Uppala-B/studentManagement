package com.studentManagement.service;

import com.studentManagement.entity.Student;
import com.studentManagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository sRepo;
    public void save(Student s)
    {   //to save the object into our database
        sRepo.save(s);
    }

    public List<Student> getAllStudents()
    {
        return sRepo.findAll();
    }

    public void deleteById(int id)
    {
        sRepo.deleteById(id);
    }

    public Student getStudentById(int id)
    {
        Student s= sRepo.getReferenceById(id);
        return s;
    }

    public Student findById(int id) {
        try {
            return sRepo.findById(id).get();  // Attempt to get student
        }
        catch (NoSuchElementException e) {
            return null;
        }
    }
}
