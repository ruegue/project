package org.ruegue.project.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ruegue.project.dao.UserDao;
import org.ruegue.project.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	UserDao userDao;
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
	
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String loginForm() {
		return "loginForm";
	}
	
	@PostMapping("/login")
	public String login(String id, String pwd, boolean remenberId,
			HttpServletRequest request, HttpServletResponse response ) {
		
		if(!loginCheck(id,pwd)) {
			String msg="";
				
			try {
				msg = URLEncoder.encode("id와 pwd가 일치하지 않습니다.", "utf-8");
			} catch (UnsupportedEncodingException e) {}
			return "redirect:/login/login?msg="+msg;
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("id", id);
		
		if(remenberId) {
			Cookie cookie = new Cookie("id", id);
			response.addCookie(cookie);
		}else {
			Cookie cookie = new Cookie("id", id);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
		
		
		return "redirect:/";
	}

	private boolean loginCheck(String id, String pwd) {
		 UserDto user = null;

	        try {
	            user = userDao.selectUser(id);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }

	        return user!=null && user.getPwd().equals(pwd);
	}
}
