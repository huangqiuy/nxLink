package com.example.test.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author nn
 */
@Data
@Component
public class AccessToken {
    @Value("${ticket.grant_type}")
    private String grantType;
    @Value("${ticket.scope}")
    private String scope;
    @Value("${ticket.domain_id}")
    private String domainId;
    @Value("${ticket.org_id}")
    private String orgId;
    @Value("${ticket.client_id}")
    private String clientId;
    @Value("${ticket.client_secret}")
    private String clientSecret;
}
