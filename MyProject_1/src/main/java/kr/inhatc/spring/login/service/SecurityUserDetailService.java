package kr.inhatc.spring.login.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.inhatc.spring.login.security.SecurityUser;
import kr.inhatc.spring.user.entity.Users;
import kr.inhatc.spring.user.repository.UserRepository;

@Service
public class SecurityUserDetailService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Users> result = userRepository.findById(username);
		if(result.isPresent()) {
			Users user = result.get();
			System.out.println("===========>" + user);
			return new SecurityUser(user);
			
		} else {
			throw new UsernameNotFoundException(username+ " 사용자 없음 !!!");
		}
	}

}
