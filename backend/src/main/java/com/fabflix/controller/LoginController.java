package com.fabflix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fabflix.entity.Employee;
import com.fabflix.entity.Login;
import com.fabflix.exception.InvalidCredentialsException;
import com.fabflix.service.CustomerService;
import com.fabflix.service.EmployeeService;

@Controller
@CrossOrigin("*")
@RequestMapping("/api")
public class LoginController {

	@Autowired
	CustomerService customerService;

	@Autowired
	EmployeeService employeeService;

	@PostMapping("/login")
	public ResponseEntity<Boolean> loginUser(@RequestBody Login login) throws InvalidCredentialsException {
		return ResponseEntity.ok(customerService.loginCustomer(login));
	}

	@PostMapping("/login/employee")
	public ResponseEntity<Boolean> loginEmployee(@RequestBody Employee employee) throws InvalidCredentialsException {
		return ResponseEntity.ok(employeeService.loginEmployee(employee));
	}
}
