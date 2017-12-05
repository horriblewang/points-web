package com.points.osp.controller.api;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bailian.entity.AddressInfo;
import com.bailian.entity.MemberInfo;
import com.bailian.utils.ResultUtil;
import com.google.common.collect.Maps;
import com.points.osp.common.service.AddressService;
import com.points.osp.controller.web.BaseController;

@Controller
@RequestMapping("api/address")
public class AddressApiController extends BaseController {
	
	private static Logger log = LoggerFactory.getLogger(AddressApiController.class);

	@Autowired
	private AddressService addressService;
	
	
	/**
	 * ɾ����ַ
	 * 
	 * @return
	 */
	@RequestMapping("delAdd")
	public String delAddress(AddressInfo addressInfo,
			HttpServletRequest request, HttpServletResponse response) {
		if(addressInfo.getMemberId() == null || addressInfo.getAddId() ==null){
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("����ʧ�ܣ����ȵ�¼��"));
		}
		addressService.delAddress(addressInfo.getAddId());
		return ajaxJsonP(request, response, ResultUtil.creComSucResult());
	}
	
	/**
	 * ����Ĭ��
	 * @param addressInfo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("setDefault")
	public String setDefaultAddress(AddressInfo addressInfo,
			HttpServletRequest request, HttpServletResponse response) {
		if(addressInfo.getMemberId() == null || addressInfo.getAddId() ==null){
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("����ʧ�ܣ����ȵ�¼��"));
		}
		addressService.setDefaultAdd(addressInfo);
		return ajaxJsonP(request, response, ResultUtil.creComSucResult());
	}

	/**
	 * ��ѯ��ַ
	 * 
	 * @return
	 */
	@RequestMapping("getList")
	public String getAddressList(MemberInfo memberInfo,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resMap = Maps.newHashMap();
		Long memIdLong = memberInfo.getMemberId();
		List<AddressInfo> adds = addressService.getList(memIdLong);
		resMap.put("address", adds);
		return ajaxJsonP(request, response, ResultUtil.creObjSucResult(resMap));
	}

	/**
	 * ���������޸ĵ�ַ
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("addOrUpdate")
	public String addOrUpdateAddress(AddressInfo addressInfo,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			if(addressInfo.getMemberId() == null){
				return ajaxJsonP(request, response, ResultUtil.creComErrorResult("����ʧ�ܣ����ȵ�¼��"));
			}
			String addInfo = addressInfo.getAddInfo();
			String name = addressInfo.getName();
			addressInfo.setAddInfo(URLDecoder.decode(addInfo, "UTF-8"));
			addressInfo.setName(URLDecoder.decode(name, "UTF-8"));
			addressService.saveOrUpdate(addressInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("�ջ���ַʧ��:" , e);
			return ajaxJsonP(request, response, ResultUtil.creComSucResult());

		}
		return ajaxJsonP(request, response, ResultUtil.creComSucResult());

	}

}
