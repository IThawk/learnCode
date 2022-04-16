package com.ithawk.demo.spring5.webflux.bean;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;


@Data
// 指定在MongoDB中生成的表
@Document(collection="t_student")
public class Student {

	@Id
	private String id;

	@NotBlank(message = "姓名不能为空")
	private String name;

	@Range(min = 10, max = 80, message = "年龄必须在{min}-{max}范围内")
	private int age;
}
















