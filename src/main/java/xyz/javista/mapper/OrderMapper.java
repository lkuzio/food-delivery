package xyz.javista.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import xyz.javista.command.CreateOrderCommand;
import xyz.javista.core.domain.Order;
import xyz.javista.dto.OrderDTO;

@Mapper(componentModel = "spring", uses = DateTimeConverter.class)
public interface OrderMapper {

    Order toEntity(OrderDTO dto);

    @Mapping(source = "createdBy", target = "author")
    OrderDTO toDto(Order order);

    Order toEntity(CreateOrderCommand createOrderCommand);
}
