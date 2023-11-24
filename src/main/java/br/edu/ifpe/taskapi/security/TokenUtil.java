package br.edu.ifpe.taskapi.security;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class TokenUtil {

    private static final long EXPIRATION = 60 * 60 * 1000;
    private static final String EMISSOR = "task_api";
    private static final String SECRET_KEY = "Vw4I2i1R169!&@OqK#u5!64@vDu@Ihbg";
    private static final String PREFIX = "Bearer";

    public static String createToken(String subject, List<String> permissions) {
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION);
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] secretKeyBytes = SECRET_KEY.getBytes();

        Claims claims = Jwts.claims().setSubject(subject);
        claims.put("permissions", permissions);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(EMISSOR)
                .setExpiration(expirationDate)
                .signWith(signatureAlgorithm, secretKeyBytes)
                .compact();

        return PREFIX + token;
    }

    public static Claims extractClaims(String token) {
        token = token.replace(PREFIX, "");

        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new IllegalArgumentException("Token inválido");
        }
    }

    public static boolean isExpirationValid(Date expiration) {
        return expiration.after(new Date(System.currentTimeMillis()));
    }

    public static boolean isEmissorValid(String emissor) {
        return emissor.equals(EMISSOR);
    }

    public static boolean isSubjectValid(String subject) {
        return subject != null && !subject.isEmpty();
    }

    public static Authentication validate(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith(PREFIX)) {
            Claims claims = extractClaims(token);

            String subject = claims.getSubject();
            String issuer = claims.getIssuer();
            Date expiration = claims.getExpiration();

            @SuppressWarnings("unchecked")
			List<String> permissions = (List<String>) claims.get("permissions");

            if (isSubjectValid(subject) && isEmissorValid(issuer) && isExpirationValid(expiration)) {
                List<GrantedAuthority> authorities = permissions.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                return new UsernamePasswordAuthenticationToken(subject, null, authorities);
            }
        }

        return null;
    }
}