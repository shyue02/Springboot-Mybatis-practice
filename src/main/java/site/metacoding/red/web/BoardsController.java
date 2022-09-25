package site.metacoding.red.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.users.Users;

@RequiredArgsConstructor
@Controller
public class BoardsController {

	private final HttpSession session;		//스프링이 서버시작시에 IoC 컨테이너에 보관함. / final - @RequiredArgsConstructor
	// @PostMapping("/boards/{id}/delete")
	// @PostMapping("/boards/{id}/update")
	
	
	@GetMapping({"/", "/boards"})
	public String getBoardList() {
		return "boards/main";
	}
	
	@GetMapping("/boards/{id}")
	public String getBoardList(@PathVariable Integer id) {
		return "boards/detail";
	}
	
	@GetMapping("/boards/writeForm")		//인증체크
	public String writeForm() {
		Users principal = (Users) session.getAttribute("principal");		//getAttribute 해당요소에 지정된 값을 반환 
		if(principal == null) {
			return "redirecct:/loginForm";
		}else {
			return "boards/writheForm";
		}
	}
}
