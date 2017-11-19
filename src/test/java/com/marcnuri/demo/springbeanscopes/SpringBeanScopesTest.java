/*
 * SpringBeanScopesTest.java
 *
 * Created on 2017-11-15, 6:57
 */
package com.marcnuri.demo.springbeanscopes;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.marcnuri.demo.springbeanscopes.SampleAutowired.BEAN_NAME_FOR_AUTOWIRED_PROTOTYPE;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2017-11-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		classes= {
				SpringBeanScopesTest.class,
				SingletonScopedController.class,
				RequestScopedController.class,
				SessionScopedController.class
		})
@Configuration
@WebAppConfiguration
public class SpringBeanScopesTest {

//**************************************************************************************************
//  Fields
//**************************************************************************************************
	private static final String SINGLETON_BEAN_SAMPLE_NAME = "sample";
	private static final String SINGLETON_ANNOTATED_BEAN_SAMPLE_NAME = "sampleAnnotated";
	private static final String PROTOTYPE_BEAN_SAMPLE_NAME = "samplePrototype";
	private static final String SINGLETON_BEAN_SAMPLE_AUTOWIRED_NAME = "sampleAutowired";

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private SingletonScopedController singletonScopedController;

	@Autowired
	private RequestScopedController requestScopedController;

	@Autowired
	private SessionScopedController sessionScopedController;


	private MockMvc mockMvc;


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
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders
				.standaloneSetup(singletonScopedController, requestScopedController, sessionScopedController)
				.build();
	}

	@After
	public void tearDown() {
		mockMvc = null;
	}

	@Bean(name = SINGLETON_BEAN_SAMPLE_NAME)
	public Sample sample() {
		return new Sample();
	}

	@Bean(name = SINGLETON_ANNOTATED_BEAN_SAMPLE_NAME)
	@Scope(SCOPE_SINGLETON)
	public Sample sampleAnnotated() {
		return new Sample();
	}

	@Bean(name = PROTOTYPE_BEAN_SAMPLE_NAME)
	@Scope(SCOPE_PROTOTYPE)
	@Qualifier(BEAN_NAME_FOR_AUTOWIRED_PROTOTYPE)
	public Sample samplePrototype() {
		return new Sample();
	}

	@Bean(name = SINGLETON_BEAN_SAMPLE_AUTOWIRED_NAME)
	@Scope(SCOPE_SINGLETON)
	public SampleAutowired sampleAutowiredPrototype() {
		return new SampleAutowired();
	}

	@Test
	public void singletonTest_na_shouldBeSameInstance() {
		Sample singleton1 = applicationContext.getBean(SINGLETON_BEAN_SAMPLE_NAME, Sample.class);
		Sample singleton2 = applicationContext.getBean(SINGLETON_BEAN_SAMPLE_NAME, Sample.class);
		Assert.assertEquals(singleton1, singleton2);
		Sample singletonAnnotated1 = applicationContext.getBean(SINGLETON_ANNOTATED_BEAN_SAMPLE_NAME, Sample.class);
		Sample singletonAnnotated2 = applicationContext.getBean(SINGLETON_ANNOTATED_BEAN_SAMPLE_NAME, Sample.class);
		Assert.assertEquals(singletonAnnotated1, singletonAnnotated2);
		Assert.assertNotEquals(singleton1, singletonAnnotated1);
	}

	@Test
	public void singletonScopedController_na_shouldReturnSameValues() throws Exception {
		String response1 = mockMvc.perform(get(AbstractController.SINGLETON_SCOPE_ENDPOINT))
				.andReturn().getResponse().getContentAsString();
		String response2 = mockMvc.perform(get(AbstractController.SINGLETON_SCOPE_ENDPOINT))
				.andReturn().getResponse().getContentAsString();
		Assert.assertEquals(response1, response2);
	}

	@Test
	public void prototypeTest_na_shouldBeDifferentInstance() {
		Sample prototype1 = applicationContext.getBean(PROTOTYPE_BEAN_SAMPLE_NAME, Sample.class);
		Sample prototype2 = applicationContext.getBean(PROTOTYPE_BEAN_SAMPLE_NAME, Sample.class);
		Assert.assertNotEquals(prototype1, prototype2);
	}

	@Test
	public void singletonWithAutowiredPrototype_na_shouldBeInstance() {
		SampleAutowired singleton1 = applicationContext.getBean(SINGLETON_BEAN_SAMPLE_AUTOWIRED_NAME, SampleAutowired.class);
		SampleAutowired singleton2 = applicationContext.getBean(SINGLETON_BEAN_SAMPLE_AUTOWIRED_NAME, SampleAutowired.class);
		Assert.assertEquals(singleton1, singleton2);
		Assert.assertEquals(singleton1.getSampleAutowiredPrototype(), singleton2.getSampleAutowiredPrototype());
	}

	@Test
	public void requestScopedController_na_shouldReturnDifferentValues() throws Exception {
		String response1 = mockMvc.perform(get(AbstractController.REQUEST_SCOPE_ENDPOINT))
				.andReturn().getResponse().getContentAsString();
		String response2 = mockMvc.perform(get(AbstractController.REQUEST_SCOPE_ENDPOINT))
				.andReturn().getResponse().getContentAsString();
		Assert.assertNotEquals(response1, response2);
	}

	@Test
	public void sessionScopedController_na_shouldReturnSameValues() throws Exception {
		MockHttpSession session1 = new MockHttpSession();
		MockHttpSession session2 = new MockHttpSession();
		String response1_1 = mockMvc.perform(get(AbstractController.SESSION_SCOPE_ENDPOINT).session(session1))
				.andReturn().getResponse().getContentAsString();
		String response1_2 = mockMvc.perform(get(AbstractController.SESSION_SCOPE_ENDPOINT).session(session1))
				.andReturn().getResponse().getContentAsString();
		Assert.assertEquals(response1_1, response1_2);
		String response2_1 = mockMvc.perform(get(AbstractController.SESSION_SCOPE_ENDPOINT).session(session2))
				.andReturn().getResponse().getContentAsString();
		String response2_2 = mockMvc.perform(get(AbstractController.SESSION_SCOPE_ENDPOINT).session(session2))
				.andReturn().getResponse().getContentAsString();
		Assert.assertEquals(response2_1, response2_2);
		Assert.assertNotEquals(response1_1, response2_1);
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
