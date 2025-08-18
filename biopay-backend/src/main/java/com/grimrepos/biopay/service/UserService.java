package com.grimrepos.biopay.service;

import com.grimrepos.biopay.dto.UserRegistrationRequest;
import com.grimrepos.biopay.dto.UserRegistrationResponse;

public interface UserService {
    UserRegistrationResponse registerNewUser(UserRegistrationRequest request);
}
