package project.shimozukuri.pastebin.mappers;

import org.mapstruct.Mapper;
import project.shimozukuri.pastebin.dtos.user.UserDto;
import project.shimozukuri.pastebin.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDto>{
}
