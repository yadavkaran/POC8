package com.poc8.springbootpoc8.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="project")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long projectId;


	@Column(name = "project_name")
	private String name;

	@ManyToMany(mappedBy = "project",cascade=CascadeType.PERSIST, fetch=FetchType.LAZY)
	private Set<Student> student=new HashSet<>();
	
}
