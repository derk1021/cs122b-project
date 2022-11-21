package com.fabflix.service;

import com.fabflix.entity.Login;
import com.fabflix.exception.InvalidCredentialsException;

public interface CustomerService {
	public boolean loginCustomer(Login login) throws InvalidCredentialsException;

}
