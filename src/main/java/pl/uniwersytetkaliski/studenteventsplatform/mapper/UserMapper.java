package pl.uniwersytetkaliski.studenteventsplatform.mapper;

import org.springframework.stereotype.Component;
import pl.uniwersytetkaliski.studenteventsplatform.dto.UserDTO;
import pl.uniwersytetkaliski.studenteventsplatform.model.User;
@Component
public class UserMapper {
    public UserDTO mapToDTO(User entity){
        return new UserDTO(entity);
    }
}
