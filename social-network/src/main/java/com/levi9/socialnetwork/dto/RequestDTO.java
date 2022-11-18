package com.levi9.socialnetwork.dto;

public class RequestDTO {

	private Long idUser;
	
	private Long idGroup;
	
	public RequestDTO() {}

	public RequestDTO(Long idUser, Long idGroup) {
		super();
		this.idUser = idUser;
		this.idGroup = idGroup;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public Long getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(Long idGroup) {
		this.idGroup = idGroup;
	}


	
}
