package site.metacoding.red.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.boards.BoardsDao;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.web.dto.request.boards.WriteDto;
import site.metacoding.red.web.dto.response.boards.MainDto;

@RequiredArgsConstructor
@Controller
public class BoardsController {

	private final HttpSession session;		//스프링이 서버시작시에 IoC 컨테이너에 보관함. / final - @RequiredArgsConstructor
	private final BoardsDao boardsDao;
	// @PostMapping("/boards/{id}/delete")
	// @PostMapping("/boards/{id}/update")
	
	@PostMapping("/boards")
	public String writeBoards(WriteDto writeDto) {
		//1번 세션에 접근해서 세션값을 확인한다. 그 때 Users로 다운캐스팅하고 키값은 principal로 한다.
		Users principal = (Users) session.getAttribute("principal");
		
		//2번 principal null인지 확인하고 null이면 loginForm 리다이렉션 해준다.
		if(principal == null) {
			return "redirect:/loginForm";
		}
		
		//3번 BoardsDao에 접근해서 insert 메소드를 호출한다.
		// 조건 : dto를 entity로 변환해서 인수로 담아준다.
		// 조건 : entity에는 세션의 principal에 getId가 필요하다.
		boardsDao.insert(writeDto.toEntity(principal.getId()));
		
		return "redirect:/";
	}
	
	@GetMapping({"/", "/boards"})
	public String getBoardList(Model model) {
		List<MainDto> boardList = boardsDao.findAll();
		model.addAttribute("boaradsList", boardList);
		return "boards/main";
	}
	
	@GetMapping("/boards/{id}")
	public String getBoardList(@PathVariable Integer id, Model model) {
		model.addAttribute("boards", boardsDao.findById(id));
		return "boards/detail";
	}
	
	@GetMapping("/boards/writeForm")		//인증체크
	public String writeForm() {
		Users principal = (Users) session.getAttribute("principal");		//getAttribute 해당요소에 지정된 값을 반환 
		if(principal == null) {
			return "redirecct:/loginForm";
		}
		return "boars/writeForm";
	}
}
