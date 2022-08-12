package kr.inhatc.spring.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {

	@GetMapping("/login/login")
	public void login() {
		log.info("로그인 진행중...");
	}
	
	@GetMapping("/login/accessDenied")
	public void accessDenied() {
		log.info("접근 거부...");
	}
	
	@GetMapping("/login/logout")
	public void logout() {
		log.info("로그아웃...");
	}
}
