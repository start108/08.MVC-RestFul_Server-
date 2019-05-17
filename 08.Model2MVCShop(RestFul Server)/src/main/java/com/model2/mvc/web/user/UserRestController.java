package com.model2.mvc.web.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;


//==> 회원관리 RestController
//@RestController // Model을 거치지 않고 바로 body로 보내기~
//@RequestMapping("/user/*")
public class UserRestController {
	
	///Field
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	//setter Method 구현 않음
		
	public UserRestController(){
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	//////수정필요없음//////
	@RequestMapping( value="json/addUser" )
	public User addUser( @RequestBody User user ) throws Exception {

		System.out.println("/user/json/addUser : POST");
		//Business Logic
		userService.addUser(user);
		
		return userService.getUser(user.getUserId());
	}

	///////수정필요없음///////
	@RequestMapping( value="json/getUser/{userId}", method=RequestMethod.GET )
	public User getUser( @PathVariable String userId ) throws Exception{
		
		System.out.println("/user/json/getUser : GET");
		
		
		return userService.getUser(userId);
	}
	
	//////수정필요없음//////
	@RequestMapping( value="json/updateUser")
	public User updateUser( @RequestBody User user , Model model , HttpSession session) throws Exception{
		//VO를 넘김
		System.out.println("/user/json/updateUser : POST");
		//Business Logic
		userService.updateUser(user);
		userService.getUser(user.getUserId());
		
		return userService.getUser(user.getUserId());
	}
	
	
	//////수정필요없음//////
	@RequestMapping( value="json/login", method=RequestMethod.POST )
	public User login(	@RequestBody User user,
									HttpSession session ) throws Exception{
	
		System.out.println("/user/json/login : POST");
		//Business Logic
		System.out.println("::"+user);
		User dbUser=userService.getUser(user.getUserId());
		
		if( user.getPassword().equals(dbUser.getPassword())){
			session.setAttribute("user", dbUser);
		}
		
		return dbUser;
	}
	//수정필요..?
	@RequestMapping( value="json/checkDuplication")
	public Map checkDuplication( @RequestBody User user , Model model ) throws Exception{
	
	System.out.println("/user/json/checkDuplication : POST");
	//Business Logic
	boolean result=userService.checkDuplication(user.getUserId());
	
	Map map = new HashMap();
	map.put("result", result);
	map.put("message", "ok");
	
	return map;
}

	//수정필요......?
	@RequestMapping( value="json/getUserList")
	public Map listUser( @RequestBody Search search , Model model , HttpServletRequest request) throws Exception{
	
	System.out.println("/user/json/getUserList : GET");
	
	if(search.getCurrentPage() ==0 ){
		search.setCurrentPage(1);
	}
	search.setPageSize(pageSize);
	
	// Business logic 수행..
	Map<String , Object> map=userService.getUserList(search);
	
	Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
	System.out.println(resultPage);
	
	return map;
}
}