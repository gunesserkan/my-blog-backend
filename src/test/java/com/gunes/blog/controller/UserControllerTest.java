package com.gunes.blog.controller;

import com.gunes.blog.model.dto.CreateUserRequest;
import com.gunes.blog.model.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UserControllerTest {


    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    @DirtiesContext
    void shoudReturnLocationAndToken(){
        Set<Role> roles=new HashSet<>();
        roles.add(Role.ROLE_USER);
        CreateUserRequest request=CreateUserRequest.builder()
                .name("sezer")
                .username("sezer")
                .password("klm123")
                .email("sezer@gmail.com")
                .authorities(roles)
                .build();
        ResponseEntity<Void> response=restTemplate.postForEntity("/api/v1/auth/register", request, Void.class);
        //response status
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        //location of the created entity
        String location=response.getHeaders().getLocation().toString();
        URI expectedLocation;
        try {
             expectedLocation = new URI("http://localhost:" + port + "/api/v1/profiles/sezer");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        assertThat(location).isEqualTo(expectedLocation.toString());
        //token for the created entity
        String token = response.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        assertThat(token).isNotNull().startsWith("Bearer ");
    }
}