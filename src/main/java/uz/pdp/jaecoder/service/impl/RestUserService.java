package uz.pdp.jaecoder.service.impl;

import lombok.NonNull;
import uz.pdp.jaecoder.criteria.UserCriteria;
import uz.pdp.jaecoder.dao.UserDao;
import uz.pdp.jaecoder.dto.UserCreateDto;
import uz.pdp.jaecoder.dto.UserDto;
import uz.pdp.jaecoder.dto.UserUpdateDto;
import uz.pdp.jaecoder.domain.User;
import uz.pdp.jaecoder.exception.NotFoundException;
import uz.pdp.jaecoder.mapper.app.UserMapper;
import uz.pdp.jaecoder.service.UserService;
import uz.pdp.jaecoder.utils.PasswordUtil;

import java.util.List;

public class RestUserService implements UserService {
    private final UserDao userDao;
    private final UserMapper userMapper;

    public RestUserService(UserDao userDao, UserMapper userMapper) {
        this.userDao = userDao;
        this.userMapper = userMapper;
    }

    @Override
    public Long create(@NonNull UserCreateDto dto) {
        User user = userMapper.fromCreateDto(dto);
        user.setPassword(PasswordUtil.hash(dto.password()));
        user = userDao.save(user);
        return user.getId();
    }

    @Override
    public boolean update(@NonNull Long id, @NonNull UserUpdateDto dto) {
        User user = requireUser(id);
        user = userMapper.fromUpdateDto(user, dto);
        userDao.update(user);
        return true;
    }


    private User requireUser(@NonNull Long id) {
        return userDao.findById(id)
                .orElseThrow(() -> new NotFoundException("user is not found : " + id));
    }

    @Override
    public UserDto get(@NonNull Long id) {
        User user = requireUser(id);
        return userMapper.toDto(user);
    }

    @Override
    public List<UserDto> getAll(@NonNull UserCriteria criteria) {
        List<User> users = userDao.findAll();
        return userMapper.toDto(users);
    }

    @Override
    public boolean deleteById(@NonNull Long id) {
        User user = requireUser(id);
        userDao.delete(user);
        return true;
    }
}
