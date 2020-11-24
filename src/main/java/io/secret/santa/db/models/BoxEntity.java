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

import java.util.List;

import javax.persistence.Column;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.RandomStringUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents the user box.
 * 
 * @author Arpan Mukhopadhyay
 *
 */
@Getter
@Setter
public class BoxEntity extends BaseEntity {

	private static final long serialVersionUID = 8308518335693466222L;

	@Getter
	@Column(name = "box_id")
	private String id = RandomStringUtils.randomAlphabetic(11, 19);
	@Column(name = "capacity")
	private int capacity = 5;
	@OneToMany
	private List<ItemEntity> items;
}
