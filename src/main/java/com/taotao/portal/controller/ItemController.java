package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbItem;
import com.taotao.portal.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	// 商品的基本信息
	@RequestMapping("/item/{itemId}")
	public String getItemById(@PathVariable Long itemId,Model model){
		TbItem tbItem = itemService.getItemById(itemId);
		model.addAttribute("item",tbItem);
		return "item" ;
	}
	// 响应的是html，因此设置MediaType
	@RequestMapping(value="/item/desc/{itemId}",produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemDescById(@PathVariable Long itemId){
		String desc = itemService.getItemDescById(itemId);
		System.out.println("desc:" + desc );
		return desc ;
	}
	
	@RequestMapping(value="/item/param/{itemId}",produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemParamById(@PathVariable Long itemId){
		String paramData = itemService.getItemParamById(itemId);
		System.out.println("paramData: "+ paramData); 
		return paramData ;
	}
}
