package kr.inhatc.spring.user.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.board.entity.Files;
import kr.inhatc.spring.user.entity.Users;
import kr.inhatc.spring.user.service.UserService;

@Controller // html 파일로 넘겨줌
public class UserController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@RequestMapping("/")
	public String hello() {
		return "index";
	}
	
	//GET(read), POST(create), PUT(update), DELETE(delete)
	
	@GetMapping
	@RequestMapping(value = "/user/userList", method = RequestMethod.GET)
	public String userList(Model model) {
		List<Users> list = userService.userList();
		model.addAttribute("list", list);
		return "user/userList";
	}
	
	@RequestMapping(value = "/user/userInsert", method = RequestMethod.GET)
	public String userWrite(Model model) {
		List<String> enabledList= new ArrayList<>();
		enabledList.add("가능");
		enabledList.add("불가능");
		
		List<String> authorityList = new ArrayList<>();
		authorityList.add("ROLE_GUEST");
		authorityList.add("ROLE_MEMBER");
		authorityList.add("ROLE_ADMIN");
		
		Map<String, List<String>> map= new HashMap<>();
		map.put("enabledList", enabledList);
		map.put("authorityList", authorityList);
		
	
		model.addAttribute("map", map);
		
		return "user/userWrite";
	}
	
	@RequestMapping(value = "/user/userMake", method = RequestMethod.GET)
	public String userMake(Model model) {
		List<String> enabledList= new ArrayList<>();
		enabledList.add("가능");
		enabledList.add("불가능");
		
		List<String> authorityList = new ArrayList<>();
		authorityList.add("ROLE_GUEST");
		authorityList.add("ROLE_MEMBER");
		authorityList.add("ROLE_ADMIN");
		
		Map<String, List<String>> map= new HashMap<>();
		map.put("enabledList", enabledList);
		map.put("authorityList", authorityList);
		
	
		model.addAttribute("map", map);
		
		return "user/userMake";
	}
	
	@RequestMapping(value = "/user/userMake", method = RequestMethod.POST)
	public String userMake(Users user, MultipartHttpServletRequest multipartHttpServletRequest) {
		if(user != null) {
			System.out.println("변경 전 : " + user.getPw());
			String pw = encoder.encode(user.getPw());
			System.out.println("변경 후 : " + user.getPw());
			user.setPw(pw);
			userService.saveUsers(user, multipartHttpServletRequest);
		}
		return "redirect:/user/userList";
	}
	
	@RequestMapping(value = "/user/userInsert", method = RequestMethod.POST)
	public String userInsert(Users user, MultipartHttpServletRequest multipartHttpServletRequest) {
		if(user != null) {
			System.out.println("변경 전 : " + user.getPw());
			String pw = encoder.encode(user.getPw());
			System.out.println("변경 후 : " + user.getPw());
			user.setPw(pw);
			userService.saveUsers(user, multipartHttpServletRequest);
		}
		return "redirect:/user/userList";
	}
	
	@RequestMapping(value = "/user/userDetail/{id}", method = RequestMethod.GET)
	public String userDetail(@PathVariable("id") String id, Model model) {
		Users user = userService.userDetail(id);
		model.addAttribute("user", user);
		return "/user/userDetail";
	}
	
	@RequestMapping(value = "/user/userUpdate/{id}", method = RequestMethod.POST)
	public String userUpdate(@PathVariable("id") String id, Users user) {
		
		// 아이디 설정
		user.setId(id);
		
		userService.saveUsers(user, null);
		return "redirect:/user/userList";
	}
	
	@RequestMapping(value = "/user/downloadUserFile", method = RequestMethod.GET)
	public void downloadUserFile(@RequestParam("idx") int idx, @RequestParam("id") String id,
			HttpServletResponse response) throws Exception {

		Files userFile = userService.selectFileInfo(idx, id);

		if (ObjectUtils.isEmpty(userFile) == false) {
			String fileName = userFile.getOriginalFileName();
			byte[] files = FileUtils.readFileToByteArray(new File(userFile.getStoredFilePath()));

			// response 헤더에 설정
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			response.setHeader("Content-Dispositon",
					"attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";"); // \" 문자열안에 "넣기
			response.setHeader("Content-Transfer-Encoding", "binary");

			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
		
	}
	
	@RequestMapping(value = "/user/userDelete/{id}", method = RequestMethod.GET)
	public String userDelete(@PathVariable("id") String id) {
	
		userService.userDelete(id);
		return "redirect:/user/userList";
	}
	
}
