package com.xxx.demo.frame.init;


import com.xxx.demo.dto.sys.UserInfo;
import com.xxx.demo.exception.LoginRequiredException;
import com.xxx.demo.frame.annotation.LoginRequired;
import com.xxx.demo.frame.constants.CurrentUserConstants;
import com.xxx.demo.frame.util.TokenUtils;
import com.xxx.demo.models.sys.SysUser;
import com.xxx.demo.models.sys.TsUser;
import com.xxx.demo.services.user.SysUserService;
import com.xxx.demo.services.user.TsUserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @description:Token验证过滤器,判断是否已登录
 * @author:@leo.
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    public final static String ACCESS_TOKEN = "accessToken";
    //@Autowired
    //private SysUserService userService;
    @Autowired
    private TsUserService tsUserService;

    /**
     *
     * 在请求处理之前进行调用（Controller方法调用之前）
     *
     * @param request
     * @param response
     * @param handler
     * @return
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws LoginRequiredException {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 判断接口是否需要登录
        LoginRequired methodAnnotation = method.getAnnotation(LoginRequired.class);
        // 有 @LoginRequired 注解，需要认证
        if (methodAnnotation != null) {
            // 判断是否存在令牌信息，如果存在，则允许登录
            //String accessToken = request.getParameter(ACCESS_TOKEN);
            String accessToken = request.getHeader(ACCESS_TOKEN);
            if (null == accessToken) {
                //throw new RuntimeException("无token，请重新登录");
                throw new LoginRequiredException("无token，请重新登录");
            }
            Claims claims = TokenUtils.parseJWT(accessToken);
            String phoneNum = claims.getId();
            UserInfo user = tsUserService.findEffctiveUserInfoByPhoneNum(phoneNum);
            if (user == null) {
                //throw new RuntimeException("用户不存在，请重新登录");
                throw new LoginRequiredException("用户不存在，请重新登录");
            }
            // 当前登录用户@CurrentUser
            request.setAttribute(CurrentUserConstants.CURRENT_USER, user);
            return true;
        }else{
            return true;
        }
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {

    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

    }

}
