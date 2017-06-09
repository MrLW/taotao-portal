package com.taotao.portal.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.portal.service.SearchService;
@Service
public class SearchServiceImpl implements SearchService {
	
	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL ;
	
	@Override
	public SearchResult search(String keyword, int page, int rows) {
		// 封装请求参数
		Map<String,String> paramMap = new HashMap<>() ;
		paramMap.put("keyword", keyword);
		paramMap.put("page", page+"") ;
		paramMap.put("rows", rows+"");
		//调用服务
		String json = HttpClientUtil.doGet(SEARCH_BASE_URL,paramMap);
		System.out.println("json:" + json); 
		//将json转换成java对象
		TaotaoResult result = TaotaoResult.formatToPojo(json, SearchResult.class);
		// 获取返回结果
		SearchResult searchResult = (SearchResult) result.getData();
		List<SearchItem> itemList = searchResult.getItemList();
		for (SearchItem item : itemList) {
			System.out.println("在service中遍历图片：" + item.getImage());
		}
		return searchResult;
	}
}
