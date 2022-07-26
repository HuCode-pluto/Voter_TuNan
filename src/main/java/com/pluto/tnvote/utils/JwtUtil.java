package com.pluto.tnvote.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private String SECRET = "DyoonSecret_0581";

    public String getJwtToken(Map<String,Object> map,Date outDate){
        Date iatDate = new Date();
        Calendar nowTime = Calendar.getInstance();
        //有10天有效期
        nowTime.add(Calendar.DATE, 10);
        Date expiresDate = nowTime.getTime();
        Claims claims = Jwts.claims();

        for(Map.Entry<String, Object> m:map.entrySet()){
            claims.put(m.getKey(),m.getValue());
        }
        claims.setIssuedAt(new Date());
        claims.setExpiration(outDate);
        return  Jwts.builder().setClaims(claims).setExpiration(expiresDate).signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    public Claims parseJwtToken(String token) {
        Claims Claims = null;
        try{
            Jws<Claims> jws = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            String signature = jws.getSignature();
            Map<String, String> header = jws.getHeader();
            Claims = jws.getBody();
        }catch (Exception e){
            throw new RuntimeException("签名失败");
        }
        return Claims;
    }

}
