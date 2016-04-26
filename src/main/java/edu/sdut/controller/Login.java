package edu.sdut.controller;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sdut.dao.UserInfoMapper;
import edu.sdut.model.UserInfo;
import edu.sdut.util.MD5Util;

@Controller
@RequestMapping("/login")
public class Login {
	
	@Resource
	UserInfoMapper userInfoMapper;
	
	@ResponseBody
	@RequestMapping("login")
	public String validateLogin(HttpServletRequest request){
		String inputCode = request.getParameter("inputCode");
		String checkCode = (String) request.getSession().getAttribute("checkCode");
		if (!inputCode.equals(checkCode)){
			return "captchafailed";
		}
		String userName = request.getParameter("userName");
		String passWord = request.getParameter("passWord");
		String md5pwd = MD5Util.MD5(passWord);
		HashMap<String,String> loginInfo =new HashMap<String,String>();
		loginInfo.put("name", userName);
		loginInfo.put("password", md5pwd);
		UserInfo userInfo = userInfoMapper.selectByNamePassword(loginInfo);
		if (userInfo==null) {
			return "failed";
		}else {
			request.getSession().setAttribute("user", userInfo);
			return "success";
		}
	}
	
}
