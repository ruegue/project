package org.ruegue.project.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.ruegue.project.dao.UserDao;
import org.ruegue.project.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {
	
	@Autowired
	UserDao userDao;
	
	final int FAIL = 0;
	
	@InitBinder
	public void toDate(WebDataBinder binder) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
		binder.setValidator(new UserValidator()); // UserValidator를 WebDataBinder의 로컬 validator로 등록 
	//	List<Validator> validatorList = binder.getValidators();
	//	System.out.println("validatorList="+validatorList);
	}
	
	@GetMapping("/add")
	public String register() {
		return "registerForm";
	}
	
	@PostMapping("/add")
	public String save(@Valid UserDto user, BindingResult result, Model m) throws Exception {
		
		if(!result.hasErrors()) {
			
			
			int rowCnt = userDao.insertUser(user);
			
			if(rowCnt!=FAIL) 
				return "registerInfo";
				
		}
		
		return "registerForm";
		
	}
	

	private boolean isValid(UserDto user) {
		return true;
	}
}
