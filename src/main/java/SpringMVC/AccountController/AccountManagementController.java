package SpringMVC.AccountController;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import SpringMVC.DTO.UserInfo;
import SpringMVC.DTO.UserLogin;
import SpringMVC.DTO.UserRegister;
import SpringMVC.Service.UserServiceImpl;
import SpringMVC.Validator.UserLoginValidator;
import SpringMVC.Validator.UserRegisterValidator;

@Controller
public class AccountManagementController {
	@Autowired
	private UserServiceImpl userServiceImpl;

	@RequestMapping(value = "tai-khoan/dang-nhap", method = RequestMethod.GET)
	public ModelAndView Login(HttpSession httpSession) {
		Object obj = httpSession.getAttribute("loginState");
		if(obj != null)
			return new ModelAndView("redirect:/");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("account/login");
		modelAndView.addObject("userLogin", new UserLogin());
		return modelAndView;
	}

	@RequestMapping(value = "tai-khoan/dang-nhap", method = RequestMethod.POST, produces = "application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView Login(HttpSession httpSession, @ModelAttribute("userLogin") UserLogin userLogin, BindingResult bindingResult,
			UserLoginValidator userLoginValidator) {
		Object obj = httpSession.getAttribute("loginState");
		if(obj != null)
			return new ModelAndView("redirect:/");
		
		userLoginValidator.validate(userLogin, bindingResult);
		if (bindingResult.hasErrors())
			return new ModelAndView("account/login", "userLogin", userLogin);

		if (userServiceImpl.Login(userLogin)) {
			UserInfo userInfo  = userServiceImpl.GetUserInfo(userLogin.getUsername());
			httpSession.setAttribute("loginState", "logged:true;username:" + userLogin.getUsername() + ";role:" + userInfo.getRole().getName());
			if(userInfo.getRole().getName().equals("Admin"))
				return new ModelAndView("redirect:/quan-tri/tong-quan");
			return new ModelAndView("redirect:/");
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("account/login");
		modelAndView.addObject("userLogin", new UserLogin());
		modelAndView.addObject("state", "????ng nh???p kh??ng th??nh c??ng, c?? th??? d??? li???u kh??ng h???p l??? ho???c ch??a t???n t???i t??i kho???n n??y");
		return modelAndView;
	}

	@RequestMapping(value = "tai-khoan/dang-ky", method = RequestMethod.GET)
	public ModelAndView Register(HttpSession httpSession) {
		Object obj = httpSession.getAttribute("loginState");
		if(obj != null)
			return new ModelAndView("redirect:/");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("account/register");
		modelAndView.addObject("userRegister", new UserRegister());
		return modelAndView;
	}

	@RequestMapping(value = "tai-khoan/dang-ky", method = RequestMethod.POST, produces = "application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView Login(HttpSession httpSession, @ModelAttribute("userRegister") UserRegister userRegister, BindingResult bindingResult,
			UserRegisterValidator userRegisterValidator) {
		Object obj = httpSession.getAttribute("loginState");
		if(obj != null)
			return new ModelAndView("redirect:/");
		
		userRegisterValidator.validate(userRegister, bindingResult);
		if (bindingResult.hasErrors())
			return new ModelAndView("account/register", "userRegister", userRegister);

		if (userServiceImpl.Register(userRegister))
			return new ModelAndView("redirect:/tai-khoan/dang-nhap");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("account/register");
		modelAndView.addObject("userRegister", new UserRegister());
		modelAndView.addObject("state", "????ng k?? kh??ng th??nh c??ng, c?? th??? d??? li???u kh??ng h???p l??? ho???c ???? t???n t???i t??i kho???n n??y");
		return modelAndView;
	}
	
	@RequestMapping(value = "tai-khoan/dang-xuat", method = RequestMethod.GET)
	public ModelAndView Logout(HttpSession httpSession) {
		Object obj = httpSession.getAttribute("loginState");
		if(obj == null)
			return new ModelAndView("redirect:/");
		
		httpSession.removeAttribute("loginState");
		return new ModelAndView("redirect:/");
	}
}
