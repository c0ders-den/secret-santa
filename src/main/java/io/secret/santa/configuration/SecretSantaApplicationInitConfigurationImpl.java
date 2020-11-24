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
	
	/**
	 * 
	 * @param args
	 */
	public SecretSantaApplicationInitConfigurationImpl(ApplicationArguments args) {
		long start = System.nanoTime();
		loadProperties(args);
		long tt = System.nanoTime() - start;
		logger.info("Configuration loaded in {} ms", TimeUnit.MILLISECONDS.convert(tt, TimeUnit.NANOSECONDS));
	}
	
	/**
	 * Loads the property from the given argument
	 * 
	 * @param args the argument
	 */
	private void loadProperties(ApplicationArguments args) {
		List<String> configFileOption = args.getOptionValues("config");
		if (null == configFileOption || configFileOption.size() == 0) {
			loadDefaultProperties();
			return;
		}
		String configFileName = configFileOption.get(0);
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
			SecretSantaConfigTemplate configTemplate = mapper.readValue(configFile, SecretSantaConfigTemplate.class);
			this.serverHost = configTemplate.getServer().getHost();
			this.serverPort = configTemplate.getServer().getPort();
		} catch (Exception e) {
			throw new ConfigParsingException("Can not parse file at " + configFile.getAbsolutePath(), e);
		}
		loadDBProperties();
	}
	
	/**
	 * Loads the default properties for host and port
	 */
	private void loadDefaultProperties() {
		this.serverHost = "0.0.0.0";
		this.serverPort = 3000;
		loadDBProperties();
	}
	
	/**
	 * Loads the DB property details
	 */
	private void loadDBProperties() {
		this.dbHost = "127.0.0.1";
		this.dbPort = 0;
		this.dbUser = "";
		this.dbPass = "";
	}
}
