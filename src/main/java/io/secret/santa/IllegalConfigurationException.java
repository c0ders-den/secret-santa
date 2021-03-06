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
package io.secret.santa;

/**
 * @author Arpan Mukhopadhyay
 *
 */
public class IllegalConfigurationException extends RuntimeException {

	private static final long serialVersionUID = 5004781920274167492L;
	
	/**
	 * 
	 */
	public IllegalConfigurationException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public IllegalConfigurationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public IllegalConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public IllegalConfigurationException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public IllegalConfigurationException(Throwable cause) {
		super(cause);
	}
}
