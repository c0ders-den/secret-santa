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
package io.secret.santa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import io.secret.santa.view.models.RegistrationModel;
import lombok.extern.log4j.Log4j2;

/**
 * @author Arpan Mukhopadhyay
 *
 */
@Controller
@Log4j2
public class UserAuthController {

	@GetMapping(path = "/register.html")
	public String openRegistrationPage(Model model) {
		model.addAttribute("registrationModel", new RegistrationModel());
		return "registration";
	}
	
	@PostMapping(path = "/register.html")
	public String registerEmail(@ModelAttribute RegistrationModel registrationModel, Model model) {
		return "registration";
	}
	
	@GetMapping(path = "/login.html")
	public String openLoginPage() {
		return "login";
	}
}
