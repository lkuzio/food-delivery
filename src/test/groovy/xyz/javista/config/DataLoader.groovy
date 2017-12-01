package xyz.javista.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import xyz.javista.core.domain.Order
import xyz.javista.core.domain.User
import xyz.javista.core.repository.OrderRepository
import xyz.javista.core.repository.UserRepository

import java.time.LocalDateTime

@Component
class DataLoader implements ApplicationRunner {

    @Autowired
    UserRepository userRepository

    @Autowired
    OrderRepository orderRepository


    @Override
    void run(ApplicationArguments applicationArguments) throws Exception {
        insertUsers()
        insertOrders()

    }

    def insertOrders() {
        def order = new Order()
        order.id = UUID.randomUUID()
        order.restaurantName = "asd"
        order.description = "description"
        def user = userRepository.findAll().get(0)
        order.createdBy = user
        order.createdOn = LocalDateTime.now()
        order.endDatetime = LocalDateTime.now().plusHours(10)
        orderRepository.save(order)
    }

    def insertUsers() {
        User user = new User()
        user.name = "Test"
        user.login = "test"
        user.email = "test@test.test"
        user.password = "11111111111111111111111111111111111111111"
        userRepository.saveAndFlush(user);
    }
}
