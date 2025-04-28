package eu.dreamlabs.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
@RequiredArgsConstructor
public class OauthServerApplication implements CommandLineRunner {
    private final Environment env;

    public static void main(String[] args) {
        SpringApplication.run(OauthServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("GOOGLE_CLIENT_ID: " + env.getProperty("GOOGLE_CLIENT_ID", "Not Set"));
        System.out.println("GOOGLE_CLIENT_SECRET: " + env.getProperty("GOOGLE_CLIENT_SECRET", "Not Set"));
        System.out.println("FACEBOOK_CLIENT_ID: " + env.getProperty("FACEBOOK_CLIENT_ID", "Not Set"));
        System.out.println("FACEBOOK_CLIENT_SECRET: " + env.getProperty("FACEBOOK_CLIENT_SECRET", "Not Set"));

    }
}
