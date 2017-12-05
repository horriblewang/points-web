package com.points.osp.controller.api;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.bailian.dto.ActivityDetailDto;
import com.bailian.entity.MemberInfo;
import com.bailian.entity.OrderInfo;
import com.bailian.utils.ResultUtil;
import com.points.osp.common.service.ActivityApiService;
import com.points.osp.common.service.MemberService;
import com.points.osp.common.service.OrderService;
import com.points.osp.common.utils.Constant;
import com.points.osp.common.utils.RedisUtil;
import com.points.osp.controller.web.BaseController;

/**
 * 积分抽奖 商品兑换
 * 
 * @author wbwangsh
 *
 */
@Controller
@RequestMapping("api/activity")
public class ActivityApiController extends BaseController {

	@Autowired
	private ActivityApiService activityApiService;

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 查询转盘活动
	 * 
	 * @return
	 */
	@RequestMapping("getLuckyDraw")
	public String getLuckyDraw(HttpServletRequest request,
			HttpServletResponse response) {
		String memberId = request.getParameter("memberId");
		if(StringUtils.isBlank(memberId)){
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("亲，先登录才能参加抽奖。"));	
		}
		
		Map<String, Object> resMap = activityApiService.getLuckyDraw();
		if ("0".equals((String) resMap.get("code"))) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult((String) resMap.get("msg")));	
		}
		MemberInfo mem = memberService.getMember(Long.valueOf(memberId));
		//查询剩余抽奖次数
		String shakeTime = redisUtil.get(Constant.SHAKE_TIME_KEY + memberId, "0");
		int userTime = Integer.valueOf(shakeTime);
		long drawTimes = mem.getReferNum() /10;
		resMap.put("drawTimes", drawTimes - userTime);
		resMap.put("memberId", memberId);
		return ajaxJsonP(request, response, ResultUtil.creObjSucResult(resMap));

	}

	/**
	 * 点击抽奖
	 * 
	 * @param activity
	 * @return
	 */
	@RequestMapping("shakeForPrize")
	public String shakeForPrize(ActivityDetailDto detailDto,
			HttpServletRequest request, HttpServletResponse response) {
		if (detailDto.getActId() == null) {
			return ajaxJsonP(request, response,
					ResultUtil.creComErrorResult("抽奖活动不存在！"));
		}
		if (detailDto.getMemberId() == null) {
			return ajaxJsonP(request, response,
					ResultUtil.creComErrorResult("未查询到用户信息，请登陆！"));

		}
		MemberInfo member = memberService.getMember(detailDto.getMemberId());
		if (member == null) {
			return ajaxJsonP(request, response,
					ResultUtil.creComErrorResult("亲，注册后才可参加抽奖活动呦！"));

		}
		long drawTimes = member.getReferNum() / 10;

		String shakeTime = redisUtil.get(Constant.SHAKE_TIME_KEY + detailDto.getMemberId(), "0");
		int userTime = Integer.valueOf(shakeTime);
		if(userTime >= drawTimes){
			return ajaxJsonP(request, response,ResultUtil.creComErrorResult("今天抽奖次数以及用完！"));
		}
		//判断是否在摇一摇进行中
		String shakeFlag = redisUtil.get("shaking_" + member.getMemberId(), "false");
		if("true".equals(shakeFlag)){
			return ajaxJsonP(request, response,
					ResultUtil.creComErrorResult("亲，系统处理中，请稍后再摇哦！"));
		}else{
			redisUtil.set(Constant.SHAKING_KEY + member.getMemberId(), "true");	
		}
		Map<String, Object> resMap = activityApiService.shakeForGift(
				detailDto.getActId(), member.getMemberId());
		if ("0".equals((String) resMap.get("code"))) {
			ResultUtil.creComErrorResult((String) resMap.get("msg"));
		}
		redisUtil.del(Constant.SHAKING_KEY + member.getMemberId());//摇一摇结束后，删除缓存
		return ajaxJsonP(request, response, ResultUtil.creObjSucResult(resMap));

	}

	/**
	 * 查询兑换活动
	 * 
	 * @return
	 */
	@RequestMapping("getExchangeAct")
	public String getExchangeAct(HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> resMap = activityApiService.getExchangeAct();
		if ("0".equals((String) resMap.get("code"))) {
			ResultUtil.creComErrorResult((String) resMap.get("msg"));
		}

		return ajaxJsonP(request, response, ResultUtil.creObjSucResult(resMap));

	}

	/**
	 * 积分兑换礼品
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("exGoodsWithPoints")
	public String exchangeGoodsWithPoints(HttpServletRequest request,
			HttpServletResponse response) {
		String memberId = request.getParameter("memberId");
		String goods = request.getParameter("goods");
		String goodsNum = request.getParameter("goodsNum");
		if (StringUtils.isBlank(memberId)) {
			return ajaxJsonP(request, response,
					ResultUtil.creComErrorResult("请登录后兑换礼品！"));

		}
		if (StringUtils.isBlank(goodsNum) || StringUtils.isBlank(goods)) {
			return ajaxJsonP(request, response,
					ResultUtil.creComErrorResult("请选择兑换的商品！"));
		}
		//兑换礼品
		Map<String, Object> resMap = activityApiService.exchangeGoods(memberId,
				goods, goodsNum);
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
