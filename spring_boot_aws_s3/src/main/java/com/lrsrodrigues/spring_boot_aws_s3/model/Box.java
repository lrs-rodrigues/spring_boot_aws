package com.lrsrodrigues.spring_boot_aws_s3.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Box {

    private String fileName;
    private String ETag;
    private long size;
    private String fileUrl;

}
