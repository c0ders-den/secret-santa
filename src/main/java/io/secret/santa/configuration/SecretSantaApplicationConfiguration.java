/* 
 * Copyright (C) 2020-present  Arpan Mukhopadhyay. All rights reserved.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package io.secret.santa.configuration;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.secret.santa.ApplicationDBInitializer;

/**
 * @author Arpan Mukhopadhyay
 *
 */
@Configuration
public class SecretSantaApplicationConfiguration {

	@Autowired
	private ApplicationArguments args;
	
	@Bean
	public SecretSantaApplicationInitConfiguration appInitConfig() {
		SecretSantaApplicationInitConfiguration appInitConfig = new SecretSantaApplicationInitConfigurationImpl(args);
		new ApplicationDBInitializer();
		return appInitConfig;
	}
	
	@Bean
	public ConfigurableServletWebServerFactory factory(SecretSantaApplicationInitConfiguration appInitConfig) {
		JettyServletWebServerFactory factory = new JettyServletWebServerFactory();
		factory.addServerCustomizers(customizer(appInitConfig));
		return factory;
	}
	
	/**
	 * 
	 * @param appInitConfig
	 * @return
	 */
	private JettyServerCustomizer customizer(SecretSantaApplicationInitConfiguration appInitConfig) {
		JettyServerCustomizer customizer = new JettyServerCustomizer() {
			@Override
			public void customize(Server server) {
				Connector simpleConnector = addConnectors(server, appInitConfig);
				Connector[] connectors = new Connector[] {simpleConnector};
				server.setConnectors(connectors);
			}
		};
		return customizer;
	}
	
	
	private Connector addConnectors(Server server, SecretSantaApplicationInitConfiguration appInitConfig) {
		ServerConnector httpConnector = new ServerConnector(server);
		httpConnector.setPort(appInitConfig.getHttpPort());
		return httpConnector;
	}
}
