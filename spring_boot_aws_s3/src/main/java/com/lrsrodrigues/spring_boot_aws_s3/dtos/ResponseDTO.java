package com.lrsrodrigues.spring_boot_aws_s3.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseDTO {

    private String message;
    private String boxName;

}
