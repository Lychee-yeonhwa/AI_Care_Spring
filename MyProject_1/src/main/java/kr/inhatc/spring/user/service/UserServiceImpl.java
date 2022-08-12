package kr.inhatc.spring.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sun.el.stream.Optional;

import kr.inhatc.spring.board.entity.Files;
import kr.inhatc.spring.user.entity.Users;
import kr.inhatc.spring.user.repository.UserRepository;
import kr.inhatc.spring.utils.FileUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private FileUtils fileUtils;

	@Override
	public List<Users> userList() {
		List<Users> list = userRepository.findAllByOrderByIdDesc();
		return list;
	}

	@Override
	public void saveUsers(Users user, MultipartHttpServletRequest multipartHttpServletRequest) {
		List<Files> list = fileUtils.parseFileInfo(multipartHttpServletRequest);
		if(CollectionUtils.isEmpty(list) == false) {
			user.setFileList(list);
		}
		userRepository.save(user);
	}

	@Override
	public Users userDetail(String id) {
		java.util.Optional<Users> optional = userRepository.findById(id);
		if (optional.isPresent()) {
			Users user = optional.get();
			return user;
		} else {
			throw new NullPointerException();
		}
	}

	@Override
	public void userDelete(String id) {
		userRepository.deleteById(id);
	}

	@Override
	public Files selectFileInfo(int idx, String id) {
		Files userFile = userRepository.findUserFile(idx, id);
		return userFile;
	}


}
