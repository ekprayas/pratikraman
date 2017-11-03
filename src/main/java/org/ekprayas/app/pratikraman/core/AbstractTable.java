/**
 * 
 */
package org.ekprayas.app.pratikraman.core;

import java.time.LocalDateTime;

import javax.persistence.Column;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * @author Amit Sachan
 *
 */
public class AbstractTable {
	

	@Column(name = "create_date_time")
	@CreationTimestamp
	private LocalDateTime createDateTime;

	@Column(name = "update_date_time")
	@UpdateTimestamp
	private LocalDateTime updateDateTime;

	
	/**
	 * @return the createDateTime
	 */
	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}

	/**
	 * @param createDateTime
	 *            the createDateTime to set
	 */
	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}

	/**
	 * @return the updateDateTime
	 */
	public LocalDateTime getUpdateDateTime() {
		return updateDateTime;
	}

	/**
	 * @param updateDateTime
	 *            the updateDateTime to set
	 */
	public void setUpdateDateTime(LocalDateTime updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

}
