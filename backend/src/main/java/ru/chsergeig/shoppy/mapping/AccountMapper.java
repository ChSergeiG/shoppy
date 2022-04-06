package ru.chsergeig.shoppy.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.chsergeig.shoppy.dto.admin.AccountDto;
import ru.chsergeig.shoppy.jooq.tables.pojos.Accounts;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    AccountDto map(Accounts pojo);

    Accounts map(AccountDto dto);

    default List<AccountDto> mapList(List<Accounts> pojos) {
        if (pojos == null) {
            return null;
        }
        List<AccountDto> dtos = new ArrayList<>();
        for (Accounts pojo : pojos) {
            dtos.add(map(pojo));
        }
        return dtos;
    }

}
