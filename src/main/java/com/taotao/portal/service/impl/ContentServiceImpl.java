package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.AdNode;
import com.taotao.portal.service.ContentService;
@Service
public class ContentServiceImpl implements ContentService {
	
	@Override
	public String getAd1List() {
		String json = HttpClientUtil.doGet("http://localhost:8081/rest/content/89");
		//将json转换成java对象
		TaotaoResult result = TaotaoResult.formatToList(json, TbContent.class);
		//获取data属性里面的json
		List<TbContent> contentList = (List<TbContent>) result.getData();
		//将其转换成AdNode列表
		List<AdNode> nodeList = new ArrayList<>() ;
		for (TbContent tbContent : contentList) {
			AdNode node = new AdNode() ;
			node.setHeight(240);
			node.setWidth(670);
			node.setSrc(tbContent.getPic());
			node.setHeight(240);
			node.setWidth(550);
			node.setSrc(tbContent.getPic2());
			
			node.setAlt(tbContent.getTitle());
			node.setHref(tbContent.getUrl());
			nodeList.add(node); 
		}
		//将nodeList转化成json返回
		String resultJson = JsonUtils.object2Json(nodeList);
		return resultJson;
	}

}
