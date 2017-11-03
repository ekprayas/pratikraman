/**
 * 
 */
package org.ekprayas.app.pratikraman.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Amit Sachan
 *
 */
@Entity
@Table(name = "institutes")
@NamedQueries({
		@NamedQuery(name = "Institute.findByCode", query = "SELECT i FROM Institute i WHERE i.code = :code"),
		@NamedQuery(name = "Institute.findAll", query = "SELECT i FROM Institute i") })
public class Institute extends AbstractTable {

	public Institute(String code, String displayName) {
		this.code = code;
		this.displayName = displayName;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "code", unique = true)
	private String code;

	@Column(name = "name")
	private String displayName;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName
	 *            the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
