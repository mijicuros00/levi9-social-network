package com.levi9.socialnetwork.dto;

public class GroupDTO {
	
	private boolean isPrivate;

	private Long idAdmin;
	
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
	
	
	
}
