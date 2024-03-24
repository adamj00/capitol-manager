/*
 * Created on 24-03-2024 20:04 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.hibernate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
public class AbstractEntity {

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
