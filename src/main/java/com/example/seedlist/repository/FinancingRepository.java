package com.example.seedlist.repository;

import com.example.seedlist.entity.Financing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancingRepository extends JpaRepository<Financing, Integer> {

    List<Financing> findByCompanyId(Integer companyId);

}
