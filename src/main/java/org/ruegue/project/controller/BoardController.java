package org.ruegue.project.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.ruegue.project.domain.BoardDto;
import org.ruegue.project.domain.CommentPageHandler;
import org.ruegue.project.domain.PageHandler;
import org.ruegue.project.domain.SearchCondition;
import org.ruegue.project.service.BoardService;
import org.ruegue.project.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	BoardService boardService;

	@Autowired
	CommentService commentservice;
	
	@PostMapping("/remove")
	public String remove(Integer bno , SearchCondition sc, RedirectAttributes rattr, Model m, HttpSession session) {
		String writer = (String) session.getAttribute("id");
		
		try {
			if(boardService.remove(bno, writer)!=1)
				throw new Exception("Delete failed.");
	
		} catch (Exception e) {
			e.printStackTrace();
			rattr.addFlashAttribute("msg", "DEL_ERR");
			return "board";
		}
		
		m.addAttribute(sc);
		rattr.addFlashAttribute("msg", "DEL_OK");
		return "redirect:/board/list";
	}
	

	@PostMapping("/modify")
	public String modify(SearchCondition sc, BoardDto boardDto,RedirectAttributes rattr, Model m, HttpSession session) {
		String writer = (String) session.getAttribute("id");
		boardDto.setWriter(writer);
		
		try {
			if(boardService.modify(boardDto)!=1)
				throw new Exception("Modify failde.");
			
			m.addAttribute("sc", sc);
			rattr.addFlashAttribute("msg", "MOD_OK");
			return "redirect:/board/list";
		} catch (Exception e) {
			e.printStackTrace();
			m.addAttribute("sc", sc);
			rattr.addFlashAttribute("msg", "MOD_ERR");
			return "board";
		}
	}
	
	@GetMapping("/write")
	public String write(BoardDto boardDto, Model m) {
		boardDto.setBno(1);
		m.addAttribute("mode", "new");
		m.addAttribute("boardDto", boardDto);
		
		return "board";
	}
	
	@PostMapping("/write")
	public String write(BoardDto boardDto, RedirectAttributes rattr, Model m, HttpSession session) {
		String writer = (String) session.getAttribute("id");
		boardDto.setWriter(writer);
		
		try {
			if(boardService.write(boardDto) !=1)
				throw new Exception("Write failed.");
			
			rattr.addFlashAttribute("msg", "WRT_OK");
			return "redirect:/board/list";
		} catch (Exception e) {
			e.printStackTrace();
			m.addAttribute("mode", "new");
			m.addAttribute(boardDto);
			m.addAttribute("msg", "WRT_ERR");
			return "board";
		}
	}
	
	@GetMapping("/read")
	public String read(Integer bno, SearchCondition sc, RedirectAttributes rattr, Model m) {
		try {

			BoardDto boardDto = boardService.read(bno);
			m.addAttribute(boardDto);
			m.addAttribute("sc" ,sc);

		} catch (Exception e) {
			e.printStackTrace();
			rattr.addFlashAttribute("sc" ,sc);
			rattr.addFlashAttribute("msg", "READ_ERR");
			return "redirect:/board/list";
		}
		return "board";
	}
	
	@GetMapping("/list")
	public String list(SearchCondition sc, Model m, HttpServletRequest request) {
		if(!loginCheck(request))
			return "redirect:/login/login";
		
		try {
			
			int totalCnt = boardService.getSearchResultCnt(sc);
			m.addAttribute("totalCnt", totalCnt);
			
			PageHandler pageHandler = new PageHandler(totalCnt, sc);
			
			List<BoardDto> list = boardService.getSearchResultPage(sc);
			m.addAttribute("list",list);
			m.addAttribute("ph", pageHandler);
			
			Instant startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
			m.addAttribute("startOfToday" , startOfToday.toEpochMilli());
		} catch (Exception e) {
			e.printStackTrace();
			m.addAttribute("msg", "LiST_ERR");
			m.addAttribute("totalCnt", 0);
		}
		
		return "boardList";
	}

	private boolean loginCheck(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		return session.getAttribute("id")!=null;
	}
}
