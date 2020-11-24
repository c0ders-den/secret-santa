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
import lombok.Setter;

/**
 * This class represents a user wallet.
 * 
 * @author Arpan Mukhopadhyay
 *
 */
@Getter
@Setter
@Entity
@Indexed
@Table(name = "wallet")
@Access(value = AccessType.FIELD)
@Audited
public class WalletEntity extends BaseEntity {

	private static final long serialVersionUID = 2063490249301211941L;

	@Getter
	@Column(name = "wallet_id")
	private String walletId = RandomStringUtils.randomAlphanumeric(11, 19);
	
	//Secret santa coins. Used to buy common items.
	@Column(name = "coins")
	private int coins = 100;
	//Secret santa currency. Used to buy special items
	@Column(name = "currency")
	private int currency = 0;
	//Secret santa key. Used to buy exclusive items.
	@Column(name = "keys")
	private int keys = 0;
	
	@OneToOne(mappedBy = "wallet")
	private ParticipantEntity participant;
}
