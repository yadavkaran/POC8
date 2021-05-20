package com.poc8.springbootpoc8.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.poc8.springbootpoc8.ResponseModule;
import com.poc8.springbootpoc8.model.Student;

public interface StudentService {

	Page<Student> findPaginated(int pageNo, int pageSize, String sortField, String sortDir);

	Student saveStudent(Student student);

	void deleteUserById(Long id);

	Student findById(Long id);

	List<Student> findAll();
	
	List<Student> getAllStudents(Long studentId);
	
}
