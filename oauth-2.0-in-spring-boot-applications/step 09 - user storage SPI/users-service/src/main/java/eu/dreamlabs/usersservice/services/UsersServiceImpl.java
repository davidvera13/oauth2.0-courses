package eu.dreamlabs.usersservice.services;

import eu.dreamlabs.usersservice.data.UserEntity;
import eu.dreamlabs.usersservice.data.UserRepository;
import eu.dreamlabs.usersservice.domains.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserResponse getUserDetails(String userName) {
        UserResponse returnValue = new UserResponse();

        UserEntity userEntity = userRepository.findByEmail(userName);
        if (userEntity == null) {
            return returnValue;
        }

        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserResponse getUserDetails(String userName, String password) {
        UserResponse returnValue = null;

        UserEntity userEntity = userRepository.findByEmail(userName);
        if (userEntity == null) {
            return null;
        }

        if (bCryptPasswordEncoder.matches(password,
                userEntity.getEncryptedPassword())) {
            System.out.println("password matches!!!");

            returnValue = new UserResponse();
            BeanUtils.copyProperties(userEntity, returnValue);
        }
        return returnValue;
    }

}