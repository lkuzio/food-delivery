package xyz.javista.validator

import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.ConstraintValidatorContext

class DateTimeValidatorTest extends Specification {

    DateTimeValidator validator = new DateTimeValidator()
    def context = Mock(ConstraintValidatorContext)

    @Unroll
    def "should #result validation for #value"() {
        when:
        def valid = validator.isValid(value, context)
        then:
        valid == isValid

        where:
        isValid | value              | result
        true    | "2017-11-03 12:12" | "pass"
        false   | "2017/11/03 12:12" | "failure"
        false   | "Lorem ipsum"      | "failure"
        false   | ""                 | "failure"
    }
}
