package com.taotao.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;
import com.taotao.portal.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService ;
	
	@RequestMapping("/search")
	public String search(@RequestParam("q")String keyword,
						@RequestParam(defaultValue="1") Integer page,
						@RequestParam(defaultValue="10") Integer rows,Model model){
		try {
			keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			keyword = "" ;
		}
		SearchResult searchResult = searchService.search(keyword, page, rows); 
		System.out.println("result:" + searchResult.getItemList());
		List<SearchItem> itemList = searchResult.getItemList();
		for (SearchItem item : itemList) {
			System.out.println("图片： " + item.getImage());
		}
		//前台存值
		model.addAttribute("query", keyword);
		model.addAttribute("totalPages", searchResult.getPageCount());
		model.addAttribute("itemList", searchResult.getItemList());
		model.addAttribute("page", searchResult.getCurPage());
		
		return "search";// 逻辑视图
	}
}
