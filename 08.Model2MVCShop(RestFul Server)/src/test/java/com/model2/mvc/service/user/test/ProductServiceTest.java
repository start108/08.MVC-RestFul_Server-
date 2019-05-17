package com.model2.mvc.service.user.test;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.user.UserService;


/*
 *	FileName :  UserServiceTest.java
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class ProductServiceTest {

	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	//@Test
	public void testAddProduct() throws Exception {
		
		Product product = new Product();
		System.out.println("product"+product);
		Date date = new Date(19, 03, 03);
		
		int i = 71001;
		
		product.setProdNo(i);
		product.setProdName("길동이노트북");
		product.setProdDetail("잘 켜지지 않음");
		product.setManuDate("19941212");
		product.setPrice(1);
		product.setFileName("개빡");
		product.setRegDate(date);	
		
		productService.addProduct(product);
		
		product = productService.getProduct(i);

		//==> console 확인
		//System.out.println(user);
		
		//==> API 확인
		Assert.assertEquals(i, product.getProdNo());
		Assert.assertEquals("길동이노트북", product.getProdName());
		Assert.assertEquals("잘 켜지지 않음", product.getProdDetail());
		//Assert.assertEquals("19941212", product.getManuDate());
		Assert.assertEquals(1, product.getPrice());
		//Assert.assertEquals("개빡", product.getFileName());
		Assert.assertEquals(date, product.getRegDate());
	}

	
	//@Test
	public void testGetProduct() throws Exception {
		
		Product product = new Product();
		Date date = new Date(19, 03, 03);
		int i = 71001;

		//==> 필요하다면...
//		user.setUserId("testUserId");
//		user.setUserName("testUserName");
//		user.setPassword("testPasswd");
//		user.setSsn("1111112222222");
//		user.setPhone("111-2222-3333");
//		user.setAddr("경기도");
//		user.setEmail("test@test.com");
		
		product = productService.getProduct(i);

		//==> console 확인
		//System.out.println(user);
		
		//==> API 확인
		Assert.assertEquals(i, product.getProdNo());
		Assert.assertEquals("길동이노트북", product.getProdName());
		Assert.assertEquals("잘 켜지지 않음", product.getProdDetail());
		//Assert.assertEquals("19941212", product.getManuDate());
		Assert.assertEquals(1, product.getPrice());
		//Assert.assertEquals("개빡", product.getFileName());
		Assert.assertEquals(date, product.getRegDate());
		
		Assert.assertNotNull(productService.getProduct(i));
	}
	
	//@Test
	 public void testUpdateProduct() throws Exception{
		
		Date date = new Date(19, 03, 03);
		int i = 71001;
		Product product = productService.getProduct(i);
		//Assert.assertNotNull(product);
		System.out.println("@@@"+date);
		System.out.println("@@@"+i);
		System.out.println("@@@"+product);
		
		
		Assert.assertEquals(i, product.getProdNo());
		Assert.assertEquals("길동이노트북", product.getProdName());
		Assert.assertEquals("잘 켜지지 않음", product.getProdDetail());
		Assert.assertEquals(1, product.getPrice());

		product.setProdName("몽이노트북");
		product.setProdDetail("여전히 켜지지 않음");
		product.setPrice(2);
		
		productService.updateProduct(product);
		System.out.println("노트북 팔리지마라");
		product = productService.getProduct(i);
		Assert.assertNotNull(product);
		
		//==> console 확인
		//System.out.println(user);
			
		//==> API 확인
		Assert.assertEquals("몽이노트북", product.getProdName());
		Assert.assertEquals("여전히 켜지지 않음", product.getProdDetail());
		Assert.assertEquals(2, product.getPrice());
	 }

	 @Test
	 public void testGetProductListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	//==> console 확인
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	 //@Test
	 public void testGetProductListByProdNo() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("admin");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	 //@Test
	 public void testGetProductListByProdName() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("SCOTT");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }	 
}
