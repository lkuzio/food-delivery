package xyz.javista.mapper;

import org.mapstruct.Mapper;
import xyz.javista.core.domain.OrderLineNumber;
import xyz.javista.web.command.CreateOrderLineItemCommand;

@Mapper(componentModel = "spring", uses = DateTimeConverter.class)
public interface OrderLineNumberMapper {

    OrderLineNumber toEntity(CreateOrderLineItemCommand createOrderLineItemCommand);
}
