package com.poc8.springbootpoc8.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.poc8.springbootpoc8.ResponseModule;
import com.poc8.springbootpoc8.model.Student;
import com.poc8.springbootpoc8.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentRepository studentRepository;

	@Override
	public Page<Student> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.studentRepository.findAll(pageable);
	}

	@Override
	public Student saveStudent(final Student student) {
		return studentRepository.save(student);

	}

	@Override
	public void deleteUserById(Long id) {
		studentRepository.deleteById(id);

	}

	@Override
	public Student findById(Long id) {
		Optional<Student> optional = studentRepository.findById(id);
		Student student = null;
		if (optional.isPresent()) {
			student = optional.get();
		} else {
			throw new RuntimeException("Student not found for id: " + id);
		}
		return student;
	}

	@Override
	public List<Student> findAll() {
		return studentRepository.findAll();
	}

	@Override
	public List<Student> getAllStudents(Long studentId) {
		if(studentId != null) {
			return studentRepository.search(studentId);
		}
		return studentRepository.findAll();
	}
	


}
