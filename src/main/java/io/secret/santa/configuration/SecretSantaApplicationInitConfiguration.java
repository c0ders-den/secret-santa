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

import org.springframework.stereotype.Component;

/**
 * @author Arpan Mukhopadhyay
 *
 */
@Component
public interface SecretSantaApplicationInitConfiguration {

	String OPTIONS_CONFIG_FILE = "config";
	String DEFAULT_CONFIG_FILE = "config/default.yaml";
	Integer DEFAULT_HTTP_PORT = 3000;
	String DEFAULT_HTTP_ADDR = "127.0.0.1";
	
	/**
	 * 
	 * @return
	 */
	AppMode getAppMode();
	
	/**
	 * 
	 * @return
	 */
	String getName();
	
	/**
	 * 
	 * @return
	 */
	String getAppBase();
	
	/**
	 * 
	 * @return
	 */
	String getData();
	
	/**
	 * 
	 * @return
	 */
	String getLogs();
	
	/**
	 * 
	 * @return
	 */
	String getProtocol();
	
	/**
	 * 
	 * @return
	 */
	String getHttpAddress();
	
	/**
	 * 
	 * @return
	 */
	int getHttpPort();
	
	/**
	 * 
	 * @return
	 */
	boolean enableCompression();
	
	/**
	 * 
	 * @return
	 */
	String getCertFile();
	
	/**
	 * 
	 * @return
	 */
	String getCertKey();
	
	/**
	 * 
	 * @return
	 */
	DBType getDBType();
	
	/**
	 * 
	 * @return
	 */
	String getDBHost();
	
	/**
	 * 
	 * @return
	 */
	String getDBName();
	
	/**
	 * 
	 * @return
	 */
	String getDBUsername();
	
	/**
	 * 
	 * @return
	 */
	String getDBPassword();
	
	/**
	 * 
	 * @return
	 */
	String getDBPath();
}
