package xyz.javista.mapper;

import org.mapstruct.Mapper;
import xyz.javista.core.domain.OrderLineNumber;
import xyz.javista.exception.DateTimeConverterException;
import xyz.javista.web.command.CreateOrderLineItemCommand;
import xyz.javista.web.dto.OrderLineNumberDTO;

@Mapper(componentModel = "spring", uses = DateTimeConverter.class)
public interface OrderLineNumberMapper {

    OrderLineNumber toEntity(CreateOrderLineItemCommand createOrderLineItemCommand) throws DateTimeConverterException;

    OrderLineNumberDTO toDTO(OrderLineNumber orderLineNumber) throws DateTimeConverterException;
}
