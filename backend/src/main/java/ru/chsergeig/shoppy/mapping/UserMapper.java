package ru.chsergeig.shoppy.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import ru.chsergeig.shoppy.dto.UserDTO;
import ru.chsergeig.shoppy.jooq.tables.records.UsersRecord;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(source = "record", target = "id", qualifiedByName = "getId")
    @Mapping(source = "record", target = "name", qualifiedByName = "getName")
    @Mapping(source = "record", target = "password", qualifiedByName = "getPassword")
    UserDTO recordToDto(UsersRecord record);

    @Named("getId")
    default Integer getId(UsersRecord record) {
        return record.getId();
    }

    @Named("getName")
    default String getName(UsersRecord record) {
        return record.getName();
    }

    @Named("getPassword")
    default String getPassword(UsersRecord record) {
        return record.getPassword();
    }

}
