package org.example.springbootcrudapp.mapper;

import org.example.springbootcrudapp.entity.EmailData;
import org.example.springbootcrudapp.model.EmailDataModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public abstract class EmailDataMapper {
    @Mapping(target = "userId", source = "userData.id")
    public abstract EmailDataModel toEmailDataModel(EmailData entity);

    @Mapping(target = "userData.id", source = "userId")
    public abstract EmailData toEmailDataEntity(EmailDataModel model);
}