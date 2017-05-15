package fr.mediassur.visionneuse.services;

import fr.mediassur.visionneuse.services.BeanDocument;

public class ServiceVisionneuseMock {


	private static final String USER_ERR = "userErr";
	private static final String USER_DB2 = "userDB2";
	private static final String USER_LIBMESS = "LibMess";

	public BeanDocument execute(BeanDocument beanDocIn) {

		BeanDocument beanDocOut = beanDocIn;


		if(beanDocIn.getCo_User().trim().equals(USER_ERR)){
			beanDocOut.getBeanECI().setCo_Typmess("ERR");
		}

		else if(beanDocIn.getCo_User().equals(USER_DB2) ){
			beanDocIn.getBeanECI().setCo_Typmess("DB2");
		}

		else {
			beanDocIn.getBeanECI().setCo_Typmess("VAL");
		}



		if(beanDocIn.getCo_User().equals(USER_LIBMESS)){
			beanDocOut.getBeanECI().setCo_Libmess(USER_LIBMESS);
		}
		

		return beanDocOut;
	}


}
