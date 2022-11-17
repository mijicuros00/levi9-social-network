package com.levi9.socialnetwork.dto;

public class GroupDTO {
	
	private boolean isPrivate;

	public GroupDTO() {}
	
	public GroupDTO(boolean isPrivate) {
		super();
		this.isPrivate = isPrivate;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	
	
	
}
