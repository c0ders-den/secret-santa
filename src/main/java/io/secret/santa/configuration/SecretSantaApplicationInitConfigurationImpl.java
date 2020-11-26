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

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.ApplicationArguments;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.secret.santa.ConfigParsingException;
import io.secret.santa.IllegalConfigurationException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

/**
 * @author Arpan Mukhopadhyay
 *
 */
@Log4j2
@Getter
public class SecretSantaApplicationInitConfigurationImpl implements SecretSantaApplicationInitConfiguration {

	private String serverHost;
	private int serverPort;
	private String dbHost;
	private int dbPort;
	private String dbUser;
	private String dbPass;
	
	private AppConfigProperties appProperties = new AppConfigProperties();
	private ServerConfigProperties srvProperties = new ServerConfigProperties();
	private DatabaseConfigProperties dbProperties = new DatabaseConfigProperties();
	
	/**
	 * 
	 * @param args
	 */
	public SecretSantaApplicationInitConfigurationImpl(ApplicationArguments args) {
		long start = System.nanoTime();
		parseConfig(args);
		long tt = System.nanoTime() - start;
		logger.info("Configuration loaded in {} ms", TimeUnit.MILLISECONDS.convert(tt, TimeUnit.NANOSECONDS));
	}
	
	/**
	 * Loads the property from the given argument
	 * 
	 * @param args the argument
	 */
	private void parseConfig(ApplicationArguments args) {
		List<String> configFileOption = args.getOptionValues("config");
		String configFileName = null;
		if (null == configFileOption || configFileOption.size() == 0) {
			configFileName = DEFAULT_CONFIG_FILE;
		} else {
			configFileName = configFileOption.get(0);
		}
		if (null == configFileName || configFileName.isBlank()) {
			logger.error("Please provide a config file after --config or remove the --config option to run the server with default host and port");
			throw new IllegalConfigurationException("Invalid value for --config option.");
		}
		File configFile = new File(configFileName);
		if (configFile.isFile() && configFile.exists() && configFile.canRead()) {
			
		} else {
			logger.error("Can not read config file. Please check if " + configFile.getAbsolutePath() + " exists and can be read.");
			throw new IllegalConfigurationException("Invalid config file or file does not exist.");
		}
		
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		mapper.findAndRegisterModules();
		try {
			final SecretSantaConfigTemplate configTemplate = mapper.readValue(configFile, SecretSantaConfigTemplate.class);
			populateProperties(configTemplate);
		} catch (Exception e) {
			throw new ConfigParsingException("Can not parse file at " + configFile.getAbsolutePath(), e);
		}
	}
	
	/**
	 * 
	 * @param template
	 */
	private void populateProperties(final SecretSantaConfigTemplate template) {
		populateAppProperties(template, appProperties);
		populateServerProperties(template, srvProperties);
		populateDBProperties(template, dbProperties);
	}
	
	/**
	 * 
	 * @param t
	 * @param conf
	 */
	private void populateAppProperties(final SecretSantaConfigTemplate t, AppConfigProperties conf) {
		conf.setAppMode(t.getAppMode());
		conf.setAppName(t.getAppName());
	}
	
	/**
	 * 
	 * @param t
	 * @param conf
	 */
	private void populateServerProperties(final SecretSantaConfigTemplate t, ServerConfigProperties conf) {
		final SecretSantaAppServerConfiguration srvConf = t.getServer();
		String addr = null == srvConf.getAddress() || srvConf.getAddress().strip().isBlank() ? DEFAULT_HTTP_ADDR
				: srvConf.getAddress();
		conf.setAddress(addr);
		Integer port = null == srvConf.getPort() ? DEFAULT_HTTP_PORT : srvConf.getPort();
		conf.setPort(port);
		conf.setProtocol(srvConf.getProtocol());
		conf.setCompressed(srvConf.getCompressd());
		conf.setCertFile(srvConf.getCertFile());
		conf.setCertKey(srvConf.getCertKey());
	}
	
	/**
	 * 
	 * @param t
	 * @param conf
	 */
	private void populateDBProperties(final SecretSantaConfigTemplate t, DatabaseConfigProperties conf) {
		final SecretSantaDBServerConfiguration dbConf = t.getDb();
		conf.setType(dbConf.getType());
		conf.setHost(dbConf.getHost());
		conf.setName(dbConf.getName());
		conf.setUsername(dbConf.getUsername());
		conf.setPassword(unwrapQuotedString(dbConf.getPassword()));
		conf.setPath(dbConf.getPath());
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	private String unwrapQuotedString(String s) {
		if (null == s || s.isBlank()) return s;
		s = s.strip();
		boolean b = s.startsWith("'");
		if (b && s.endsWith("'")) {
			s = s.substring(1, s.length() - 1);
		}
		return s;
	}
	
	@Getter
	@Setter
	private class AppConfigProperties {
		private AppMode appMode;
		private String appName;
	}

	@Getter
	@Setter
	private class ServerConfigProperties {
		private String protocol;
		private String address;
		private int port;
		private String certFile;
		private String certKey;
		private boolean compressed;
	}
	
	@Getter
	@Setter
	private class DatabaseConfigProperties {
		private DBType type;
		private String host;
		private String name;
		private String username;
		private String password;
		private String path;
	}
	
	@Override
	public AppMode getAppMode() {
		return appProperties.getAppMode();
	}

	@Override
	public String getName() {
		return appProperties.getAppName();
	}

	@Override
	public String getAppBase() {
		return null;
	}

	@Override
	public String getData() {
		return null;
	}

	@Override
	public String getLogs() {
		return null;
	}

	@Override
	public String getProtocol() {
		return srvProperties.getProtocol();
	}

	@Override
	public String getHttpAddress() {
		return srvProperties.getAddress();
	}

	@Override
	public int getHttpPort() {
		return srvProperties.getPort();
	}

	@Override
	public boolean enableCompression() {
		return srvProperties.isCompressed();
	}

	@Override
	public String getCertFile() {
		return srvProperties.getCertFile();
	}

	@Override
	public String getCertKey() {
		return srvProperties.getCertKey();
	}
	
	@Override
	public DBType getDBType() {
		return dbProperties.getType();
	}

	@Override
	public String getDBHost() {
		return dbProperties.getHost();
	}

	@Override
	public String getDBName() {
		return dbProperties.getName();
	}

	@Override
	public String getDBUsername() {
		return dbProperties.getUsername();
	}

	@Override
	public String getDBPassword() {
		return dbProperties.getPassword();
	}

	@Override
	public String getDBPath() {
		return dbProperties.getPath();
	}
}
