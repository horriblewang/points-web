package com.points.osp.controller.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bailian.dto.AddressInfoDto;
import com.bailian.entity.MemberInfo;
import com.bailian.utils.Page;
import com.google.common.collect.Maps;
import com.points.osp.common.service.AddressService;
import com.points.osp.common.utils.ViewPage;


@Controller
@RequestMapping("points/address")
public class AddressController {
	
	private static Logger log = LoggerFactory.getLogger(AddressController.class);

	private static final String WEB_ROOT = "modules/address/";
	
	@Autowired
	private AddressService addressService;
	
	@RequestMapping({"list",""})
	public String List(@RequestParam Map<String, String> paraMap,
			HttpServletRequest request, HttpServletResponse response,
			Model model, AddressInfoDto addressInfoDto){

		ViewPage<AddressInfoDto> viewPage = new ViewPage<AddressInfoDto>(request,
				response);
		int count = addressService.countAddress(paraMap);
		Page<AddressInfoDto> page = new Page<AddressInfoDto>();
		page.setCurrentPage(viewPage.getPageNo());
		page.setPageSize(viewPage.getPageSize());
		page.setCount(count);
		Map<String, Object> queryMap = Maps.newHashMap();
		queryMap.putAll(paraMap);
		queryMap.put("start", page.getStart() - 1);
		queryMap.put("end", page.getPageSize());
		List<AddressInfoDto> list = addressService.getListByPage(queryMap);
		viewPage.getList().addAll(list);
		viewPage.setCount(count);
		model.addAttribute("page", viewPage);
		model.addAttribute("addressInfo", addressInfoDto);
		return WEB_ROOT + "addList";
	}

}
