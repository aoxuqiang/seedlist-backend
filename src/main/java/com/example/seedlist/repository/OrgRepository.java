package com.example.seedlist.repository;

import com.example.seedlist.entity.Org;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrgRepository extends JpaRepository<Org, Integer> {

}
