package com.gupaoedu.security.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

/**
 * 让每一个人的职业生涯不留遗憾
 *
 * @author 波波老师【咕泡学院】
 */
@RestController
public class UserController {

	// @PreAuthorize(value = "hasRole('ROOT')")
	// @RolesAllowed(value = {"ROOT"})
	@Secured(value = { "ROLE_ROOT" })
	@GetMapping("/query")
	public String query() {
		return "query ...";
	}

	// @RolesAllowed(value = {"test"})
	// @PreAuthorize(value = "hasRole('TEST')")
	@Secured(value = "test")
	@GetMapping("/save")
	public String save() {
		return "save ... ";
	}

}
