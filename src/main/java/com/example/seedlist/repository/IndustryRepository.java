package com.example.seedlist.repository;

import com.example.seedlist.entity.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface IndustryRepository extends JpaRepository<Industry, Integer> {

}
