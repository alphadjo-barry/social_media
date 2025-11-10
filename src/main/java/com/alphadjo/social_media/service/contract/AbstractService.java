package com.alphadjo.social_media.service.contract;

import java.util.List;

public interface AbstractService<T> {

    List<T> findAll();
    T findById(Long id);
    T save(T t);
    T update(T t, Long id);
    void delete(Long id);
}
