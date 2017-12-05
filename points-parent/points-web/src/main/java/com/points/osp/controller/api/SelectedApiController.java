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
 * APP精选接口
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
	 * 使用jsop远程调用
	 * 精选商品-搜索
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("selList")
	public String getSelList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> banMap = Maps.newHashMap();
		Map<String, String> param = Maps.newHashMap();
		String goodsType = request.getParameter("goodsType");//商品类型
		String goodsName = request.getParameter("goodsName");//商品名称
		if(StringUtils.isNoneBlank(goodsType)){
			param.put("goodsType", goodsType);
		}
		if(StringUtils.isNoneBlank(goodsName)){
			param.put("goodsName", goodsName);
		}
		List<Selected> sels = selectedService.getSelectedForApp(param);
		//加工主图片
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
	 * 推广兑换礼品
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
					ResultUtil.creComErrorResult("请登录后兑换礼品！"));

		}
		if (StringUtils.isBlank(selId)) {
			return ajaxJsonP(request, response,
					ResultUtil.creComErrorResult("请选择兑换的商品！"));
		}
		if (StringUtils.isBlank(num)) {
			return ajaxJsonP(request, response,
					ResultUtil.creComErrorResult("请输入兑换数量！"));
		}
		//兑换精选商品
		Map<String, Object> resMap = selectedService.tradeSelected(memberId,selId,Long.valueOf(num));
		if ("0".equals((String) resMap.get("code"))) {
			return ajaxJsonP(request, response,
					ResultUtil.creComErrorResult((String) resMap.get("msg")));
		}
		
		//积分兑换增加相应推荐积分
		OrderInfo orderInfo = (OrderInfo)resMap.get("orderInfo");
		orderService.addReferPoints(orderInfo);
		
		return ajaxJsonP(request, response, ResultUtil.creComSucResult());

	}

	

}
