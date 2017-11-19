/*
 * SampleController.java
 *
 * Created on 2017-11-15, 7:40
 */
package com.marcnuri.demo.springbeanscopes;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2017-11-15.
 */
@RestController()
@RequestScope
public class RequestScopedController extends AbstractController{

//**************************************************************************************************
//  Fields
//**************************************************************************************************

//**************************************************************************************************
//  Constructors
//**************************************************************************************************

//**************************************************************************************************
//  Abstract Methods
//**************************************************************************************************

//**************************************************************************************************
//  Overridden Methods
//**************************************************************************************************

//**************************************************************************************************
//  Other Methods
//**************************************************************************************************
	@GetMapping(AbstractController.REQUEST_SCOPE_ENDPOINT)
	public String getUuid() {
		return super.getUuid();
	}

//**************************************************************************************************
//  Getter/Setter Methods
//**************************************************************************************************

//**************************************************************************************************
//  Static Methods
//**************************************************************************************************

//**************************************************************************************************
//  Inner Classes
//**************************************************************************************************

}