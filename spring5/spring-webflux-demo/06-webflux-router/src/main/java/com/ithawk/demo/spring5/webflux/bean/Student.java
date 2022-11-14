package com.ithawk.demo.spring5.webflux.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="t_student")
public class Student {

	@Id
	private String id;
	private String name;
	private int age;
}
















