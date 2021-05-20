package com.poc8.springbootpoc8.service;

import java.util.List;

import com.poc8.springbootpoc8.ResponseModule;
import com.poc8.springbootpoc8.model.Project;
import com.poc8.springbootpoc8.model.Student;

public interface ProjectService {

	List<Project> findAll();

	Project findById(Long projectId);

	void saveProject(Project project);

	void deleteById(Long id);
	
}
