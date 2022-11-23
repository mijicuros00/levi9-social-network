package com.levi9.socialnetwork.dto;

public class GroupDTO {
	
	private boolean isPrivate;

	private Long idAdmin;

	private String name;
	
	public GroupDTO() {}
	
	public GroupDTO(boolean isPrivate, Long idAdmin) {
		super();
		this.isPrivate = isPrivate;
		this.idAdmin = idAdmin;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public Long getIdAdmin() {
		return idAdmin;
	}

	public void setIdAdmin(Long idAdmin) {
		this.idAdmin = idAdmin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
