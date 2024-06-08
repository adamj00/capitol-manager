
package com.capitolmanager.hibernate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public abstract class AbstractEntity {

	public static final String D_ID = "id";

	@Id
	@GeneratedValue
	@Column(updatable = false)
	protected Long id;

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}
}
