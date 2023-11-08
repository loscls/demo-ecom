package demoecom.ecommerce.services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import demoecom.ecommerce.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtService {

    private static final String SECRET_KEY = "58703273357638792F423F4528482B4D6251655468576D597133743677397A24";

    public String extractEmailFromToken(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }
    
    public String extractEmailFromRequest(HttpServletRequest request) {
        
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken = authHeader.substring(7); 

        return extractEmailFromToken(jwtToken);
    }

    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaim(jwtToken);
        return claimResolver.apply(claims);
    }

    public String generateToken(User user) {
        return generateToken(new HashMap<>(), user);
    }

    
    public String generateToken(Map<String, Object> extraClaim, User user) {
        extraClaim.put("ROLE", user.getRole());
        
        return Jwts
            .builder()
            .setClaims(extraClaim)
            .setSubject(user.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
    }
    
    public boolean isTokenValid(String jwtToken, User user) {
        final String userEmail = extractEmailFromToken(jwtToken);

        return (userEmail.equals(user.getUsername()) && !isTokenExpired(jwtToken));
    }

    public boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    public Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }


    private Claims extractAllClaim(String jwtToken) {
        return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(jwtToken)
        .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    
}
