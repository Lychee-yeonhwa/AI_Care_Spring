package kr.inhatc.spring.user.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.board.entity.Files;
import kr.inhatc.spring.user.entity.Users;

public interface UserService {

	List<Users> userList();

	Users userDetail(String id);

	void userDelete(String id);

	void saveUsers(Users user, MultipartHttpServletRequest multipartHttpServletRequest);

	Files selectFileInfo(int idx, String id);

}
