package xyz.javista.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.stereotype.Component;
import xyz.javista.core.domain.User;
import xyz.javista.mapper.UserMapper;
import xyz.javista.web.dto.UserDTO;

@Component
public class CustomTokenEnhancer extends TokenEnhancerChain {

    @Autowired
    UserMapper userMapper;

    @Override
    public CustomOAuth2Token enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        OAuth2AccessToken token = super.enhance(accessToken, authentication);
        CustomOAuth2Token customOAuth2Token = new CustomOAuth2Token(token);
        UserDTO user = userMapper.toDto((User) authentication.getUserAuthentication().getPrincipal());
        customOAuth2Token.getAdditionalInformation().put("user", user);
        return customOAuth2Token;
    }
}
