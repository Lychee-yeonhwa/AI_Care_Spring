package kr.inhatc.spring.quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.inhatc.spring.quiz.entity.QuestionForm;
import kr.inhatc.spring.quiz.entity.Result;
import kr.inhatc.spring.quiz.service.QuizService;

@Controller
public class Quizcontroller {

	@Autowired
	Result result;
	
	@Autowired
	QuizService qService;
	
	Boolean submitted = false;
	
	@ModelAttribute("result")
	public Result getResult() {
		return result;
	}
	
	@RequestMapping(value ="/quiz/quizmain", method = RequestMethod.GET)
	public String quizmain() {
		// 이동하는 역할
		return "/quiz/quizmain";
	}
	
	@RequestMapping(value ="/quiz/quiz" , method = RequestMethod.POST)
	public String quiz(@RequestParam("username") String username,Model model,RedirectAttributes re) {
		if(username.equals("")) {
			re.addFlashAttribute("warning", "이름을 입력하지 않으셨습니다!");
			return "redirect:/quiz/quizmain";
		}
		
		submitted = false;
		result.setUsername(username);
		QuestionForm qForm = qService.getQuestions();
		model.addAttribute("qForm", qForm);
		
			return "/quiz/quiz";
	}
	
	@RequestMapping(value ="quiz/submit" , method = RequestMethod.POST)
	public String submit(@ModelAttribute("qForm") QuestionForm qForm, Model model) {
		if(!submitted) {
			result.setTotalCorrect(qService.getResult(qForm));
			qService.saveScore(result);
			submitted = true;
		}
		return "/quiz/result";
	}
	
	@RequestMapping(value ="/quiz/score", method = RequestMethod.GET)
	public String score(Model model) {
		List<Result> sList = qService.getTopScore();
		model.addAttribute("sList", sList);
		
		return "/quiz/score";
	}
}
