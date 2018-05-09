/*
 * ClassWithSamplePrototypes.java
 *
 * Created on 2018-05-08, 7:12
 */
package com.marcnuri.demo.springbeanscopes.singletonprototype;

import com.marcnuri.demo.springbeanscopes.Sample;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2018-05-08.
 */
public class ClassWithSamplePrototypes implements ApplicationContextAware {

//**************************************************************************************************
//  Fields
//**************************************************************************************************
	private final Sample autowiredSample;
	private final ObjectFactory<Sample> sampleObjectFactory;
	private ApplicationContext applicationContext;

//**************************************************************************************************
//  Constructors
//**************************************************************************************************
	@Autowired
	public ClassWithSamplePrototypes(Sample autowiredSample, ObjectFactory<Sample> sampleObjectFactory) {
		this.autowiredSample = autowiredSample;
		this.sampleObjectFactory = sampleObjectFactory;
	}

//**************************************************************************************************
//  Abstract Methods
//**************************************************************************************************

//**************************************************************************************************
//  Overridden Methods
//**************************************************************************************************
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

//**************************************************************************************************
//  Other Methods
//**************************************************************************************************

//**************************************************************************************************
//  Getter/Setter Methods
//**************************************************************************************************
	public Sample getAutowiredSample() {
		return autowiredSample;
	}

	@Lookup
	public Sample getSampleUsingLookup() {
		return null;
	}

	public Sample getSampleUsingObjectFactory() {
		return sampleObjectFactory.getObject();
	}

	public Sample getSampleUsingApplicationContext() {
		return applicationContext.getBean(Sample.class);
	}

//**************************************************************************************************
//  Static Methods
//**************************************************************************************************

//**************************************************************************************************
//  Inner Classes
//**************************************************************************************************

}
