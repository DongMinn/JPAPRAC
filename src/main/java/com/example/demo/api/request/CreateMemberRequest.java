package com.example.demo.api.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateMemberRequest {
    @NotEmpty
    private String name;
}
