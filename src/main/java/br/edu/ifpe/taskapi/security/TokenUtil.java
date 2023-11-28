package br.edu.ifpe.taskapi.security;

import java.util.Collections;
import java.util.Date;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class TokenUtil {

    private static final String HEADER = "Authorization";
    private static final long EXPIRATION = 60*60*1000;
    private static final String EMISSOR = "Task-API";
    private static final String SECRET_KEY = "Wv4Ivz2R149!&$OqKZu5!64@vDu@Ihbg";
    private static final String PREFIX = "Bearer";

    public static String createToken(String subject) {

        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION);
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] secretKeyBytes = SECRET_KEY.getBytes();

        String token = Jwts.builder()
                .setSubject(subject)
                .setIssuer(EMISSOR)
                .setExpiration(expirationDate)
                .signWith(signatureAlgorithm,secretKeyBytes)
                .compact();

        return PREFIX + token;
    }

    public static boolean isExpirationValid(Date expiration){
       return expiration.after(new Date(System.currentTimeMillis()));
    }

    public static boolean isEmissorValid(String emissor){
        return emissor.equals(EMISSOR);
     }

    public static boolean isSubjectValid(String string){
        return string != null && string.length() > 0;
    }
    
  
    public static org.springframework.security.core.Authentication validate(HttpServletRequest request) {
        String token = request.getHeader(HEADER);
        token = token.replace(PREFIX,"");
    
        Claims claims = Jwts.parser()
            .setSigningKey(SECRET_KEY.getBytes())
            .parseClaimsJws(token)
            .getBody();
    
        String subject = claims.getSubject();
        String issuer = claims.getIssuer();
        Date expiration = claims.getExpiration();
    
        if (isSubjectValid(subject) && isEmissorValid(issuer) && isExpirationValid(expiration)) {
            return new UsernamePasswordAuthenticationToken(subject, null, Collections.emptyList());
        }
    
        return null;
    }

}
