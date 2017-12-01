package xyz.javista.mapper;

import org.mapstruct.Mapper;
import xyz.javista.core.domain.OrderLineNumber;
import xyz.javista.web.command.CreateOrderLineItemCommand;
import xyz.javista.web.dto.OrderLineNumberDTO;

@Mapper(componentModel = "spring", uses = DateTimeConverter.class)
public interface OrderLineNumberMapper {

    OrderLineNumber toEntity(CreateOrderLineItemCommand createOrderLineItemCommand);

    OrderLineNumberDTO toDTO(OrderLineNumber orderLineNumber);
}
