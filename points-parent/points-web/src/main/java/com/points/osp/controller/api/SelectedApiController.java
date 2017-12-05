package com.points.osp.controller.api;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bailian.entity.OrderInfo;
import com.bailian.entity.Selected;
import com.bailian.utils.ResultUtil;
import com.google.common.collect.Maps;
import com.points.osp.common.service.OrderService;
import com.points.osp.common.service.SelectedService;
import com.points.osp.controller.web.BaseController;

/**
 * APP��ѡ�ӿ�
 * 
 * @author wbwangsh
 *
 */
@Controller
@RequestMapping("api/selected")
public class SelectedApiController extends BaseController {

	@Autowired
	private SelectedService selectedService;
	
	
	@Autowired
	private OrderService orderService;

	
	
	/**
	 * ʹ��jsopԶ�̵���
	 * ��ѡ��Ʒ-����
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("selList")
	public String getSelList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> banMap = Maps.newHashMap();
		Map<String, String> param = Maps.newHashMap();
		String goodsType = request.getParameter("goodsType");//��Ʒ����
		String goodsName = request.getParameter("goodsName");//��Ʒ����
		if(StringUtils.isNoneBlank(goodsType)){
			param.put("goodsType", goodsType);
		}
		if(StringUtils.isNoneBlank(goodsName)){
			param.put("goodsName", goodsName);
		}
		List<Selected> sels = selectedService.getSelectedForApp(param);
		//�ӹ���ͼƬ
		if(CollectionUtils.isNotEmpty(sels)){
			String pic = "";
			for(Selected sel: sels){
				pic = sel.getSelPics();
				sel.setMainPic(pic.split(";")[0]);
			}
		}
		banMap.put("sels", sels);
		banMap.put("count", sels.size());
		return ajaxJsonP(request, response, ResultUtil.creObjSucResult(banMap));
	}
	
	
	
	
	/**
	 * �ƹ�һ���Ʒ
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("trade")
	public String tradeSelected(HttpServletRequest request,
			HttpServletResponse response) {
		String memberId = request.getParameter("memberId");
		String selId = request.getParameter("selId");
		String num = request.getParameter("num");
		if (StringUtils.isBlank(memberId)) {
			return ajaxJsonP(request, response,
					ResultUtil.creComErrorResult("���¼��һ���Ʒ��"));

		}
		if (StringUtils.isBlank(selId)) {
			return ajaxJsonP(request, response,
					ResultUtil.creComErrorResult("��ѡ��һ�����Ʒ��"));
		}
		if (StringUtils.isBlank(num)) {
			return ajaxJsonP(request, response,
					ResultUtil.creComErrorResult("������һ�������"));
		}
		//�һ���ѡ��Ʒ
		Map<String, Object> resMap = selectedService.tradeSelected(memberId,selId,Long.valueOf(num));
		if ("0".equals((String) resMap.get("code"))) {
			return ajaxJsonP(request, response,
					ResultUtil.creComErrorResult((String) resMap.get("msg")));
		}
		
		//���ֶһ�������Ӧ�Ƽ�����
		OrderInfo orderInfo = (OrderInfo)resMap.get("orderInfo");
		orderService.addReferPoints(orderInfo);
		
		return ajaxJsonP(request, response, ResultUtil.creComSucResult());

	}

	

}
