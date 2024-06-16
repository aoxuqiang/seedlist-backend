package com.example.seedlist.repository;

import com.example.seedlist.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    List<Tag> queryAllByIdIn(List<Integer> ids);
}
