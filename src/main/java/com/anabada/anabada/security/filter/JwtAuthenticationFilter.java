package com.anabada.anabada.security.filter;

import com.anabada.anabada.security.model.UserDetailsImpl;
import com.anabada.anabada.security.jwt.JwtUtil;
import com.anabada.anabada.security.model.entity.UserRoleEnum;
import com.anabada.anabada.security.model.request.LoginRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUserid(),
                            requestDto.getUserpw(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException{
        log.info("로그인 성공 및 JWT 생성");
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        List<? extends GrantedAuthority> authorities = new ArrayList<>(((UserDetailsImpl) authResult.getPrincipal()).getAuthorities());
        UserRoleEnum role = UserRoleEnum.USER;

        String token = jwtUtil.createToken(username, role);
        jwtUtil.addJwtToCookie(token, response);
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write("로그인에 성공하였습니다.");
    }

    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException{
        log.info("로그인 실패");
        response.setStatus(400);
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write("회원을 찾을 수 없습니다.");
    }
}

