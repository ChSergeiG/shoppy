package ru.chsergeig.shoppy.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.chsergeig.shoppy.jooq.tables.pojos.Orders;
import ru.chsergeig.shoppy.model.AdminOrderDto;
import ru.chsergeig.shoppy.model.CommonOrderDto;
import ru.chsergeig.shoppy.model.ExtendedOrderDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {EnumMapper.class})
public interface OrderMapper {

    Orders mapCommon(CommonOrderDto dto);

    AdminOrderDto mapAdmin(Orders pojo);

    ExtendedOrderDto mapExtended(Orders pojo);

    Orders mapAdmin(AdminOrderDto dto);

}
