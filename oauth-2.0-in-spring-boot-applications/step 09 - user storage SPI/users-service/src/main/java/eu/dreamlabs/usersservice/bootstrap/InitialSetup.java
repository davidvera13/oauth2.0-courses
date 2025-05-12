package eu.dreamlabs.usersservice.bootstrap;

import eu.dreamlabs.usersservice.data.UserEntity;
import eu.dreamlabs.usersservice.data.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitialSetup {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if(userRepository.findByEmail("test@test.com") == null){
            UserEntity user = new UserEntity(
                    "qswe3mg84mfjtu",
                    "David",
                    "Vera",
                    "test@test.com",
                    bCryptPasswordEncoder.encode("12345678"),
                    "",
                    false);

            userRepository.save(user);
        }
    }
}
