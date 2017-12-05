package xyz.javista.web.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import spock.lang.Specification
import spock.lang.Unroll
import xyz.javista.web.command.RegisterUserCommand
import xyz.javista.web.controlleradvice.ErrorDTO
import xyz.javista.web.controlleradvice.ValidationErrorDTO
import xyz.javista.web.dto.UserDTO

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegistrationControllerTest extends Specification {

    @Autowired
    private TestRestTemplate restTemplate


    def "Registration should pass"() {
        when:
        def response = this.restTemplate.postForEntity("/registration", registrationCommand("testUser", "Vladislav Dytka", "SuP3Rh4RD", "user@server.com"), UserDTO)
        then:
        response.statusCode == HttpStatus.OK
        response.body.email == "user@server.com"
        response.body.name == "Vladislav Dytka"
        response.body.login == "testUser"
        response.body.id != null
    }

    def "Registration should fail duo to existed user"() {
        when:
        def response = this.restTemplate.postForEntity("/registration", registrationCommand("test", "Test", "11111111111111111111111111111111111111111", "test@test.test"), ErrorDTO)
        then:
        response.statusCode == HttpStatus.BAD_REQUEST
        response.body.message == "USER_EXIST"
    }

    @Unroll
    def "Registration should fail for empty field: #field"() {
        when:
        def response = this.restTemplate.postForEntity("/registration", registrationCommand(login, name, password, email), ValidationErrorDTO)

        then:
        response.statusCode == HttpStatus.BAD_REQUEST
        response.body.message == message
        response.body.fieldErrors.size() == 1
        def invalidField = response.body.fieldErrors.get(0)
        invalidField.message == validation_message
        invalidField.field == field

        where:
        login      | name              | password    | email             | message            | field      | validation_message
        ""         | "Vladislav Dytka" | "SuP3Rh4RD" | "user@server.com" | "VALIDATION_ERROR" | "login"    | "The login must be between 1 and 32 characters"
        "UserName" | ""                | "SuP3Rh4RD" | "user@server.com" | "VALIDATION_ERROR" | "name"     | "The name must be between 1 and 32 characters"
        "UserName" | "Vladislav Dytka" | ""          | "user@server.com" | "VALIDATION_ERROR" | "password" | "The password must be between 1 and 255 characters"
        "UserName" | "Vladislav Dytka" | "SuP3Rh4RD" | ""                | "VALIDATION_ERROR" | "email"    | "The email must be between 1 and 255 characters"

    }

    RegisterUserCommand registrationCommand(def login, def name, def password, def email) {
        RegisterUserCommand command = new RegisterUserCommand()
        command.login = login
        command.name = name
        command.password = password
        command.email = email
        command
    }
}
