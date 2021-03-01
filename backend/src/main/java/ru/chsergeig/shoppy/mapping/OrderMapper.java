package ru.chsergeig.shoppy.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.chsergeig.shoppy.dto.OrderDTO;
import ru.chsergeig.shoppy.jooq.tables.records.OrderRecord;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    OrderDTO map(OrderRecord record);

    OrderRecord map(OrderDTO dto);

}
