package com.osu.opr3.opr3project.security;

import com.osu.opr3.opr3project.jwt.JwtService;
import com.osu.opr3.opr3project.user.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class JwtControllerAdvice {

    private final JwtService jwtService;

    @ModelAttribute("jwtUser")
    public User getUserFromJwt(HttpServletRequest request){
        return jwtService.getUserFromRequest(request);
    }

}
