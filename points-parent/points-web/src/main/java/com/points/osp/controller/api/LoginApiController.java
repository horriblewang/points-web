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
	 * 判断自动登陆
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
	 * 登陆
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/loginIn")
	public String loginIn(User user, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isBlank(user.getMobile()) || StringUtils.isBlank(user.getPassWord())) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("请输入用户名和密码！"));
		}
		Map<String, Object> loginMap = userApiService.loginIn(user);

		if ("1".equals(loginMap.get("code").toString())) {
			String nonce = getNonce();
			MemberInfo memberInfo = memberService.getMember((Long) loginMap.get("memberId"));
			memberInfo.setPassword(nonce);
			redisUtil.set(Constant.LOGIN_NONCE_KEY + nonce, memberInfo.getMemberId().toString(), 24*3600);//一天登录有效
			return ajaxJsonP(request, response, ResultUtil.creObjSucResult(memberInfo));
		} else {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult(loginMap.get("msg").toString()));
		}

	}

	/**
	 *  接口标示
	 * @return
	 */
	private String getNonce() {
		// TODO Auto-generated method stub
		String date = DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_HHMMSS);
		return MD5.getMD5((date + UUID.randomUUID().toString()).getBytes());
	}

	/**
	 * 注册
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/regist")
	public String regist(User user, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isBlank(user.getMobile()) || StringUtils.isBlank(user.getPassWord())) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("请输入用户名和密码！"));
		}
		String verifyCode = request.getParameter("veryCode");
		if (StringUtils.isBlank(verifyCode)) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("请输入手机验证码！"));
		}

		// 看看还有没有验证码
		String sendCode = redisUtil.get(Constant.VERIFY_CODE_KEY + user.getMobile(), "");
		if (StringUtils.isBlank(sendCode)) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("手机验证码已过期，请重新获取！"));
		}

		// 看看验证码是否正确
		if (!verifyCode.equals(sendCode)) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("手机验证码错误！"));
		}

		Map<String, Object> loginMap = userApiService.regist(user);
		if ("1".equals((String) loginMap.get("code"))) {
			return ajaxJsonP(request, response, ResultUtil.creObjSucResult((MemberInfo) loginMap.get("memberInfo")));
		} else {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult((String) loginMap.get("msg")));
		}

	}

	/**
	 * 密码重置
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
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("没有查到登陆信息！"));
		}

		if (StringUtils.isBlank(newPassword)) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("用户新密码不能为空！"));
		}

		if (StringUtils.isBlank(verifyCode)) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("验证码不能为空！"));
		}

		// 发送的验证码
		String sendCode = redisUtil.get(Constant.VERIFY_CODE_KEY + mobile, "");

		if (!sendCode.equals(verifyCode)) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("验证码不正确！"));
		}

		User user = systemService.getLoginUser(memberDto.getMobile());

		if (user == null) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("用户不存在！"));
		}

		systemService.updatePasswordById(user.getUserId(), newPassword);

		return ajaxJsonP(request, response, ResultUtil.creComSucResult());

	}

	/**
	 * 发送短信验证码
	 * 
	 * @param memberDto
	 * @param response
	 * @return
	 */
	@RequestMapping("/sendVerifyCode")
	public String sendVerifyCode(MemberDto memberDto, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isBlank(memberDto.getMobile())) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("手机号码不能为空！"));
		}
		if (!isMobileNO(memberDto.getMobile())) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("手机号码格式错误！"));
		}
		try {
			String ImageCode = memberDto.getImageCode();
			String sendCode = redisUtil.get(Constant.IMAGE_CODE + memberDto.getTimeCode(), "");
			if(StringUtils.isBlank(ImageCode) || !sendCode.equals(ImageCode)){
				return ajaxJsonP(request, response, ResultUtil.creComErrorResult("图形验证码错误！"));	
			}
			String code = userApiService.sendSmsVerifyCode(memberDto.getMobile());
			if ("error".equals(code)) {
				return ajaxJsonP(request, response, ResultUtil.creComErrorResult("验证码发送失败，请稍后重试！"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("发送验证码失败", e);
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("验证码发送失败，请稍后重试！"));
		}
		return ajaxJsonP(request, response, ResultUtil.creComSucResult());
	}

	public boolean isMobileNO(String mobile) {
		Pattern p = Pattern.compile("^1[345678][0-9]{9}$");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}

	/**
	 * 修改密码
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
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("没有查到登陆信息！"));
		}

		if (StringUtils.isBlank(newPassword)) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("用户新密码不能为空！"));
		}

		if (StringUtils.isBlank(oldPassword)) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("用户旧密码不能为空！"));
		}
		MemberInfo member = memberService.getMember(memberId);
		if (member == null) {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("没有查到登陆信息！"));
		}
		User user = systemService.getLoginUser(member.getMobile());
		if (user.getPassWord().equals(MD5.getMD5(oldPassword.getBytes()))) {
			systemService.updatePasswordById(user.getUserId(), newPassword);
		} else {
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("用户旧密码输入错误！"));
		}

		return ajaxJsonP(request, response, ResultUtil.creComSucResult());

	}

}
