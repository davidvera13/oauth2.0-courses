package eu.dreamlabs;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class PkceSample {
    public static void main(String[] args) {
        try {
            PKCEHelper pkce = new PKCEHelper();

            String codeVerifier = pkce.generateCodeVerifier();
            System.out.println("Pkce verifier generated: " + codeVerifier);

            String codeChallenge = pkce.generateCodeChallenge(codeVerifier);
            System.out.println("Pkce challenge generated: " + codeChallenge);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }
}