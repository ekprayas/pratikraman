/**
 * 
 */
package org.ekprayas.app.pratikraman.authz;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import org.ekprayas.app.pratikraman.core.User;

/**
 * @author Amit Sachan
 *
 */
public class AppSecurityContext implements SecurityContext {

	private final User principal;
	private final SecurityContext securityContext;

	public AppSecurityContext(User principal, SecurityContext securityContext) {
		this.principal = principal;
		this.securityContext = securityContext;
	}

	@Override
	public Principal getUserPrincipal() {
		return principal;
	}

	@Override
	public boolean isUserInRole(String role) {
		return role.equals(principal.getRole());
	}

	@Override
	public boolean isSecure() {
		return securityContext.isSecure();
	}

	@Override
	public String getAuthenticationScheme() {
		return "CUSTOM_TOKEN";
	}
}
