package com.studentManagement.controller;
import com.studentManagement.entity.Student;
import com.studentManagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.*;
import java.util.List;
import java.util.Optional;

@Controller
public class StudentController {
    @Autowired
    private StudentService sService;
    @GetMapping("/")
    public String home()
    {
        return "home";
    }

    @GetMapping("/student_add")
    public String studentAdd()
    {
        return "student_add";
    }

    @GetMapping("/all_students")
    public ModelAndView getAllStudents()
    {
        List<Student> list =sService.getAllStudents();
        return new ModelAndView("all_students","student",list);
    }

    @GetMapping("/delete_student")
    public ModelAndView deleteStudents()
    {
        List<Student> list=sService.getAllStudents();
        return new ModelAndView("delete_student","students",list);
    }

    @GetMapping("/update_student")
    public ModelAndView updateStudents()
    {
        List<Student> list=sService.getAllStudents();
        return new ModelAndView("update_student","students",list);
    }

    @PostMapping("/save")
    public String addStudent(@ModelAttribute Student s)
    {
        sService.save(s);
        return "redirect:/all_students";
    }

    @RequestMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable("id") int id)
    {
        sService.deleteById(id);
        return "redirect:/delete_student";
    }

    @RequestMapping("/editStudent/{id}")
    public String editStudent(@PathVariable("id") int id,Model model)
    {
        Student s=sService.getStudentById(id);
        model.addAttribute("student",s);
        return "studentEdit";
    }

    @GetMapping("/search_student")
    public String searchStudentForm(Model model)
    {
        model.addAttribute("studentId", 0);
        return "search_student";
    }

    @PostMapping("/search_student")
    public String searchStudent(@ModelAttribute Student student, Model model) {

        Student studentToFind = student;

        if (studentToFind != null) {

            Optional<Student> foundStudent = Optional.ofNullable(sService.findById(studentToFind.getId()));

            if (foundStudent.isPresent()) {
                model.addAttribute("student", foundStudent.get());
            }
            else
            {
                model.addAttribute("studentId", studentToFind.getId());
                model.addAttribute("errorMessage", "Student not found with ID: " + studentToFind.getId());
                return "search_student";
            }
        }
        else {
            model.addAttribute("errorMessage", "Invalid student data provided.");
            return "search_student";
        }
        return "search_student";
    }
}
