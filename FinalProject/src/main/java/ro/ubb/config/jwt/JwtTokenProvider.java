package ro.ubb.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.config.MyUserDetails;
import ro.ubb.model.User;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private long validityInMiliseconds;
    private SecretKey secretKey;

    @Autowired
    private MyUserDetails myUserDetails;

    @PostConstruct
    protected void init() throws NoSuchAlgorithmException {
        byte[] decodedKey = "secretsecretsecretsecretsecretsecretsecretsecretsecret".getBytes();
        secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
        validityInMiliseconds = Long.parseLong("99999999");
    }

    public String createToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMiliseconds);
        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity).signWith(secretKey, SignatureAlgorithm.HS256).compact();
    }

    @Transactional
    public Authentication getAuthentication(String token) {
        User user = myUserDetails.loadUser(getUsername(token));
        initializeProxies(user);
        UserDetails userDetails = myUserDetails.getUserDetails(user);
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
        return true;
    }

    public void initializeProxies(User user) {
    }

}
