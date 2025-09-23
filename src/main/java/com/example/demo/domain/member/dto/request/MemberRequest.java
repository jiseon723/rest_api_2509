package com.example.demo.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
