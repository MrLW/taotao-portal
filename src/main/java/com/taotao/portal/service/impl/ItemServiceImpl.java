package com.taotao.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.PortalItem;
import com.taotao.portal.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService {
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL ;
	
	@Value("${REST_ITEM_BASE_INFO_URL}")
	private String REST_ITEM_BASE_INFO_URL ;
	
	@Value("${REST_ITEM_DESC_URL}")
	private String REST_ITEM_DESC_URL ;
	
	@Value("${REST_ITEM_PARAM_URL}")
	private String REST_ITEM_PARAM_URL ;
	
	@Override
	public TbItem getItemById(Long itemId) {
		// 调用rest服务
		String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_BASE_INFO_URL + itemId) ;
		// 转成pojo
		TaotaoResult result = TaotaoResult.formatToPojo(json, PortalItem.class) ;
		
		TbItem tbItem = (TbItem) result.getData();
		return tbItem;
	}

	@Override
	public String getItemDescById(Long itemId) {
		String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_DESC_URL + itemId);
		TaotaoResult result = TaotaoResult.formatToPojo(json, TbItemDesc.class);
		TbItemDesc tbItemDesc = (TbItemDesc)result.getData();
		String desc = tbItemDesc.getItemDesc();
		return desc;
	}
	
	@Override
	public String getItemParamById(Long itemId) {
		// 根据商品id查找对应的规格参数
		String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_PARAM_URL + itemId);
		TaotaoResult result = TaotaoResult.formatToPojo(json, TbItemParamItem.class);
		TbItemParamItem tbItemParam = (TbItemParamItem)result.getData();
		String param = tbItemParam.getParamData() ;
		//手工生成html
		StringBuffer sb = new StringBuffer() ;
		if(StringUtils.isNotEmpty(param)){
			//把json转成java(不知道什么结构可以用map)
			List<Map> mapList = JsonUtils.jsonToList(param, Map.class);
			sb.append("<table border=\"1\" cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
			sb.append("	<tbody>\n");
			for (Map map : mapList) {
				sb.append("		<tr>\n");
				sb.append("			<th class=\"tdTitle\" colspan=\"2\">"+map.get("group")+"</th>\n");
				sb.append("		</tr>\n");
				//取规格项
				List<Map>mapList2 = (List<Map>) map.get("params");
				for (Map map2 : mapList2) {
					sb.append("		<tr>\n");
					sb.append("			<td class=\"tdTitle\">"+map2.get("k")+"</td>\n");
					sb.append("			<td>"+map2.get("v")+"</td>\n");
					sb.append("		</tr>\n");
				}
			}
			sb.append("	</tbody>\n");
			sb.append("</table>");
		}
		
		return sb.toString(); 
	}

}
