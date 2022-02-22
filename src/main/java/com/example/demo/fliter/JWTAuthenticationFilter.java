package com.example.demo.fliter;

import com.example.demo.entity.sys.JwtUser;
import com.example.demo.entity.sys.LoginUser;
import com.example.demo.utils.JwtTokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author 李昕
 * @date 2021/12/22 12:39
 * JWTAuthenticationFilter继承于UsernamePasswordAuthenticationFilter
 * 该拦截器用于获取用户登录的信息，只需创建一个token并调用authenticationManager.authenticate()
 * 让spring-security去进行验证就可以了，不用自己查数据库再对比密码了，这一步交给spring去操作。
 * 这个操作有点像是shiro的subject.login(new UsernamePasswordToken())，验证的事情交给框架。
 *
 */
@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
        // 设置登录路径
        super.setFilterProcessesUrl("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 从输入流中获取登录信息
        try{
            LoginUser loginUser = new ObjectMapper().readValue(request.getInputStream(), LoginUser.class);
            log.info(loginUser.toString());
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(),loginUser.getPassword(),new ArrayList<>()));

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 验证成功后进入的方法,需要产生token返回
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 查看源代码会发现调用getPrincipal()方法会返回一个实现了`UserDetails`接口的对象
        // 所以就是JwtUser啦
        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
        String role = "";
        Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
        // 只有一个角色，暂时先这么办了
        for (GrantedAuthority authority : authorities) {
            role = authority.getAuthority();
        }
        String token = JwtTokenUtils.createToken(jwtUser.getUsername(),role,false);
        // 返回创建成功的token
        // 但是这里创建的token只是单纯的token
        // 按照jwt的规定，最后请求的格式应该是 `Bearer token`
        response.setHeader("Access-Control-Expose-Headers","token");
        response.setHeader("token",JwtTokenUtils.TOKEN_PREFIX+token);
        response.getOutputStream().write((JwtTokenUtils.TOKEN_PREFIX+token).getBytes());
    }

    /**
     * 失败时调用的方法
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.getOutputStream().write("用户名或密码错误".getBytes());
    }
}
