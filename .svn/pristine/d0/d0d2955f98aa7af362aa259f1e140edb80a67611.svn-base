package com.quickdone.znwh.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Created by psf on 2017/10/22.
 */
@NoRepositoryBean
public abstract interface BaseRepository<T, ID extends Serializable> extends
        JpaRepository<T, ID>, JpaSpecificationExecutor<T> {


}
