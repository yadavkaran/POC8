package com.poc8.springbootpoc8.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.poc8.springbootpoc8.controller.FileUploadUtil;
import com.poc8.springbootpoc8.model.Project;
import com.poc8.springbootpoc8.model.Student;
import com.poc8.springbootpoc8.service.ProjectService;
import com.poc8.springbootpoc8.service.StudentService;

//@Controller
@RestController
public class ServiceController {

	@Autowired
	private StudentService studentService;

	@Autowired
	private ProjectService projectService;

	// display list of students
	@GetMapping("/")
	public String viewHomePage(Model model) {
		return findPaginated(1, "firstName", "asc", model);
	}

	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo, @RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir, Model model) {

		int pageSize = 5;

		Page<Student> page = studentService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Student> listStudents = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("listStudents", listStudents);
		return "index";
	}

	// new student form
	@GetMapping("/showNewStudentForm")
	public String showNewStudentForm(Model model) {
		Student student = new Student();
		model.addAttribute("student", student);
		return "new_student";
	}

	// saving new student with validation
	@PostMapping("/saveStudent")
	public String saveStudent(@ModelAttribute("student") Student student,
			@RequestParam("image") MultipartFile multipartFile)throws IOException {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		student.setPhotos(fileName);
		Student savedStudent=studentService.saveStudent(student);
		
		String uploadDir = "student-photos/"+savedStudent.getId();
		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		
		
		studentService.saveStudent(student);
		return "redirect:/";
	}
	@GetMapping("/showNewProjectForm")
	public String showNewProjectForm(Model model) {
		Project project = new Project();
		model.addAttribute("project", project);
		return "new_project";
	}
	
	@PostMapping("/saveProject")
	public String saveProject(@ModelAttribute("project") Project project) {
		projectService.saveProject(project);
		return "redirect:/showNewProjectForm";
	}
	
	@DeleteMapping("/deleteStudent/{id}") //for testing	
	@GetMapping("/deleteStudent/{id}")
	public String deleteStudent(@PathVariable(value = "id") long id) {
		studentService.deleteUserById(id);
		return "redirect:/";
	}

	@GetMapping("/addStudentProject/{id}")
	public String addStudentProjectForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("projects", projectService.findAll());
		model.addAttribute("student", studentService.findById(id));

		return "addStudentProject";
	}

	@GetMapping("/student/{id}/projects")
	public String saveStudentProject(@PathVariable Long id, @RequestParam Long projectId, Model model) {
		Project project = projectService.findById(projectId);
		Student student = studentService.findById(id);

		if (student != null) {
			if (!student.hasProject(project)) {
				student.getProject().add(project);
			}

			studentService.saveStudent(student);
			model.addAttribute("student", studentService.findById(id));
			model.addAttribute("projects", projectService.findAll());

			return "redirect:/";
		}
		return "redirect:/";
	}

	@GetMapping("/getStudents")
	public @ResponseBody List<Student> getStudents() {
		return (List<Student>) studentService.findAll();
	}
	
	@DeleteMapping("/student/{studentId}/deleteProject/{id}") //for testing
	@GetMapping("/student/{studentId}/deleteProject/{id}")
	public String deleteProject(@PathVariable(value = "studentId") Long studentId,
			@PathVariable(value = "id") Long id, Model model) {
		projectService.deleteById(id);
		
		model.addAttribute("student", studentService.findById(studentId));
		model.addAttribute("project",projectService.findAll());
		model.addAttribute("project", new Project()); 
		
		return "addStudentProject";
		
	}
	
	@GetMapping("/student/search")
	public String searchStudent(Model model, @Param("keyword") Long keyword) {
		System.out.println(keyword + "HI KARAN");
		List<Student> students = studentService.getAllStudents(keyword);
		for(Student student : students){
			System.out.println(student+"Bye");
			
		}
		
		//model.addAttribute("projects",projectService.findAll());
		model.addAttribute("listStudents",students);
		model.addAttribute("keyword", keyword);
		return "index";
		
	}

}
