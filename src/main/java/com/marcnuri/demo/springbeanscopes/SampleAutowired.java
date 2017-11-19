/*
 * SampleAutowired.java
 *
 * Created on 2017-11-15, 7:21
 */
package com.marcnuri.demo.springbeanscopes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2017-11-15.
 */
public class SampleAutowired implements Serializable {

	private static final long serialVersionUID = 8071922940684168355L;

//**************************************************************************************************
//  Fields
//**************************************************************************************************
	protected static final String BEAN_NAME_FOR_AUTOWIRED_PROTOTYPE = "sampleAutowiredPrototype";

	private final UUID uuid;

	@Autowired
	@Qualifier(BEAN_NAME_FOR_AUTOWIRED_PROTOTYPE)
	private Sample sampleAutowiredPrototype;

//**************************************************************************************************
//  Constructors
//**************************************************************************************************
	public SampleAutowired() {
		this.uuid = UUID.randomUUID();
	}

//**************************************************************************************************
//  Abstract Methods
//**************************************************************************************************

//**************************************************************************************************
//  Overridden Methods
//**************************************************************************************************
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SampleAutowired that = (SampleAutowired) o;

		if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;
		return sampleAutowiredPrototype != null ? sampleAutowiredPrototype.equals(that.sampleAutowiredPrototype) : that.sampleAutowiredPrototype == null;
	}

	@Override
	public int hashCode() {
		int result = uuid != null ? uuid.hashCode() : 0;
		result = 31 * result + (sampleAutowiredPrototype != null ? sampleAutowiredPrototype.hashCode() : 0);
		return result;
	}

//**************************************************************************************************
//  Other Methods
//**************************************************************************************************

//**************************************************************************************************
//  Getter/Setter Methods
//**************************************************************************************************
	public UUID getUuid() {
		return uuid;
	}

	public Sample getSampleAutowiredPrototype() {
		return sampleAutowiredPrototype;
	}

//**************************************************************************************************
//  Static Methods
//**************************************************************************************************

//**************************************************************************************************
//  Inner Classes
//**************************************************************************************************

}
