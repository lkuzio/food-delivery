package xyz.javista.config

import org.springframework.http.client.ClientHttpResponse
import org.springframework.security.oauth2.client.http.OAuth2ErrorHandler
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails

class CustomOAuth2ErrorHandler extends OAuth2ErrorHandler {
    CustomOAuth2ErrorHandler(OAuth2ProtectedResourceDetails resource) {
        super(resource)
    }

    @Override
    boolean hasError(ClientHttpResponse response) throws IOException {
        return false
    }
}
