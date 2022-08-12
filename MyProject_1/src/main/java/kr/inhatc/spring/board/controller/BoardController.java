package kr.inhatc.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.board.entity.Board;
import kr.inhatc.spring.board.entity.Files;
import kr.inhatc.spring.board.service.BoardService;

//@RestController 결과물을 바로 받아옴
@Controller // html 파일로 넘겨줌
public class BoardController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BoardService boardService;

//	@RequestMapping("/")
//	public String hello() {
//		return "redirect:/board/boardList";
//	}

	@RequestMapping(value = "/test/testPage", method = RequestMethod.GET)
	public String testPage() {
		// 이동하는 역할
		return "/test/testPage";
	}

	// GET(read), POST(create), PUT(update), DELETE(delete)

	@GetMapping
	@RequestMapping(value = "/board/boardList", method = RequestMethod.GET)
	public String boardList(@PageableDefault Pageable pageable, Model model) {

		Page<Board> boardList = boardService.getBoardList(pageable);
		model.addAttribute("boardList", boardList);

		log.debug(
				"총 element 수 : {}, 전체 page 수 : {}, 페이지에 표시할 element 수 : {}, "
						+ "현재 페이지 index : {}, 현재 페이지의 element 수 : {}",
				boardList.getTotalElements(), boardList.getTotalPages(), boardList.getSize(), boardList.getNumber(),
				boardList.getNumberOfElements());

		// 서비스 로직
//		List<Board> list = boardService.boardList();
//
//		model.addAttribute("list", list); // 웹페이지로 들고감 (상자 같은 개념)
		// model.addAttribute("name", "홍길동"); // 웹페이지로 들고감 (상자 같은 개념)

		// 뷰어 이동
		return "board/boardList";
	}

	@GetMapping
	@RequestMapping(value = "/board/boardWrite", method = RequestMethod.GET)
	public String boardWrite() {
		// 이동하는 역할
		return "/board/boardWrite";
	}

	@RequestMapping(value = "/game/posegame", method = RequestMethod.GET)
	public String posegame() {
		// 이동하는 역할
		return "/game/posegame";
	}

	@RequestMapping(value = "/game/quizgame", method = RequestMethod.GET)
	public String quizgame() {
		// 이동하는 역할
		return "/game/quizgame";
	}

	@RequestMapping(value = "/board/boardInsert", method = RequestMethod.POST)
	public String boardInsert(Board board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		System.out.println("==========>" + board);
		int hitCnt = board.getHitCnt();

		boardService.saveBoard(board, multipartHttpServletRequest, hitCnt);
		return "redirect:/board/boardList"; // redirect
	}

	@RequestMapping(value = "/board/boardDetail/{boardIdx}", method = RequestMethod.GET)
	public String boardDetail(@PathVariable("boardIdx") int boardIdx, Model model) throws Exception {
		Board board = boardService.boardDetail(boardIdx);
		int hitCnt = board.getHitCnt();
		boardService.saveBoard(board, null, hitCnt + 1); // 조회수 증가
		model.addAttribute("board", board);
		return "/board/boardDetail";
	}

	@RequestMapping(value = "/board/boardDetail2/{boardIdx}", method = RequestMethod.GET)
	public String boardDetail2(@PathVariable("boardIdx") int boardIdx, Model model) throws Exception {
		Board board = boardService.boardDetail(boardIdx);
		int hitCnt = board.getHitCnt();
		model.addAttribute("board", board);
		return "/board/boardDetail2";
	}

	@RequestMapping(value = "/board/boardUpdate/{boardIdx}", method = RequestMethod.POST)
	public String boardUpdate(@PathVariable("boardIdx") int boardIdx, Board board) throws Exception {
		int hitCnt = board.getHitCnt();
		boardService.saveBoard(board, null, hitCnt);
		return "redirect:/board/boardList";
	}

	@RequestMapping(value = "/board/boardDelete/{boardIdx}", method = RequestMethod.POST)
	public String boardDelete(@PathVariable("boardIdx") int boardIdx) {

		boardService.boardDelete(boardIdx);
		return "redirect:/board/boardList";
	}

	@RequestMapping(value = "/board/downloadBoardFile", method = RequestMethod.GET)
	public void downloadBoardFile(@RequestParam("idx") int idx, @RequestParam("boardIdx") int boardIdx,
			HttpServletResponse response) throws Exception {

		Files boardFile = boardService.selectFileInfo(idx, boardIdx);

		if (ObjectUtils.isEmpty(boardFile) == false) {
			String fileName = boardFile.getOriginalFileName();
			byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));

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

	@RequestMapping(value = "/board/downloadBoardFile", method = RequestMethod.POST)
	public String deleteBoardFile(@RequestParam int idx, @RequestParam int boardIdx) throws Exception {
		boardService.deleteBoardFile(idx, boardIdx);
		return "redirect:/board/boardDetail/" + boardIdx;
	}

	// 검색 기능
	@GetMapping("/board/search")
	public String search(@RequestParam(value = "keyword") String keyword, Model model) {
		List<Board> list = boardService.searchPosts(keyword);

		model.addAttribute("boardList", list);

		return "/board/boardList";
	}

//	@RequestMapping("/board/boardDetail/")
//	public String boardDetail(@RequestParam int boardIdx, Model model) {
//		// System.out.println("================> 여기까지");
//		BoardDto board = boardService.boardDeatil(boardIdx);
//		model.addAttribute("board", board);
//		return "board/boardDetail";
//	}
//
//	@RequestMapping("/board/boardUpdate")
//	public String boardUpdate(BoardDto board) {
//		boardService.boardUpdate(board);
//		return "redirect:/board/boardList";
//	}
//
//	@RequestMapping("/board/boardDelete")
//	public String boardDelete(@RequestParam("boardIdx") int boardIdx) {
//		boardService.boardDelete(boardIdx);
//		return "redirect:/board/boardList";
//	}
//

//	}

}
