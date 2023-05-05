package ru.chsergeig.shoppy.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.ValueMapping;
import ru.chsergeig.shoppy.jooq.enums.AccountRole;
import ru.chsergeig.shoppy.jooq.enums.Status;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EnumMapper {

    @ValueMapping(source = "aDMIN", target = "ROLE_ADMIN")
    @ValueMapping(source = "uSER", target = "ROLE_USER")
    @ValueMapping(source = "gUEST", target = "ROLE_GUEST")
    AccountRole toJooq(ru.chsergeig.shoppy.model.AccountRole model);

    @ValueMapping(source = "ROLE_ADMIN", target = "aDMIN")
    @ValueMapping(source = "ROLE_USER", target = "uSER")
    @ValueMapping(source = "ROLE_GUEST", target = "gUEST")
    ru.chsergeig.shoppy.model.AccountRole fromJooq(AccountRole model);

    @ValueMapping(source = "aDDED", target = "ADDED")
    @ValueMapping(source = "aCTIVE", target = "ACTIVE")
    @ValueMapping(source = "rEMOVED", target = "REMOVED")
    @ValueMapping(source = "dISABLED", target = "DISABLED")
    Status toJooq(ru.chsergeig.shoppy.model.Status model);

    @ValueMapping(source = "ADDED", target = "aDDED")
    @ValueMapping(source = "ACTIVE", target = "aCTIVE")
    @ValueMapping(source = "REMOVED", target = "rEMOVED")
    @ValueMapping(source = "DISABLED", target = "dISABLED")
    ru.chsergeig.shoppy.model.Status fromJooq(Status model);

}
