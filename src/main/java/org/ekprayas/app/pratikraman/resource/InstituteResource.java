/**
 * 
 */
package org.ekprayas.app.pratikraman.resource;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.errors.ErrorMessage;

import java.text.ParseException;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.ekprayas.app.pratikraman.core.Institute;
import org.ekprayas.app.pratikraman.db.InstituteDAO;

import com.google.common.base.Optional;
import com.nimbusds.jose.JOSEException;

/**
 * @author Amit Sachan
 *
 */
@Path("/api/bank")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InstituteResource {

	private final InstituteDAO dao;

	public InstituteResource(InstituteDAO instituteDAO) {
		this.dao = instituteDAO;
	}

	@GET
	@Path("/all")
	@UnitOfWork
	@PermitAll
	public Response getAllInstitutes() {
		return Response.ok().entity(dao.findAll()).build();
	}

	@GET
	@UnitOfWork
	@PermitAll
	public Response getInstitute(@Valid final long id) {
		final Institute institute = dao.findById(id).get();
		return Response.ok().entity(institute).build();
	}

	@POST
	@UnitOfWork
	@RolesAllowed({ "ADMIN" })
	public Response createInstitute(@Valid final Institute institute)
			throws JOSEException {
		final Institute saved = dao.save(institute);
		return Response.status(Status.CREATED).entity(saved).build();
	}

	@PUT
	@UnitOfWork
	@RolesAllowed({ "ADMIN" })
	public Response updateInstitute(@Valid Institute institute)
			throws ParseException, JOSEException {
		Optional<Institute> foundInstitute = helperGetInstitute(institute
				.getId());

		if (!foundInstitute.isPresent()) {
			return Response.status(Status.NOT_FOUND)
					.entity(new ErrorMessage(AuthResource.NOT_FOUND_MSG))
					.build();
		}

		Institute instituteToUpdate = foundInstitute.get();
		instituteToUpdate.setDisplayName(institute.getDisplayName());
		instituteToUpdate.setCode(institute.getCode());
		dao.save(instituteToUpdate);

		return Response.ok().build();
	}

	/*
	 * Helper methods
	 */
	private Optional<Institute> helperGetInstitute(long id)
			throws ParseException, JOSEException {
		return dao.findById(id);
	}

}
