package com.sheldon.springoauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: Sheldon
 * @Date: 2018/09/24 10:11
 */

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userService;
    @Autowired
    private ClientDetailsService clientDetails;
    @Bean // 声明 ClientDetails实现
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }


    ///////////////////////////////////
    //////
    ///////////////////////////////////
    @Autowired
    private DataSource dataSource;
    @Bean // 声明TokenStore实现
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Autowired
    private TokenStore tokenStore;

    ///////////////////////////////////
    //////
    ///////////////////////////////////


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /*clients.inMemory()
                .withClient("hxjh")//客户端ID
                .authorizedGrantTypes("password", "refresh_token")//设置验证方式
                .scopes("read", "write")
                .secret("123456")
                .accessTokenValiditySeconds(3600) //token过期时间
                .refreshTokenValiditySeconds(-1); //refreshToken过期时间:-1永不过期
    */
        clients.jdbc(dataSource);

    }

    /*@Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userService); //配置userService 这样每次认证的时候会去检验用户是否锁定，有效等
    }*/

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
        endpoints.tokenStore(tokenStore());
        endpoints.userDetailsService(userService);
        endpoints.setClientDetailsService(clientDetails);
        //配置TokenServices参数
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.HOURS.toSeconds(1)); // 1h
        tokenServices.setRefreshTokenValiditySeconds(-1);
        endpoints.tokenServices(tokenServices);
    }


    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore);
        return tokenServices;
    }


}
