package com.bridgelabz.bookstoreapi.constants;

public class Constants {
	
	private Constants() {}
	
	public static final String EXCHANGE_NAME = "mail-exchange";
	public static final String QUEUE_NAME = "mail-queue";
	public static final String ROUTING_KEY = "mail-sender";
	
	public static final String REGISTRATION_STATUS = "Registration Confirmation";
	public static final String REGISTRATION_MESSAGE = ", you have successfully Registrered to our website\nPlease click on below link to verify:\n";
	public static final String VERIFICATION_LINK = "http://localhost:8080/users/registration/verify/";
	public static final String RESET_MSG = "Click On the below link to reset your password";

	public static final String RESET_PASSWORD_LINK = "Using below link reset your password\nhttp://localhost:3000/resetpassword/";
	public static final String ADMIN_RESET_PASSWORD_LINK = "Using below link reset your password\nhttp://localhost:3000/admin/resetpassword/";
	public static final String ADMIN_VERIFICATION_LINK = "http://localhost:8080/admin/registration/verify/" ;

	public static final String USER_RESET_PASSWORD_LINK = "Using below link reset your password\nhttp://localhost:4200/users/resetpassword/";
	
	public static final String INDEX = "books";
	public static final String TYPE = "_doc";
}
