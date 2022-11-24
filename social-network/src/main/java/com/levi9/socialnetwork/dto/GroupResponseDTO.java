package com.levi9.socialnetwork.dto;

import com.levi9.socialnetwork.Model.Group;

public class GroupResponseDTO {

    private Long id;
    
    private Long idAdmin;
    
    private boolean isPrivate;
    
    public GroupResponseDTO() {}

    public GroupResponseDTO(Long id, Long idAdmin, boolean isPrivate) {
        this.id = id;
        this.idAdmin = idAdmin;
        this.isPrivate = isPrivate;
    }
    
    public GroupResponseDTO(Group group) {
        this.id = group.getId();
        this.idAdmin = group.getIdAdmin();
        this.isPrivate = group.isPrivate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Long idAdmin) {
        this.idAdmin = idAdmin;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }
    
    
}
