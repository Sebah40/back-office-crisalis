package com.orange.Crisalis.security.Service;

import com.orange.Crisalis.security.jwt.JwtProvider;
import org.springframework.stereotype.Service;

@Service
public class JWTService {
    private final JwtProvider jwtProvider;

    public JWTService(JwtProvider jwtProvider){
        this.jwtProvider = jwtProvider;
    }

    public String getUsernameByToken(String token){

        return jwtProvider.getUsernameFromToken(token);
    }





    public String getTokenByHeaders(String authorizationHeader){

        return authorizationHeader.substring(6);
    }

}
