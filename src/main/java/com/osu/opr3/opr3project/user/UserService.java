package com.osu.opr3.opr3project.user;

import com.osu.opr3.opr3project.security.AuthRequest;
import com.osu.opr3.opr3project.security.AuthResponse;
import com.osu.opr3.opr3project.security.RegistrationRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.Optional;

public interface UserService {

    User getByEmail(String email);

    AuthResponse registerUser(RegistrationRequest request);
    AuthResponse authenticate(AuthRequest request);
    void refreshToken(HttpServletRequest request,
                      HttpServletResponse response) throws IOException;
}
