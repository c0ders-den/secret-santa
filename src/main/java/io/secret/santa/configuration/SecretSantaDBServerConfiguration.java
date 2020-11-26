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

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Arpan Mukhopadhyay
 *
 */
@Getter
@Setter
@ToString
public class SecretSantaDBServerConfiguration {

	@JsonProperty("type")
	private DBType type;
	
	@JsonProperty("host")
	private String host;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("user")
	private String username;
	
	@JsonProperty("password")
	private String password;
	
	@JsonProperty("path")
	private String path;
}
