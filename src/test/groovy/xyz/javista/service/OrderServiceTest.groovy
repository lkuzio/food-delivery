package xyz.javista.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Ignore
import spock.lang.Specification
import xyz.javista.config.AuditorAwareImpl
import xyz.javista.core.repository.OrderRepository
import xyz.javista.core.repository.UserRepository
import xyz.javista.web.command.CreateOrderCommand

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class OrderServiceTest extends Specification {


    @Autowired
    OrderService orderService

    @Autowired
    OrderRepository orderRepository

    @Autowired
    UserRepository userRepository

    def auditor = Mock(AuditorAwareImpl)

    def setup() {
        given:
        auditor.getCurrentAuditor() >> userRepository.findAll().get(0)
    }


    def "CreateOrder"() {
        when:
        def result = orderService.createOrder(createOrderRequest())
        then:
        noExceptionThrown()

    }

    CreateOrderCommand createOrderRequest() {
        CreateOrderCommand command = new CreateOrderCommand()
        command.restaurantName = "RestaurantName"
        command.endDatetime = "2017-11-03 12:12"
        command.description = "Fast cheap and delicious"
        command
    }


    @Ignore
    def "RemoveOrder"() {
        given:
        def existedOrder = orderRepository.findAll().get(0)
        when:
        orderService.removeOrder(existedOrder.id.toString())
        then:
        noExceptionThrown()
    }

    def "UpdateOrder"() {
    }

    def "RemoveOrderItem"() {
    }

    def "UpdateOrderItem"() {
    }
}
