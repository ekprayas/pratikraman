package org.ekprayas.app.pratikraman.resource;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.errors.ErrorMessage;

import java.text.ParseException;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.ekprayas.app.pratikraman.auth.AuthUtils;
import org.ekprayas.app.pratikraman.core.User;
import org.ekprayas.app.pratikraman.db.UserDAO;

import com.google.common.base.Optional;
import com.nimbusds.jose.JOSEException;

@Path("/api/me")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
	
	private final UserDAO dao;
	
	public UserResource(UserDAO userDAO) {
		this.dao = userDAO;
	}

	@GET
	@UnitOfWork
	@PermitAll
	public Response getUser(@Context HttpServletRequest request) throws ParseException, JOSEException {
		Optional<User> foundUser = getAuthUser(request);
		
		if (!foundUser.isPresent()) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok().entity(foundUser.get()).build();
	}
	
	@GET
	@Path("/all")
	@UnitOfWork
	@RolesAllowed({"ADMIN"})
	public Response getAllUsers() {
		return Response.ok().entity(dao.findAll()).build();
	}

	@PUT
	@UnitOfWork
	@PermitAll
	public Response updateUser(@Valid User user, @Context HttpServletRequest request) throws ParseException, JOSEException {
		Optional<User> foundUser = getAuthUser(request);
		
		if (!foundUser.isPresent()) {
			return Response
					.status(Status.NOT_FOUND)
					.entity(new ErrorMessage(AuthResource.NOT_FOUND_MSG)).build();
		}
		
		User userToUpdate = foundUser.get();
		userToUpdate.setDisplayName(user.getDisplayName());
		userToUpdate.setEmail(user.getEmail());
		dao.save(userToUpdate);

		return Response.ok().build();
	}
	
	/*
	 * Helper methods
	 */	
	private Optional<User> getAuthUser(HttpServletRequest request) throws ParseException, JOSEException {
		String subject = AuthUtils.getSubject(request.getHeader(AuthUtils.AUTH_HEADER_KEY));
		return dao.findById(Long.parseLong(subject));
	}

}
