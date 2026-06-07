package br.com.fiap.firesearchlogic.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.issuer}")
    private String issuer;

    private static final String FIREBASE_JWKS_URL =
            "https://www.googleapis.com/service_accounts/v1/jwk/securetoken@system.gserviceaccount.com";

    public Claims extrairClaims(String token) {
        try {
            // Pega o kid do header do token
            String[] parts = token.split("\\.");
            String headerJson = new String(Base64.getUrlDecoder().decode(parts[0]));
            Map<?, ?> header = new ObjectMapper().readValue(headerJson, Map.class);
            String kid = (String) header.get("kid");

            // Busca chaves públicas do Firebase
            PublicKey publicKey = buscarChavePublica(kid);

            return Jwts.parser()
                    .verifyWith(publicKey)
                    .requireIssuer(issuer)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (Exception e) {
            throw new JwtException("Token Firebase inválido: " + e.getMessage());
        }
    }

    public boolean isTokenValido(String token) {
        try {
            extrairClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String extrairEmail(String token) {
        Claims claims = extrairClaims(token);
        String email = claims.get("email", String.class);
        if (email == null) email = claims.getSubject();
        return email;
    }

    private PublicKey buscarChavePublica(String kid) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(FIREBASE_JWKS_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        Map<?, ?> jwks = new ObjectMapper().readValue(response.body(), Map.class);
        var keys = (java.util.List<?>) jwks.get("keys");

        for (Object keyObj : keys) {
            Map<?, ?> keyMap = (Map<?, ?>) keyObj;
            if (kid.equals(keyMap.get("kid"))) {
                String n = (String) keyMap.get("n");
                String e = (String) keyMap.get("e");

                BigInteger modulus = new BigInteger(1, Base64.getUrlDecoder().decode(n));
                BigInteger exponent = new BigInteger(1, Base64.getUrlDecoder().decode(e));

                RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
                return KeyFactory.getInstance("RSA").generatePublic(spec);
            }
        }
        throw new JwtException("Chave pública não encontrada para kid: " + kid);
    }
}