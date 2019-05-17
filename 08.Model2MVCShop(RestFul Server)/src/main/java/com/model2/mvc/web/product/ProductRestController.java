package com.model2.mvc.web.product;

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
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.user.UserService;


//==> 상품관리 RestController
@RestController
@RequestMapping("/product/*")
public class ProductRestController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
		
	public ProductRestController(){
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	
	@RequestMapping( value="json/getProduct/{prodNo}", method=RequestMethod.GET ) //GET방식은 {prodNo}처럼 명시를 해줘야하지만
	public Product getProduct( @PathVariable int prodNo ) throws Exception{//       POST방식의 경우 Body로 바로 가므로 명시하지않고 바로 접근이 가능
		
		System.out.println("/product/json/getProduct : GET");
		
		return productService.getProduct(prodNo);
	}

	
	@RequestMapping( value="json/addProduct", method=RequestMethod.POST)
	public void addProduct(@RequestBody Product product)throws Exception{
		
		System.out.println("/product/json/addProduct: POST");
		
		productService.addProduct(product);
		
	}
	
	
	@RequestMapping(value="json/updateProduct")
	public Product updateProduct(@RequestBody Product product, Model model, HttpSession session) throws Exception{
		
		System.out.println("/product/json/updateProduct : POST");
		//Business Logic
		productService.updateProduct(product);
		productService.getProduct(product.getProdNo());
		
		return productService.getProduct(product.getProdNo());
	}

	
	@RequestMapping( value="json/listProduct")
	public Map listProduct( @RequestBody Search search , Model model , HttpServletRequest request) throws Exception{
	
	System.out.println("/product/json/listProduct");
	
	if(search.getCurrentPage() ==0 ){
		search.setCurrentPage(1);
	}
	search.setPageSize(pageSize);
	
	// Business logic 수행..
	Map<String , Object> map=productService.getProductList(search);
	
	Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
	System.out.println(resultPage);
	
	return map;
	}
}