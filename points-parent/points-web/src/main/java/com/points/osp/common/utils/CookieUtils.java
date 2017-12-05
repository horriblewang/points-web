package com.points.osp.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import com.points.osp.common.config.SpringContextHolder;

public class CookieUtils
{
  public static void setCookie(HttpServletResponse response, String name, String value)
  {
    setCookie(response, name, value, 86400);
  }

  public static void setCookie(HttpServletResponse response, String name, String value, int maxAge){
    Cookie cookie = new Cookie(name, null);
    if (StringUtils.isNotBlank(SpringContextHolder.getApplicationContext().getApplicationName()))
      cookie.setPath(SpringContextHolder.getApplicationContext().getApplicationName());
    else {
      cookie.setPath("/");
    }
    cookie.setMaxAge(maxAge);
    try {
      cookie.setValue(URLEncoder.encode(value, "utf-8"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    response.addCookie(cookie);
  }

  public static String getCookie(HttpServletRequest request, String name)
  {
    return getCookie(request, null, name, false);
  }

  public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name)
  {
    return getCookie(request, response, name, true);
  }

  public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name, boolean isRemove)
  {
    String value = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (!cookie.getName().equals(name)) continue;
        try {
          value = URLDecoder.decode(cookie.getValue(), "utf-8");
        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        }
        if (isRemove) {
          cookie.setMaxAge(0);
          response.addCookie(cookie);
        }
      }
    }

    return value;
  }
}