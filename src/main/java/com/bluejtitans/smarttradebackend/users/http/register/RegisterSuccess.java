package com.bluejtitans.smarttradebackend.users.http.register;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterSuccess implements RegisterResponse {
    private String email;
}
