package com.mkyong.editor;

public class Books {
	 private Integer id;
	 private String name;
	 
	 public Books() {

	}
	 
	 public Books(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	 public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
