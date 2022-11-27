package br.com.cursomicroservices.productapi.modules.jwt.services;

import br.com.cursomicroservices.productapi.config.exceptions.AuthenticationException;
import br.com.cursomicroservices.productapi.modules.jwt.interfaces.JwtResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class JwtService {

  private static final String bearer = "Bearer ";

  @Value("${app.config.secrets.api-secret}")
  private String apiSecret;

  public void validateAuthorization(String token) {
    var accessToken = extractToken(token);

    try {
      var claims = Jwts
        .parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(apiSecret.getBytes()))
        .build()
        .parseClaimsJws(accessToken)
        .getBody();
      
      var user = JwtResponse.getUser(claims);

      if (isEmpty(user) || isEmpty(user.getId())) {
        throw new AuthenticationException("The user is not valid.");
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new AuthenticationException("Error while trying to process the Access Token.");
    }
  }

  private String extractToken(String token) {
    if (isEmpty(token)){
      throw new AuthenticationException("The access token was not informed.");
    }

    if (token.contains(bearer)) {
      token = token.replace(bearer, String.EMPTY);
    }

    return token;
  }
}
