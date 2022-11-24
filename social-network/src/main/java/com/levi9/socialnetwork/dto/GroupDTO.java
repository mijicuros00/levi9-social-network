package com.levi9.socialnetwork.dto;

public class GroupDTO {
	
    private boolean isPrivate;

    private String name;
	
    public GroupDTO() {}
	
    public GroupDTO(boolean isPrivate, String name) {
	    this.isPrivate = isPrivate;
      this.name = name;
	   }

	  public boolean isPrivate() {
		  return isPrivate;
	  }

	  public void setPrivate(boolean isPrivate) {
		  this.isPrivate = isPrivate;
	  }

	  public String getName() {
		  return name;
	  }

	  public void setName(String name) {
		  this.name = name;
	  }

}
