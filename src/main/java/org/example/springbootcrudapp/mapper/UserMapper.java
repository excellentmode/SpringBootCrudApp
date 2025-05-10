package org.example.springbootcrudapp.mapper;

import org.example.springbootcrudapp.dto.UserDto;
import org.example.springbootcrudapp.entity.UserData;
import org.example.springbootcrudapp.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AccountMapper.class, EmailDataMapper.class, PhoneDataMapper.class})
public abstract class UserMapper {
    //todo переписать на userDetails.
    //Разделить сущности, чтобы не бизнес-сущность UserData не хранила пароль.
    public abstract UserModel toUserModelAuth(UserData entity);

    @Mapping(target = "password", ignore = true)
    public abstract UserModel toUserModel(UserData entity);

    public abstract UserDto toUserDtos(UserModel model);
}