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
 * ���ֳ齱 ��Ʒ�һ�
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
	 * ��ѯת�̻
	 * 
	 * @return
	 */
	@RequestMapping("getLuckyDraw")
	public String getLuckyDraw(HttpServletRequest request,
			HttpServletResponse response) {
		String memberId = request.getParameter("memberId");
		if(StringUtils.isBlank(memberId)){
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("�ף��ȵ�¼���ܲμӳ齱��"));	
		}
		
		Map<String, Object> resMap = activityApiService.getLuckyDraw();
		if ("0".equals((String) resMap.get("code"))) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult((String) resMap.get("msg")));	
		}
		MemberInfo mem = memberService.getMember(Long.valueOf(memberId));
		//��ѯʣ��齱����
		String shakeTime = redisUtil.get(Constant.SHAKE_TIME_KEY + memberId, "0");
		int userTime = Integer.valueOf(shakeTime);
		long drawTimes = mem.getReferNum() /10;
		resMap.put("drawTimes", drawTimes - userTime);
		resMap.put("memberId", memberId);
		return ajaxJsonP(request, response, ResultUtil.creObjSucResult(resMap));

	}

	/**
	 * ����齱
	 * 
	 * @param activity
	 * @return
	 */
	@RequestMapping("shakeForPrize")
	public String shakeForPrize(ActivityDetailDto detailDto,
			HttpServletRequest request, HttpServletResponse response) {
		if (detailDto.getActId() == null) {
			return ajaxJsonP(request, response,
					ResultUtil.creComErrorResult("�齱������ڣ�"));
		}
		if (detailDto.getMemberId() == null) {
			return ajaxJsonP(request, response,
					ResultUtil.creComErrorResult("δ��ѯ���û���Ϣ�����½��"));

		}
		MemberInfo member = memberService.getMember(detailDto.getMemberId());
		if (member == null) {
			return ajaxJsonP(request, response,
					ResultUtil.creComErrorResult("�ף�ע���ſɲμӳ齱��ϣ�"));

		}
		long drawTimes = member.getReferNum() / 10;

		String shakeTime = redisUtil.get(Constant.SHAKE_TIME_KEY + detailDto.getMemberId(), "0");
		int userTime = Integer.valueOf(shakeTime);
		if(userTime >= drawTimes){
			return ajaxJsonP(request, response,ResultUtil.creComErrorResult("����齱�����Լ����꣡"));
		}
		//�ж��Ƿ���ҡһҡ������
		String shakeFlag = redisUtil.get("shaking_" + member.getMemberId(), "false");
		if("true".equals(shakeFlag)){
			return ajaxJsonP(request, response,
					ResultUtil.creComErrorResult("�ף�ϵͳ�����У����Ժ���ҡŶ��"));
		}else{
			redisUtil.set(Constant.SHAKING_KEY + member.getMemberId(), "true");	
		}
		Map<String, Object> resMap = activityApiService.shakeForGift(
				detailDto.getActId(), member.getMemberId());
		if ("0".equals((String) resMap.get("code"))) {
			ResultUtil.creComErrorResult((String) resMap.get("msg"));
		}
		redisUtil.del(Constant.SHAKING_KEY + member.getMemberId());//ҡһҡ������ɾ������
		return ajaxJsonP(request, response, ResultUtil.creObjSucResult(resMap));

	}

	/**
	 * ��ѯ�һ��
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
	 * ���ֶһ���Ʒ
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
					ResultUtil.creComErrorResult("���¼��һ���Ʒ��"));

		}
		if (StringUtils.isBlank(goodsNum) || StringUtils.isBlank(goods)) {
			return ajaxJsonP(request, response,
					ResultUtil.creComErrorResult("��ѡ��һ�����Ʒ��"));
		}
		//�һ���Ʒ
		Map<String, Object> resMap = activityApiService.exchangeGoods(memberId,
				goods, goodsNum);
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
