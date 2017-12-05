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
 * ��Ա����ӿ�
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
	 * ��Ա����
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
					ResultUtil.creComErrorResult("��Ա��źͷ�����Ϣ����Ϊ��"));

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
	 * �ҵ���Ϣ
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
			ajaxJsonP(request, response, ResultUtil.creComErrorResult("���¼��"));
		}
		Map<String, Object> resMap = memApiService.myBaseInfo(memberId);
		if("0".equals(resMap.get("code"))){
			ajaxJsonP(request, response, ResultUtil.creComErrorResult((String)resMap.get("msg")));
		}
		return ajaxJsonP(request, response, ResultUtil.creObjSucResult(resMap));
	}

}
