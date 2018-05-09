/*
 * SingletonWithPrototypesTest.java
 *
 * Created on 2018-05-08, 7:34
 */
package com.marcnuri.demo.springbeanscopes.singletonprototype;

import com.marcnuri.demo.springbeanscopes.Sample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2018-05-08.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		classes= {
				SingletonWithLookupPrototype.class,
				SingletonWithPrototypesTest.class
		})
public class SingletonWithPrototypesTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Bean
	@Scope(SCOPE_PROTOTYPE)
	public Sample sample(){
		return new Sample();
	}

	@Bean
	@Scope(SCOPE_SINGLETON)
	public ClassWithSamplePrototypes singletonWithSamplePrototypes(Sample autowiredSample, ObjectFactory<Sample> sampleObjectFactory) {
		return new ClassWithSamplePrototypes(autowiredSample, sampleObjectFactory);
	}

	@Test
	public void getAutowiredSample_na_shouldBeSameInstance() {
		// Given
		ClassWithSamplePrototypes singleton1 = applicationContext.getBean(ClassWithSamplePrototypes.class);
		ClassWithSamplePrototypes singleton2 = applicationContext.getBean(ClassWithSamplePrototypes.class);

		// When
		Sample autowiredSample1 = singleton1.getAutowiredSample();
		Sample autowiredSample2 = singleton2.getAutowiredSample();

		// Then
		assertEquals(singleton1, singleton2);
		assertEquals(autowiredSample1, autowiredSample2);
		assertEquals(autowiredSample1.getUuid(), autowiredSample2.getUuid());
	}

	@Test
	public void getSampleUsingLookupInRegularBean_na_shouldBeNull() {
		// Given
		ClassWithSamplePrototypes singleton1 = applicationContext.getBean(ClassWithSamplePrototypes.class);
		ClassWithSamplePrototypes singleton2 = applicationContext.getBean(ClassWithSamplePrototypes.class);

		// When
		Sample lookedupSample1 = singleton1.getSampleUsingLookup();
		Sample lookedupSample2 = singleton2.getSampleUsingLookup();

		// Then
		assertEquals(singleton1, singleton2);
		assertNull(lookedupSample1);
		assertNull(lookedupSample2);
	}

	@Test
	public void getSampleUsingLookupInComponent_na_shouldBeDifferent() {
		// Given
		SingletonWithLookupPrototype singleton1 = applicationContext.getBean(SingletonWithLookupPrototype.class);
		SingletonWithLookupPrototype singleton2 = applicationContext.getBean(SingletonWithLookupPrototype.class);

		// When
		Sample lookedupSample1 = singleton1.getSampleUsingLookup();
		Sample lookedupSample2 = singleton2.getSampleUsingLookup();

		// Then
		assertEquals(singleton1, singleton2);
		assertNotNull(singleton1);
		assertNotNull(singleton2);
		assertNotEquals(lookedupSample1, lookedupSample2);
		assertNotEquals(lookedupSample1.getUuid(), lookedupSample2.getUuid());
	}

	@Test
	public void getSampleUsingObjectFactory_na_shouldBeDifferent() {
		// Given
		ClassWithSamplePrototypes singleton1 = applicationContext.getBean(ClassWithSamplePrototypes.class);
		ClassWithSamplePrototypes singleton2 = applicationContext.getBean(ClassWithSamplePrototypes.class);

		// When
		Sample objectFactorySample1 = singleton1.getSampleUsingObjectFactory();
		Sample objectFactorySample2 = singleton2.getSampleUsingObjectFactory();

		// Then
		assertEquals(singleton1, singleton2);
		assertNotNull(singleton1);
		assertNotNull(singleton2);
		assertNotEquals(objectFactorySample1, objectFactorySample2);
		assertNotEquals(objectFactorySample1.getUuid(), objectFactorySample2.getUuid());
	}

	@Test
	public void getSampleUsingApplicationContext_na_shouldBeDifferent() {
		// Given
		ClassWithSamplePrototypes singleton1 = applicationContext.getBean(ClassWithSamplePrototypes.class);
		ClassWithSamplePrototypes singleton2 = applicationContext.getBean(ClassWithSamplePrototypes.class);

		// When
		Sample applicationContextSample1 = singleton1.getSampleUsingApplicationContext();
		Sample applicationContextSample2 = singleton2.getSampleUsingApplicationContext();

		// Then
		assertEquals(singleton1, singleton2);
		assertNotNull(singleton1);
		assertNotNull(singleton2);
		assertNotEquals(applicationContextSample1, applicationContextSample2);
		assertNotEquals(applicationContextSample1.getUuid(), applicationContextSample2.getUuid());
	}
}
