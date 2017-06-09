package com.taotao.portal.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.portal.service.ItemService;
import com.taotao.portal.service.StaticPageService;

import freemarker.template.Configuration;
import freemarker.template.Template;
@Service
public class StaticPageServiceImpl implements StaticPageService {

	@Autowired
	private ItemService itemService ;
	@Autowired
	private FreeMarkerConfig freeMarkerConfig;
	@Value("${STATIC_PAGE_PATH}")
	private String STATIC_PAGE_PATH ;
	
	@Override
	public TaotaoResult getItemHtml(Long itemId) throws Exception {
		TbItem tbItem = itemService.getItemById(itemId);
		String desc = itemService.getItemDescById(itemId);
		String param = itemService.getItemParamById(itemId);
		//生成静态页面
		Configuration conf = freeMarkerConfig.getConfiguration();
		Template template = conf.getTemplate("item.ftl");
		// 创建数据集
		Map map = new HashMap<>(); 
		map.put("item", tbItem) ;
		map.put("itemDesc", desc);
		map.put("itemParam", param) ;
		//创建write
		Writer out = new FileWriter(new File(STATIC_PAGE_PATH, itemId + ".html"));
		//开始生产
		template.process(map, out);
		out.flush();
		out.close();
		return TaotaoResult.ok();
	}

}
