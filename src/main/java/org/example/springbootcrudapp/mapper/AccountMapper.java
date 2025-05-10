package org.example.springbootcrudapp.mapper;

import org.example.springbootcrudapp.entity.AccountData;
import org.example.springbootcrudapp.model.AccountModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public abstract class AccountMapper {
    @Mapping(target = "userId", source = "userData.id")
    public abstract AccountModel toAccountModel(AccountData entity);

    @Mapping(target = "userData.id", source = "userId")
    public abstract AccountData toAccountEntity(AccountModel model);
}