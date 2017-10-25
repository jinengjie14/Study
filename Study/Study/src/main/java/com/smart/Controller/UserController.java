package com.smart.Controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.Service.LoginService;
import com.smart.valid.LoginForm;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.smart.Repository.UserDao;
import com.smart.Service.RegisterService;
import com.smart.valid.RegisterForm;



@Controller
public class UserController {

	@Autowired  
	DefaultKaptcha defaultKaptcha; 
	@Resource
	private UserDao userdao;
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	@RequestMapping("/login")
	public String login() {
		return "user/login";
	}
	@RequestMapping("/register")
	public String register() {
		return "user/reg";
	}
	
	@ResponseBody//注册入口
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public ModelAndView UserRegister(@Valid RegisterForm user,BindingResult Result,HttpSession httpSession,RegisterService registerservice,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse){
		ModelAndView mav= new ModelAndView("redirect:/register");
		registerservice.init(Result, user, userdao);
		String captchaId = (String) httpServletRequest.getSession().getAttribute("vrifyCode");    
	     String parameter = httpServletRequest.getParameter("vrifyCode");  
	     System.out.println("Session  vrifyCode "+captchaId+" form vrifyCode "+parameter);
		if(registerservice.getCount()>0||!captchaId.equals(parameter)){
			mav.addObject("error",registerservice.getErrorInfo());
			if(!captchaId.equals(parameter)) {
				registerservice.setErrorInfo("验证码错误");
				mav.addObject("error", registerservice.getErrorInfo());
			}
			return mav;
		}
		registerservice.Registercast(user); //进行注册操作
		//mav.addObject("userid", user.getId());
		mav.setViewName("redirect:/login");
		return mav; 
	}
	@ResponseBody//登陆入口以及验证码的验证
	@RequestMapping(value = "/login/check", method = RequestMethod.POST)
	public ModelAndView logincheck(@Valid LoginForm user,BindingResult Result,HttpSession httpSession,LoginService loginservice,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse){
		ModelAndView mav= new ModelAndView("redirect:/login");
		loginservice.init(Result, user, httpSession,userdao);
		String captchaId = (String) httpServletRequest.getSession().getAttribute("vrifyCode");    
	     String parameter = httpServletRequest.getParameter("vrifyCode");  
	     System.out.println("Session  vrifyCode "+captchaId+" form vrifyCode "+parameter);
		if(loginservice.getCount()>0 ||!captchaId.equals(parameter)){
			mav.addObject("error", loginservice.getErrorInfo()); //向前台输入错误信息
			if(!captchaId.equals(parameter)) {
				loginservice.setErrorInfo("验证码错误");
				mav.addObject("error", loginservice.getErrorInfo());
			}
			return mav;
		}else{
			mav.addObject("info", "登录成功"); 
			mav.setViewName("redirect:/");
			return mav; 
			
			} 
		}
	//生成验证码
	@RequestMapping("/defaultKaptcha")  
    public void defaultKaptcha(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception{  
            byte[] captchaChallengeAsJpeg = null;    
             ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();    
             try {    
             //生产验证码字符串并保存到session中  
             String createText = defaultKaptcha.createText();  
             httpServletRequest.getSession().setAttribute("vrifyCode", createText);  
             //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中  
             BufferedImage challenge = defaultKaptcha.createImage(createText);  
             ImageIO.write(challenge, "jpg", jpegOutputStream);  
             } catch (IllegalArgumentException e) {    
                 httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);    
                 return;    
             }   
         
             //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组  
             captchaChallengeAsJpeg = jpegOutputStream.toByteArray();    
             httpServletResponse.setHeader("Cache-Control", "no-store");    
             httpServletResponse.setHeader("Pragma", "no-cache");    
             httpServletResponse.setDateHeader("Expires", 0);    
             httpServletResponse.setContentType("image/jpeg");    
             ServletOutputStream responseOutputStream =    
                     httpServletResponse.getOutputStream();    
             responseOutputStream.write(captchaChallengeAsJpeg);    
             responseOutputStream.flush();    
             responseOutputStream.close();    
    }  
	
	}
