package com.levi9.storage.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "storage")
@Getter
@Setter
@Builder
@NoArgsConstructor
public class Storage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "link")
	private String link;

	public Storage(Long id, String link) {
	        this.id = id;
	        this.link = link;
	    }

	public Storage(String link) {
	        this.link = link;
	    }


}
