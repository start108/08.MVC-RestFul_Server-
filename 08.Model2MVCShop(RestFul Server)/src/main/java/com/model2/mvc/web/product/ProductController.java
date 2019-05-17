package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;


@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

		
	public ProductController(){
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	
	@RequestMapping("addProductView")
	public String addProductView() throws Exception {

		System.out.println("addProductView");
		
		return "forward:/product/getProduct.jsp";
	}
	
	@RequestMapping("addProduct")
	public String addProduct( @ModelAttribute("productVO") Product product ) throws Exception {

		System.out.println("addProduct");
		//Business Logic
		String manuDate = product.getManuDate().replaceAll("-","");
		product.setManuDate(manuDate);
		productService.addProduct(product);
		
		return "forward:/product/addProduct.jsp";
	}
	
	@RequestMapping("getProduct")
	public String getProduct( @RequestParam("prodNo") int prodNo , @RequestParam("menu") String menu, Model model) throws Exception {
		
		System.out.println("getProduct");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		
		model.addAttribute("product", product);
		
		if (menu.equals("manage")) {
		
			return "forward:/product/updateProduct.jsp?menu=manage";
		}
			return "forward:/product/getProduct.jsp?menu=search";
	}

		
	
	@RequestMapping("updateProduct")
	public String updateProduct( @ModelAttribute("product") Product product  ,Model model, HttpSession session) throws Exception{

		System.out.println("updateProduct");
		//Business Logic
		
		productService.updateProduct(product);
	
		return "forward:/product/getProduct.jsp?menu=manage";
	}
	
	@RequestMapping("updateProductView")
	public String updateUserView( @RequestParam("prodNo") int prodno , Model model ) throws Exception{

		System.out.println("updateProductView");
		//Business Logic
		Product product = productService.getProduct(prodno);
		
		model.addAttribute("product", product);
		
		return "forward:/product/updateProduct.jsp";
	}
	
	
	
	@RequestMapping("listProduct")
	public String listUser( @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("listProduct");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model and View 
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}

}