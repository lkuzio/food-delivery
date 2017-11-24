package xyz.javista.config.security;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import xyz.javista.web.dto.UserDTO;

import java.util.Objects;

public class CustomOAuth2Token extends DefaultOAuth2AccessToken {

    private UserDTO user;


    public CustomOAuth2Token(OAuth2AccessToken accessToken) {
        super(accessToken);
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CustomOAuth2Token that = (CustomOAuth2Token) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user);
    }
}
