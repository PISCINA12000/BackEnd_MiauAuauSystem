package miau.auau.amigosdequatropatas.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JWTTokenProvider {
    private static final SecretKey CHAVE = Keys.hmacShaKeyFor(
            "MINHACHAVESECRETA_MINHACHAVESECRETA".getBytes(StandardCharsets.UTF_8));

    static public String getToken(String usuario,String privilegio, int cod_usuario)
    {
        String jwtToken = Jwts.builder()
        .setSubject("usuario")
        .setIssuer("localhost:8080")
        .claim("usuario", usuario)
        .claim("privilegio", privilegio)
        .claim("cod_usuario", cod_usuario)
        .setIssuedAt(new Date())
        .setExpiration(Date.from(LocalDateTime.now().plusMinutes(15L)
                .atZone(ZoneId.systemDefault()).toInstant()))
        .signWith(CHAVE)
        .compact();

        return jwtToken;
    }

    static public boolean verifyToken(String token)
    {
        try {
            Claims claims = JWTTokenProvider.getAllClaimsFromToken(token);
            if (claims.get("privilegio").equals("A") || claims.get("privilegio").equals("C")) // necessita estar logado
            {
                Jwts.parserBuilder()
                .setSigningKey(CHAVE)
                .build()
                .parseClaimsJws(token).getSignature();
                return true;
            }

       } catch (Exception e) {
                System.out.println(e);
       }
       return false;       
    }

    static public Claims getAllClaimsFromToken(String token) 
    {
        Claims claims=null;
        try {
            claims = Jwts.parserBuilder()
            .setSigningKey(CHAVE)
            .build()
            .parseClaimsJws(token)
            .getBody();
        } catch (Exception e) {
            System.out.println("Erro ao recuperar as informações (claims)");
        }
        return claims;        
    }

}
