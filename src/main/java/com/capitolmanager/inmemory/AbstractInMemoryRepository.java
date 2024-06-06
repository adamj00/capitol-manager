/*
 * Created on 06-06-2024 20:16 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.inmemory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.capitolmanager.hibernate.AbstractEntity;


public abstract class AbstractInMemoryRepository<ENTITY extends AbstractEntity> {

	private final List<ENTITY> entities;


	@Autowired
	protected AbstractInMemoryRepository(List<ENTITY> entities) {

		Assert.notNull(entities, "entities must not be null");

		this.entities = entities;
	}

	public void saveOrUpdate(ENTITY entity) {

		entities.remove(entity);
		entities.add(entity);
	}

	public void delete(ENTITY entity) {

		entities.remove(entity);
	}
}
