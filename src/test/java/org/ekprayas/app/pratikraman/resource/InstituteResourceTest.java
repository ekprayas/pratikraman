/**
 * 
 */
package org.ekprayas.app.pratikraman.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import io.dropwizard.testing.junit.ResourceTestRule;

import org.ekprayas.app.pratikraman.core.Institute;
import org.ekprayas.app.pratikraman.db.InstituteDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.google.common.base.Optional;

/**
 * @author Amit Sachan
 *
 */

public class InstituteResourceTest {
//	private static final InstituteDAO dao = mock(InstituteDAO.class);
//
//	@ClassRule
//	public static final ResourceTestRule resources = ResourceTestRule.builder()
//			.addResource(new InstituteResource(dao)).build();
//
//	private final Institute institute = new Institute("blah",
//			"blah@example.com");
//
//	@Before
//	public void setup() {
//		when(dao.findByCode(eq("blah"))).thenReturn(
//				Optional.fromNullable(institute));
//	}
//
//	@After
//	public void tearDown() {
//		reset(dao);
//	}
//
//	@Test
//	public void testGetPerson() {
//		Institute actual = resources.client().target("/api/bank").request("1")
//				.get(Institute.class);
//		assertThat(actual.getCode()).isEqualTo(institute.getCode());
//	}
}
