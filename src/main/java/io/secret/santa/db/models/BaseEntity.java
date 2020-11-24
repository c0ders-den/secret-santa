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

import java.time.OffsetDateTime;
import java.time.ZoneId;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Arpan Mukhopadhyay
 *
 */
@SuppressWarnings("serial")
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Entity {

	@Id
	@GeneratedValue(generator = "uuidGenerator")
	@GenericGenerator(name = "uuidGenerator", strategy = "org.hibernate.id.UUIDGenerator", parameters = {@Parameter(
			name = org.hibernate.id.UUIDGenerator.UUID_GEN_STRATEGY_CLASS, 
			value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
	)})
	@Column(name = "id")
	@JsonIgnore
	@Getter @Setter
	private String id;
	
	@CreationTimestamp
	@Column(name = "created")
	@Getter
	private OffsetDateTime created = OffsetDateTime.now(ZoneId.of("Z"));
	
	@UpdateTimestamp
	@Column(name = "updated")
	@Getter @Setter
	private OffsetDateTime updated;
}
