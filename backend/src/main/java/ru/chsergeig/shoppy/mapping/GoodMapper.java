package ru.chsergeig.shoppy.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.chsergeig.shoppy.jooq.tables.pojos.Goods;
import ru.chsergeig.shoppy.model.AdminCountedGoodDto;
import ru.chsergeig.shoppy.model.AdminGoodDto;
import ru.chsergeig.shoppy.model.CommonGoodDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {EnumMapper.class})
public interface GoodMapper {


    AdminGoodDto mapAdmin(Goods pojo);

    Goods mapAdmin(AdminGoodDto pojo);

    CommonGoodDto mapCommon(Goods pojo);

    @Mapping(source = "pojo.status", target = "status" )
    AdminCountedGoodDto mapAdminCounted(Goods pojo, Integer count);

    Goods mapCommon(CommonGoodDto dto);

    default List<CommonGoodDto> mapList(Collection<Goods> pojos) {
        if (pojos == null) {
            return null;
        }
        List<CommonGoodDto> result = new ArrayList<>();
        for (Goods pojo : pojos) {
            result.add(mapCommon(pojo));
        }
        return result;
    }

}
