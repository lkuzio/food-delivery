package xyz.javista.web.controller

import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails
import org.springframework.test.annotation.Rollback
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll
import xyz.javista.config.CustomOAuth2ErrorHandler
import xyz.javista.config.CustomPageableResponse
import xyz.javista.web.command.CreateOrderCommand
import xyz.javista.web.controlleradvice.ValidationErrorDTO
import xyz.javista.web.dto.OrderDTO

import javax.transaction.Transactional

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class OrderControllerTest extends Specification {

    @LocalServerPort
    int randomServerPort

    def URL = ""

    OAuth2RestTemplate restTemplate

    @Shared
    String toLongName = toLongString(256)

    void setup() {
        this.URL = "http://localhost:" + randomServerPort
        ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails()
        resourceDetails.setUsername("test")
        resourceDetails.setPassword("11111111111111111111111111111111111111111")
        resourceDetails.setAccessTokenUri(URL + "/oauth/token")
        resourceDetails.setClientId("asdasdasd")
        resourceDetails.setClientSecret("asdasdasd")
        resourceDetails.setGrantType("password")
        resourceDetails.setScope(Arrays.asList("read", "write"))

        DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext()

        this.restTemplate = new OAuth2RestTemplate(resourceDetails, clientContext)
        this.restTemplate.setErrorHandler(new CustomOAuth2ErrorHandler())
    }

    void cleanup() {

    }

    @Transactional
    @Rollback
    def "CreateOrder should pass"() {

        when:
        def response = restTemplate.postForEntity(URL + "/orders", createOrder("MgFonald", "2017-11-14 10:46", "Fast and delicious food"), OrderDTO)

        then:
        response.statusCode == HttpStatus.OK

    }


    @Unroll
    def "CreateOrder request fail due to #fieldErrorMessage"() {
        given:
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON))
        HttpEntity<ValidationErrorDTO> entity = new HttpEntity<>(headers)

        when:
        def response = restTemplate.postForEntity(URL + "/orders", createOrder(restaurantName, endDateTime, description), ValidationErrorDTO)

        then:
        response.statusCode == HttpStatus.BAD_REQUEST
        response.body.message == message
        response.body.fieldErrors.size() == fieldErrorsSize
        response.body.fieldErrors.get(0).field == fieldName
        response.body.fieldErrors.get(0).message == fieldErrorMessage

        where:
        restaurantName | endDateTime        | description | fieldErrorsSize | message            | fieldName        | fieldErrorMessage
        ""             | "2017-11-14 10:46" | "asd"       | 1               | "VALIDATION_ERROR" | "restaurantName" | "The restaurant name must be between 1 and 255 characters"
        toLongName     | "2017-11-14 10:46" | "asd"       | 1               | "VALIDATION_ERROR" | "restaurantName" | "The restaurant name must be between 1 and 255 characters"
        "MgFonald"     | ""                 | "asd"       | 1               | "VALIDATION_ERROR" | "endDatetime"    | "DateTime should be in format : \"yyyy-MM-dd HH:mm\""
        "MgFonald"     | "asdasd"           | "asd"       | 1               | "VALIDATION_ERROR" | "endDatetime"    | "DateTime should be in format : \"yyyy-MM-dd HH:mm\""
        "MgFonald"     | "2017-11-14 10:46" | ""          | 1               | "VALIDATION_ERROR" | "description"    | "The description must be between 1 and 255 characters"

    }

    def "GetOrders"() {
        given:
        def responseType = new ParameterizedTypeReference<CustomPageableResponse<OrderDTO>>() {}

        when:
        ResponseEntity<CustomPageableResponse<OrderDTO>> response = restTemplate.exchange(URL + "/orders",
                HttpMethod.GET, null, responseType)

        then:
        response.statusCode == HttpStatus.OK
        response.body.content.size() > 0

    }


    String toLongString(int i) {
        def sb = new StringBuilder()
        for (int j = 0; j < i; j++) {
            sb.append('a')
        }
        sb.toString()
    }

    CreateOrderCommand createOrder(def restaurantName, def endDateTime, def description) {
        CreateOrderCommand command = new CreateOrderCommand()
        command.restaurantName = restaurantName
        command.endDatetime = endDateTime
        command.description = description
        command
    }

}
