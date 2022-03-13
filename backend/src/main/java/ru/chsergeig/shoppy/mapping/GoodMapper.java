package ru.chsergeig.shoppy.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.chsergeig.shoppy.dto.GoodDTO;
import ru.chsergeig.shoppy.jooq.tables.pojos.Goods;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GoodMapper {

    GoodDTO map(Goods pojo);

    Goods map(GoodDTO dto);

}
