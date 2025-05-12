package eu.dreamlabs.usersservice.services;

import eu.dreamlabs.usersservice.domains.UserResponse;

public interface UserService {
    UserResponse getUserDetails(String userName, String password);
    UserResponse getUserDetails(String userName);
}
