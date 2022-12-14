package kr.inhatc.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

//첨부파일과 관련된 자동 구성을 사용하지 않도록 설정
@SpringBootApplication(exclude = {MultipartAutoConfiguration.class})
@ComponentScan(basePackages={"kr.inhatc.spring"})
public class MyProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyProjectApplication.class, args);
	}

}
