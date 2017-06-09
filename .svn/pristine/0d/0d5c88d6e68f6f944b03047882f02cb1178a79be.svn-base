package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.portal.service.ContentService;

/**
 * 访问首页 
 * @author lw
 */
@Controller
public class IndexController {
	
	@Autowired
	private ContentService contentService ;
	
	/**
	 *  进入首页
	 * @return 首页文件名
	 */
	@RequestMapping("index")
	public String index(Model model){
		String json = contentService.getAd1List();
		//将数据存入域中,前台可以直接通过el表达式取出来
		model.addAttribute("ad1",json);
		return "index" ;
	}
}
