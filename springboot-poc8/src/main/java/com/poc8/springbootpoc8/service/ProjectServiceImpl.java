package com.poc8.springbootpoc8.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc8.springbootpoc8.ResponseModule;
import com.poc8.springbootpoc8.model.Project;
import com.poc8.springbootpoc8.model.Student;
import com.poc8.springbootpoc8.repository.ProjectRepository;

@Service
public class ProjectServiceImpl implements ProjectService{
	
	@Autowired 
	ProjectRepository projectRepository;

	@Override
	public List<Project> findAll() {
		return projectRepository.findAll();
	}

	@Override
	public Project findById(Long projectId) {
		Optional<Project> optional = projectRepository.findById(projectId);
		Project project = null;
		if (optional.isPresent()) {
			project = optional.get();
		} else {
			throw new RuntimeException("Project not found for id: " + projectId);
		}
		return project;
	}

	@Override
	public void saveProject(Project project) {
		projectRepository.save(project);
		
	}

	@Override
	public void deleteById(Long id) {
		projectRepository.deleteById(id);
		
	}

	}


