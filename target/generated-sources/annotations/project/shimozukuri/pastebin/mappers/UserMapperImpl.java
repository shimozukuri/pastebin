package project.shimozukuri.pastebin.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import project.shimozukuri.pastebin.dtos.user.UserDto;
import project.shimozukuri.pastebin.entities.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-27T19:50:32+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setUsername( entity.getUsername() );
        userDto.setPassword( entity.getPassword() );
        userDto.setEmail( entity.getEmail() );

        return userDto;
    }

    @Override
    public User toEntity(UserDto dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( dto.getUsername() );
        user.setPassword( dto.getPassword() );
        user.setEmail( dto.getEmail() );

        return user;
    }
}
