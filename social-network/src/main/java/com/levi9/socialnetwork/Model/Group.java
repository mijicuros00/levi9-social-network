package com.levi9.socialnetwork.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="group", schema="public")
@NoArgsConstructor
@Getter
@Setter
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "private")
	private boolean isPrivate; 
	
	@Column(name = "id_admin")
	private Long idAdmin;

	public Group(Long id, boolean isPrivate, Long idAdmin) {
		super();
		this.id = id;
		this.isPrivate = isPrivate;
		this.idAdmin = idAdmin;
	}
	
	
	
}
