package ru.chsergeig.shoppy.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.chsergeig.shoppy.dto.admin.GoodDto;
import ru.chsergeig.shoppy.jooq.tables.pojos.Goods;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GoodMapper {

    GoodDto map(Goods pojo);

    Goods map(GoodDto dto);

    default List<GoodDto> mapList(Collection<Goods> pojos) {
        if (pojos == null) {
            return null;
        }
        List<GoodDto> result = new ArrayList<>();
        for (Goods pojo : pojos) {
            result.add(map(pojo));
        }
        return result;
    }

}
