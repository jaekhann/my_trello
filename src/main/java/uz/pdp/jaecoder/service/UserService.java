package uz.pdp.jaecoder.service;

import uz.pdp.jaecoder.criteria.UserCriteria;
import uz.pdp.jaecoder.dto.UserCreateDto;
import uz.pdp.jaecoder.dto.UserDto;
import uz.pdp.jaecoder.dto.UserUpdateDto;

public interface UserService extends GenericCrudService<UserDto,
        UserCreateDto, UserUpdateDto, UserCriteria, Long> {
}
