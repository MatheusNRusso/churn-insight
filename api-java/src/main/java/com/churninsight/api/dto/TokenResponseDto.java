package com.churninsight.api.dto;

public record TokenResponseDto(
        String token,
        String tokenType
) {}
