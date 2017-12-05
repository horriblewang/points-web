package com.points.osp.controller.api;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bailian.dto.MemberDto;
import com.bailian.entity.MemberInfo;
import com.bailian.entity.User;
import com.bailian.utils.DateUtils;
import com.bailian.utils.MD5;
import com.bailian.utils.ResultUtil;
import com.google.common.collect.Maps;
import com.points.osp.common.service.MemberService;
import com.points.osp.common.service.SystemService;
import com.points.osp.common.service.UserApiService;
import com.points.osp.common.utils.Constant;
import com.points.osp.common.utils.RedisUtil;
import com.points.osp.controller.web.BaseController;

@Controller
@RequestMapping("api/login")
public class LoginApiController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(LoginApiController.class);

	@Autowired
	private UserApiService userApiService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private SystemService systemService;

	@Autowired
	private RedisUtil redisUtil;
	
	
	/**
	 * �ж��Զ���½
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/autoLogin")
	public String autoLogin(User user, HttpServletRequest request, HttpServletResponse response) {
		String nonce = request.getParameter("nonce");
		String memberId = redisUtil.get(Constant.LOGIN_NONCE_KEY + nonce, "");
		if(StringUtils.isNotBlank(memberId)){
			MemberInfo memberInfo = memberService.getMember(Long.valueOf(memberId));
			return ajaxJsonP(request, response, ResultUtil.creObjSucResult(memberInfo));
		}else{
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult());
		}

	}

	/**
	 * ��½
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/loginIn")
	public String loginIn(User user, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isBlank(user.getMobile()) || StringUtils.isBlank(user.getPassWord())) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("�������û��������룡"));
		}
		Map<String, Object> loginMap = userApiService.loginIn(user);

		if ("1".equals(loginMap.get("code").toString())) {
			String nonce = getNonce();
			MemberInfo memberInfo = memberService.getMember((Long) loginMap.get("memberId"));
			memberInfo.setPassword(nonce);
			redisUtil.set(Constant.LOGIN_NONCE_KEY + nonce, memberInfo.getMemberId().toString(), 24*3600);//һ���¼��Ч
			return ajaxJsonP(request, response, ResultUtil.creObjSucResult(memberInfo));
		} else {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult(loginMap.get("msg").toString()));
		}

	}

	/**
	 *  �ӿڱ�ʾ
	 * @return
	 */
	private String getNonce() {
		// TODO Auto-generated method stub
		String date = DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_HHMMSS);
		return MD5.getMD5((date + UUID.randomUUID().toString()).getBytes());
	}

	/**
	 * ע��
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/regist")
	public String regist(User user, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isBlank(user.getMobile()) || StringUtils.isBlank(user.getPassWord())) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("�������û��������룡"));
		}
		String verifyCode = request.getParameter("veryCode");
		if (StringUtils.isBlank(verifyCode)) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("�������ֻ���֤�룡"));
		}

		// ��������û����֤��
		String sendCode = redisUtil.get(Constant.VERIFY_CODE_KEY + user.getMobile(), "");
		if (StringUtils.isBlank(sendCode)) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("�ֻ���֤���ѹ��ڣ������»�ȡ��"));
		}

		// ������֤���Ƿ���ȷ
		if (!verifyCode.equals(sendCode)) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("�ֻ���֤�����"));
		}

		Map<String, Object> loginMap = userApiService.regist(user);
		if ("1".equals((String) loginMap.get("code"))) {
			return ajaxJsonP(request, response, ResultUtil.creObjSucResult((MemberInfo) loginMap.get("memberInfo")));
		} else {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult((String) loginMap.get("msg")));
		}

	}

	/**
	 * ��������
	 * 
	 * @param memberDto
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("resetPass")
	public String modifyPwd(MemberDto memberDto, HttpServletRequest request, HttpServletResponse response) {

		String newPassword = memberDto.getNewpassword();

		String verifyCode = request.getParameter("verifyCode");

		String mobile = memberDto.getMobile();

		if (StringUtils.isBlank(mobile)) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("û�в鵽��½��Ϣ��"));
		}

		if (StringUtils.isBlank(newPassword)) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("�û������벻��Ϊ�գ�"));
		}

		if (StringUtils.isBlank(verifyCode)) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("��֤�벻��Ϊ�գ�"));
		}

		// ���͵���֤��
		String sendCode = redisUtil.get(Constant.VERIFY_CODE_KEY + mobile, "");

		if (!sendCode.equals(verifyCode)) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("��֤�벻��ȷ��"));
		}

		User user = systemService.getLoginUser(memberDto.getMobile());

		if (user == null) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("�û������ڣ�"));
		}

		systemService.updatePasswordById(user.getUserId(), newPassword);

		return ajaxJsonP(request, response, ResultUtil.creComSucResult());

	}

	/**
	 * ���Ͷ�����֤��
	 * 
	 * @param memberDto
	 * @param response
	 * @return
	 */
	@RequestMapping("/sendVerifyCode")
	public String sendVerifyCode(MemberDto memberDto, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isBlank(memberDto.getMobile())) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("�ֻ����벻��Ϊ�գ�"));
		}
		if (!isMobileNO(memberDto.getMobile())) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("�ֻ������ʽ����"));
		}
		try {
			String ImageCode = memberDto.getImageCode();
			String sendCode = redisUtil.get(Constant.IMAGE_CODE + memberDto.getTimeCode(), "");
			if(StringUtils.isBlank(ImageCode) || !sendCode.equals(ImageCode)){
				return ajaxJsonP(request, response, ResultUtil.creComErrorResult("ͼ����֤�����"));	
			}
			String code = userApiService.sendSmsVerifyCode(memberDto.getMobile());
			if ("error".equals(code)) {
				return ajaxJsonP(request, response, ResultUtil.creComErrorResult("��֤�뷢��ʧ�ܣ����Ժ����ԣ�"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("������֤��ʧ��", e);
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("��֤�뷢��ʧ�ܣ����Ժ����ԣ�"));
		}
		return ajaxJsonP(request, response, ResultUtil.creComSucResult());
	}

	public boolean isMobileNO(String mobile) {
		Pattern p = Pattern.compile("^1[345678][0-9]{9}$");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}

	/**
	 * �޸�����
	 * 
	 * @param memberDto
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("modifyPass")
	public String modifyPass(MemberDto memberDto, HttpServletRequest request, HttpServletResponse response) {

		String newPassword = memberDto.getNewpassword();

		String oldPassword = memberDto.getOldpassword();

		Long memberId = memberDto.getMemberId();

		if (memberId == null) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("û�в鵽��½��Ϣ��"));
		}

		if (StringUtils.isBlank(newPassword)) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("�û������벻��Ϊ�գ�"));
		}

		if (StringUtils.isBlank(oldPassword)) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("�û������벻��Ϊ�գ�"));
		}
		MemberInfo member = memberService.getMember(memberId);
		if (member == null) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("û�в鵽��½��Ϣ��"));
		}
		User user = systemService.getLoginUser(member.getMobile());
		if (user.getPassWord().equals(MD5.getMD5(oldPassword.getBytes()))) {
			systemService.updatePasswordById(user.getUserId(), newPassword);
		} else {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("�û��������������"));
		}

		return ajaxJsonP(request, response, ResultUtil.creComSucResult());

	}

}
