package com.example.seedlist.service;

import com.example.seedlist.entity.User;
import com.example.seedlist.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends BaseService<UserRepository, User,Integer> {

    protected UserService(UserRepository repository) {
        super(repository);
    }

    public User getByWx(String wxUserId) {
        return getRepository().getByWxUserId(wxUserId);
    }

    public List<User> queryByIds(List<Integer> ids) {
        return getRepository().queryAllByIdIn(ids);
    }


    public void updateOrgId(Integer id, Integer orgId) {
        User user = getRepository().getOne(id);
        user.setOrgId(orgId);
        getRepository().save(user);
    }
}

