package com.crux.ssmcrud.test;

import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration	//Spring的MVC测试模块，让其可以自动注入WebApplicationContext
@ContextConfiguration({"classpath:spring.xml", "classpath:spring-mvc.xml"})
public class MVCTest{
	@Autowired
	protected WebApplicationContext webApplicationContext;
	protected MockMvc mockMvc;	//MVC测试模块

	/**
	 * MVC测试模块初始化，执行该类的每个测试方法前都会执行此方法
	 */
	@BeforeEach
	protected void initMockMvc(){
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	/**
	 * 测试请求：/employee_list
	 */
	@Test
	protected void testRequestPage() throws Exception{
		System.out.println("执行请求测试：/employee_list");
		MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
		params.add("pageNum", "1");
		params.add("pageSize", "5");
		params.add("navigate", "5");
		var result = mockMvc.perform(MockMvcRequestBuilders.get("/employee_list")
						.params(params)).andReturn();
		ModelAndView mav = result.getModelAndView();
		assert mav != null;
		System.out.println("视图名：" + mav.getViewName());
		PageInfo<?> pageInfo = (PageInfo<?>)mav.getModel().get("pageInfo");
		System.out.println(pageInfo);
	}
}
