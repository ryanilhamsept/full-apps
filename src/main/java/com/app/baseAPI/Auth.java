package com.app.baseAPI;

import com.app.actions.HttpOperation;
import com.app.restassuredFuntions.API;

public class Auth extends API {

    public Auth(){}

	/**
	 * Author: @regiewby
	 * used for create token
	 * @param userName username of application
	 * @param passWord password of application
	 */
    private void createToken(String userName, String passWord) {
		initBase("Host");
		init("/auth", HttpOperation.POST);
		setHeader("Content-Type","application/json");
		setBody("{ \"username\" : \""+userName+"\", \"password\" : \""+passWord+"\"}");
	}

	/**
	 * Author: @regiewby
	 * used for get login token
	 * @param userName username of application
	 * @param passWord password of application
	 * @return String token
	 */
	public String getLoginToken(String userName, String passWord) {
		createToken(userName, passWord);
		return callIt();
	}
}
