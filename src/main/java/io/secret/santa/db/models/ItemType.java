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
package io.secret.santa.db.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This enum represents the item type.
 * 
 * @author Arpan Mukhopadhyay
 *
 */
@AllArgsConstructor
@Getter
public enum ItemType {

	FREE(0), COMMON(1), SPECIAL(2), EXCLUSIVE(3);  
	
	private int type;
}
