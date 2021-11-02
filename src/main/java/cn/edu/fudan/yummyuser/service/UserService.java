package cn.edu.fudan.yummyuser.service;

import cn.edu.fudan.yummyuser.mapper.IUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;


@Service
@RequiredArgsConstructor
public class UserService {

    private final IUserMapper userMapper;
    private final PlatformTransactionManager trxManager;

}
