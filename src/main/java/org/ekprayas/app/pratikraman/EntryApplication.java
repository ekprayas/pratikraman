package org.ekprayas.app.pratikraman;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.ws.rs.client.Client;

import org.ekprayas.app.pratikraman.AppConfiguration.ClientSecretsConfiguration;
import org.ekprayas.app.pratikraman.auth.AuthFilter;
import org.ekprayas.app.pratikraman.core.Institute;
import org.ekprayas.app.pratikraman.core.User;
import org.ekprayas.app.pratikraman.db.InstituteDAO;
import org.ekprayas.app.pratikraman.db.UserDAO;
import org.ekprayas.app.pratikraman.resource.AuthResource;
import org.ekprayas.app.pratikraman.resource.ClientResource;
import org.ekprayas.app.pratikraman.resource.InstituteResource;
import org.ekprayas.app.pratikraman.resource.UserResource;

public class EntryApplication extends Application<AppConfiguration> {
	public static final String APP_NAME = "PRATIKRAMAN";

	public static void main(final String[] args) throws Exception {
		new EntryApplication().run(args);
	}

	private final HibernateBundle<AppConfiguration> hibernateBundle = new HibernateBundle<AppConfiguration>(
			User.class, Institute.class) {
		@Override
		public DataSourceFactory getDataSourceFactory(
				final AppConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};

	@Override
	public String getName() {
		return APP_NAME;
	}

	@Override
	public void initialize(final Bootstrap<AppConfiguration> bootstrap) {
		bootstrap.addBundle(new MigrationsBundle<AppConfiguration>() {
			@Override
			public DataSourceFactory getDataSourceFactory(
					final AppConfiguration configuration) {
				return configuration.getDataSourceFactory();
			}
		});

		bootstrap.addBundle(hibernateBundle);

		bootstrap.addBundle(new AssetsBundle("/assets/app.js", "/app.js", null,
				"app"));
		bootstrap.addBundle(new AssetsBundle("/assets/stylesheets",
				"/stylesheets", null, "css"));
		bootstrap.addBundle(new AssetsBundle("/assets/directives",
				"/directives", null, "directives"));
		bootstrap.addBundle(new AssetsBundle("/assets/controllers",
				"/controllers", null, "controllers"));
		bootstrap.addBundle(new AssetsBundle("/assets/services", "/services",
				null, "services"));
		bootstrap.addBundle(new AssetsBundle("/assets/vendor", "/vendor", null,
				"vendor"));
		bootstrap.addBundle(new AssetsBundle("/assets/partials", "/partials",
				null, "partials"));
	}

	@Override
	public void run(final AppConfiguration configuration,
			final Environment environment) throws ClassNotFoundException {

		final UserDAO dao = new UserDAO(hibernateBundle.getSessionFactory());
		final InstituteDAO instituteDAO = new InstituteDAO(
				hibernateBundle.getSessionFactory());

		final Client client = new JerseyClientBuilder(environment).using(
				configuration.getJerseyClient()).build(getName());
		final ClientSecretsConfiguration clientSecrets = configuration
				.getClientSecrets();

		// all Resources
		environment.jersey().register(new ClientResource());
		environment.jersey().register(new UserResource(dao));
		environment.jersey().register(
				new AuthResource(client, dao, clientSecrets));
		environment.jersey().register(new InstituteResource(instituteDAO));

		// added authz
		//environment.jersey().register(RolesAllowedDynamicFeature.class);

		environment.servlets().addFilter("AuthFilter", new AuthFilter())
				.addMappingForUrlPatterns(null, true, "/api/me");
		environment.servlets().addFilter("AuthFilter", new AuthFilter())
				.addMappingForUrlPatterns(null, true, "/api/bank");
	}
}
