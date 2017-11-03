/**
 * 
 */
package org.ekprayas.app.pratikraman.db;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;

import org.ekprayas.app.pratikraman.core.Institute;
import org.hibernate.SessionFactory;

import com.google.common.base.Optional;

/**
 * @author Amit Sachan
 *
 */
public class InstituteDAO extends AbstractDAO<Institute> {

	public InstituteDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public List<Institute> findAll() {
		return list(namedQuery("Institute.findAll"));
	}

	public Optional<Institute> findById(Long id) {
		return Optional.fromNullable(get(id));
	}

	public Optional<Institute> findByCode(String code) {
		Institute found = (Institute) namedQuery("User.findByCode")
				.setParameter("code", code).uniqueResult();
		return Optional.fromNullable(found);
	}

	public Institute save(Institute institute) {
		return persist(institute);
	}
}
