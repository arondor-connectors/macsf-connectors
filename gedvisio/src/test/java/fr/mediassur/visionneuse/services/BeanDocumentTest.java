package fr.mediassur.visionneuse.services;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import fr.mediassur.visionneuse.services.BeanDocument;


public class BeanDocumentTest {

	private static final String USER = "User";
	private static final String ACTION_UNIQUE = "UNIQUE";
	private static final String ACTION_MULTI = "MULTI";
	private static final String REFERENCE = "123456789";
	private static final String USER_ERR = "userErr";
	private static final String USER_DB2 = "userDB2";
	private static final String USER_LIBMESS = "LibMess";
	

	ServiceVisionneuseMock service;

	BeanDocument beanDocIn;
	BeanDocument beanDocOut;

	@Before
	public void init() {
		service = new ServiceVisionneuseMock();
		beanDocIn = new BeanDocument();


	}

	@Test
	public void setUser(){
		beanDocIn.setCo_User(USER);
		beanDocOut = service.execute(beanDocIn);
		assertEquals(USER, beanDocOut.getCo_User().trim());
	}

	@Test
	public void setActionUnique(){
		beanDocIn.setCo_Action(ACTION_UNIQUE);
		beanDocOut = service.execute(beanDocIn);
		assertEquals(ACTION_UNIQUE, beanDocOut.getCo_Action().trim());
	}

	@Test
	public void setActionMulti(){
		beanDocIn.setCo_Action(ACTION_MULTI);
		beanDocOut = service.execute(beanDocIn);
		assertEquals(ACTION_MULTI, beanDocOut.getCo_Action().trim());
	}


	@Test
	public void setReference(){
		beanDocIn.setReferenceOrigine(REFERENCE);
		beanDocOut = service.execute(beanDocIn);
		assertEquals(REFERENCE, beanDocOut.getReferenceOrigine().trim());
	}



	/**
	 * "ERR" = Erreur du traitement (Fonctionnelle, technique etc..)
	 */
	@Test
	public void typMessErr(){

		beanDocIn.setCo_User(USER_ERR);
		beanDocOut = service.execute(beanDocIn);
		assertEquals("ERR", beanDocOut.getBeanECI().getCo_Typmess().trim()); 	
	}


	/**
	 * "DB2" = Erreur DB2 du traitement
	 */
	@Test
	public void typMessDB2(){

		beanDocIn.setCo_User(USER_DB2);
		beanDocOut = service.execute(beanDocIn);

		assertEquals("DB2", beanDocOut.getBeanECI().getCo_Typmess().trim()); 	
	}

	/**
	 * "VAL" = Traitement Complet .
	 */
	@Test
	public void typMessVal(){

		beanDocOut = service.execute(beanDocIn);
		assertEquals("VAL", beanDocOut.getBeanECI().getCo_Typmess().trim()); 	
	}

	@Test
	public void Co_Libmess(){
		
		beanDocIn.setCo_User(USER_LIBMESS);
		beanDocOut = service.execute(beanDocIn);	
		assertEquals(USER_LIBMESS, beanDocOut.getBeanECI().getCo_Libmess().trim());
	}

	


}