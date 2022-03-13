package ru.chsergeig.shoppy.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.chsergeig.shoppy.dto.OrderDTO;
import ru.chsergeig.shoppy.jooq.tables.pojos.Orders;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    OrderDTO map(Orders pojo);

    Orders map(OrderDTO dto);

}
