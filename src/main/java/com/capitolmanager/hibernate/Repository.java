
package com.capitolmanager.hibernate;

public interface Repository<Entity extends AbstractEntity> {

	void saveOrUpdate(Entity entity);
	void delete(Entity entity);
}
