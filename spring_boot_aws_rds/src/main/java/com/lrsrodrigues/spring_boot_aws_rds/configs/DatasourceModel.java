package com.lrsrodrigues.spring_boot_aws_rds.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString
public class DatasourceModel {

    private String username;
    private String password;
    private String engine;
    private String host;
    private String port;
    private String dbInstanceIdentifier;
    private String url;

    public String getUrl() {
        return String.format("jdbc:postgresql://%s:%s/%s", host, port, username);
    }
}
