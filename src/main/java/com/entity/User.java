package com.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class User implements Serializable{

	private static final long serialVersionUID = 3407873063428268372L;

	@ApiModelProperty(value = "名字", required = true)
	private String name;
	
	@ApiModelProperty(value = "年龄", required = true)
	private Integer age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	
	
}
