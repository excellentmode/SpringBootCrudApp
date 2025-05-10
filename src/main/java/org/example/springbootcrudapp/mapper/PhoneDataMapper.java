package org.example.springbootcrudapp.mapper;

import org.example.springbootcrudapp.entity.PhoneData;
import org.example.springbootcrudapp.model.PhoneDataModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public abstract class PhoneDataMapper {
    @Mapping(target = "userId", source = "userData.id")
    public abstract PhoneDataModel toPhoneDataModel(PhoneData entity);

    @Mapping(target = "userData.id", source = "userId")
    public abstract PhoneData toPhoneDataEntity(PhoneDataModel model);
}