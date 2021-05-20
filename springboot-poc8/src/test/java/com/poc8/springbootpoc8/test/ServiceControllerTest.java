package com.poc8.springbootpoc8.test;
import java.io.File;
import java.io.FileInputStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class ServiceControllerTest {
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	ObjectMapper objMap = new ObjectMapper();
	

	@BeforeAll
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	@WithMockUser(username = "root", password = "root")
	public void saveUserTest() throws Exception {
		
		File f = new File("/home/webwerks/Pictures/male.jpg");
		FileInputStream fileInput1 = new FileInputStream(f);
		MockMultipartFile fstmp = new MockMultipartFile("image", f.getName(), "multipart/form-data",fileInput1);
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		
		mockMvc.perform(MockMvcRequestBuilders.multipart("/saveStudent").file(fstmp)
				.param("id", "87")
				.param("firstName", "John")
				.param("lastName","Snow")
				.param("contact", "999999999")
				.param("email", "snow@gmail.com")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
		.andExpect(redirectedUrl(null));
	}
	
	@Test
	@WithMockUser(username = "root", password = "root")
	public void deleteStudentTest() throws Exception {
		MvcResult result = mockMvc.perform(delete("/deleteStudent/4")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(redirectedUrl(null))
				.andExpect(status().isOk()).andReturn();
		
		int response = result.getResponse().getStatus();
		assertEquals(200, response);
	}
	
	@Test
	@WithMockUser(username = "root", password = "root")
	public void updateStudentTest() throws Exception {
		File f = new File("/home/webwerks/Pictures/male.jpg");
		FileInputStream fileInput1 = new FileInputStream(f);
		MockMultipartFile fstmp = new MockMultipartFile("image", f.getName(), "multipart/form-data",fileInput1);
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		
		mockMvc.perform(MockMvcRequestBuilders.multipart("/saveStudent").file(fstmp)
				.param("id", "20")
				.param("firstName", "Karan")
				.param("lastName","Yadav")
				.param("contact", "234567875")
				.param("email", "yadav.karan@gmail.com")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
		.andExpect(redirectedUrl(null));
		
		
	}
	
	@Test
	@WithMockUser(username = "root", password = "root")
	public void saveStudentProjectTest() throws Exception {

		MvcResult result = mockMvc.perform(post("/student/2/projects")
				.param("name", "JAVA")
				.contentType(MediaType.MULTIPART_FORM_DATA))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		
		int status = result.getResponse().getStatus();
		assertEquals(200, status);
	}
	
	@Test
	@WithMockUser(username = "root", password = "root")
	public void deleteProjectTest() throws Exception {
		MvcResult result = mockMvc.perform(delete("/student/2/deleteProject/5")///student/{studentId}/deleteProject/{id}
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk()).andReturn();
		
		int response = result.getResponse().getStatus();
		assertEquals(200, response);
	}
	
	@Test
	@WithMockUser(username = "root", password = "root")
	public void studentSearchTest() throws Exception {
		
		MvcResult result = mockMvc
				.perform(get("/student/search")
				.param("keyword", "1")
				.contentType(MediaType.MULTIPART_FORM_DATA))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		
		int status = result.getResponse().getStatus();
		assertEquals(200, status);
	}

}