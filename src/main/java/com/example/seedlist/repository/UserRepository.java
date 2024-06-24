package com.example.seedlist.repository;

import com.example.seedlist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User getByWxUserId(String wxUserId);

    List<User> queryAllByIdIn(List<Integer> ids);
}
