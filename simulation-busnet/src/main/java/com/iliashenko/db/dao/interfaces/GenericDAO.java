package com.iliashenko.db.dao.interfaces;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, PK extends Serializable> {
	void create(T t);

	T read(PK pk);

	void update(T t);

	void delete(T t);

	List<T> getAll();
}
