package com.taotao.portal.pojo;

import org.apache.commons.lang3.StringUtils;

import com.taotao.pojo.TbItem;

public class PortalItem extends TbItem {

	public String[] getImages(){
		String image = this.getImage();
		if(StringUtils.isNotBlank(image)){
			String[] imgs = image.split(",");
			return imgs ;
		}
		return null ;
	}
}
