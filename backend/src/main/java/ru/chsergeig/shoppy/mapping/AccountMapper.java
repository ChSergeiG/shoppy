package ru.chsergeig.shoppy.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.chsergeig.shoppy.jooq.tables.pojos.Accounts;
import ru.chsergeig.shoppy.model.AdminAccountDto;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {EnumMapper.class})
public interface AccountMapper {

    AdminAccountDto mapAdmin(Accounts pojo);

    Accounts mapAdmin(AdminAccountDto dto);

    default List<AdminAccountDto> mapAdminList(List<Accounts> pojos) {
        if (pojos == null) {
            return null;
        }
        List<AdminAccountDto> dtos = new ArrayList<>();
        for (Accounts pojo : pojos) {
            dtos.add(mapAdmin(pojo));
        }
        return dtos;
    }

}
