package kz.sportify.administrationservice.mapper;

import kz.sportify.administrationservice.model.User;
import kz.sportify.administrationservice.model.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mappings({})
    List<UserDTO> UserListToUserDTOList(List<User> userList);
    @Mappings({})
    List<User> UserDTOListToUserList(List<UserDTO> userDTOList);
    @Mappings({})
    UserDTO entityToApi(User user);
    @Mappings({})
    User apiToEntity(UserDTO userDTO);
}
