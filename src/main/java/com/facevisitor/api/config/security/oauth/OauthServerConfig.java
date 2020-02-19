package com.facevisitor.api.config.security.oauth;

import com.facevisitor.api.config.security.SecurityUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class OauthServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final String SERVER_RESOURCE_ID = "face_visitor_oauth2";

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    SecurityUserDetailService securityUserDetailService;

    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;

    @Value("${secret.oauth.clientId}")
    String clientId;

    @Value("${secret.oauth.clientSecurity}")
    String clientSecurity;

    //AccessToken Expired 30 days
    int access_token_expired = 60 * 60 * 24 * 30;

    //Refresh Token Expired 30 days
    int refresh_token_expired = 60 * 60 * 24 * 30;

//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer security) {
//        security.passwordEncoder(passwordEncoder);
//    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(clientId)
                .secret(passwordEncoder.encode(clientSecurity))
                .accessTokenValiditySeconds(access_token_expired)
                .refreshTokenValiditySeconds(refresh_token_expired)
                .resourceIds(SERVER_RESOURCE_ID)
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("read","write");

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(securityUserDetailService)
                .accessTokenConverter(jwtAccessTokenConverter);
    }

}
