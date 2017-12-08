package xyz.javista.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
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

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    void run(ApplicationArguments applicationArguments) throws Exception {
        insertUsers()
        insertOrders()

    }

    def insertOrders() {
        def order = new Order()
        def user = userRepository.findAll().get(0)
        order.id = UUID.randomUUID()
        order.restaurantName = "FastHot"
        order.description = "The best in city"
                order.createdBy = user
        order.createdOn = LocalDateTime.now()
        order.endDatetime = LocalDateTime.now().plusHours(10)
        orderRepository.save(order)
        order.id = UUID.randomUUID()
        order.restaurantName = "FastHot2"
        order.description = "Fresh juice for free"
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
        user.password = bCryptPasswordEncoder.encode("11111111111111111111111111111111111111111")
        userRepository.saveAndFlush(user)
    }
}
