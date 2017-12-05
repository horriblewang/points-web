package com.points.osp.controller.api;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bailian.entity.FeedBack;
import com.bailian.entity.MemberInfo;
import com.bailian.utils.ResultUtil;
import com.points.osp.common.service.MemApiService;
import com.points.osp.controller.web.BaseController;

/**
 * 会员对外接口
 * 
 * @author wbwangsh
 *
 */
@Controller
@RequestMapping("/api/member")
public class MemApiController extends BaseController {

	@Autowired
	private MemApiService memApiService;

	/**
	 * 会员反馈
	 * 
	 * @param feedBack
	 * @return
	 */
	@RequestMapping("/feedBack")
	public String feedBack(FeedBack feedBack,
			HttpServletRequest request, HttpServletResponse response) {

		if (feedBack.getMemberId() == null
				|| StringUtils.isBlank(feedBack.getRemark())) {
			return ajaxJsonP(request, response,
					ResultUtil.creComErrorResult("会员编号和反馈信息不能为空"));

		}
		String remark = "";
		try {
			remark = URLDecoder.decode(feedBack.getRemark(), "utf-8");
			feedBack.setRemark(remark);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		System.out.println("$$$$$$$$$$$" + remark);

		memApiService.feedBack(feedBack);

		return ajaxJsonP(request, response, ResultUtil.creComSucResult());
	}
	
	/**
	 * 我的信息
	 * @param memberInfo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("myInfo")
	public String myInfo(MemberInfo memberInfo,
			HttpServletRequest request, HttpServletResponse response){
		Long memberId = memberInfo.getMemberId();
		if(memberId == null){
			ajaxJsonP(request, response, ResultUtil.creComErrorResult("请登录！"));
		}
		Map<String, Object> resMap = memApiService.myBaseInfo(memberId);
		if("0".equals(resMap.get("code"))){
			ajaxJsonP(request, response, ResultUtil.creComErrorResult((String)resMap.get("msg")));
		}
		return ajaxJsonP(request, response, ResultUtil.creObjSucResult(resMap));
	}

}
