package com.example.seedlist.service;

import cn.hutool.core.collection.CollectionUtil;
import com.example.seedlist.entity.BaseEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public abstract class BaseService<R extends JpaRepository<T, D>, T extends BaseEntity, D> {

    private final R repository;

    protected BaseService(R repository) {
        this.repository = repository;
    }

    protected R getRepository() {
        return this.repository;
    }

    public T getById(D d) {
        return repository.findById(d).orElse(null);
    }

    public List<T> getAll() {
        return repository.findAll(Sort.by(Sort.Order.desc("id")));
    }

    public List<T> findAllById(List<D> ids) {
        return CollectionUtil.reverse(repository.findAllById(ids));
    }



    public T save(T t) {
        return repository.save(t);
    }

    public List<T> saveAll(List<T> ts) {
        return repository.saveAll(ts);
    }

    public void delById(D d){
        repository.deleteById(d);
    }

}
