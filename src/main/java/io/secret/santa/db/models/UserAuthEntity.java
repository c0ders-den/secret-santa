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

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.envers.Audited;
import org.springframework.stereotype.Indexed;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Arpan Mukhopadhyay
 *
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Indexed
@Table(name = "auth")
@Access(value = AccessType.FIELD)
@Audited
public class UserAuthEntity extends BaseEntity {

	private static final long serialVersionUID = 6704658936537322676L;
	
	@Getter
	@Column(name = "auth_id")
	private String authId = RandomStringUtils.randomAlphanumeric(11, 19);
	
	@Column(name = "auth_email", unique = true, nullable = false)
	private String authEmail;
	
	@Column(name = "auth_password", nullable = false)
	private String authPass;
	
	@OneToOne(mappedBy = "auth")
	private ParticipantEntity participant;
}
