package com.bridgelabz.bookstoreapi.service;

import com.bridgelabz.bookstoreapi.dto.AdminDto;
import com.bridgelabz.bookstoreapi.dto.AdminPasswordResetDto;
import com.bridgelabz.bookstoreapi.dto.LoginDTO;
import com.bridgelabz.bookstoreapi.entity.Admin;

public interface AdminService {
	Admin login(LoginDTO adminLoginDto);

	boolean sendLinkForPassword(String email);

	boolean resetAdminPassword(AdminPasswordResetDto resetDto,String token);

	boolean verifyEmail(String token);

	boolean register(AdminDto adminDto);

	boolean verifyBook(String booktoken,String token);
}
