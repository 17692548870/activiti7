package com.hpe.ctrm.repository;


import com.hpe.ctrm.entity.Evection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * evection表数据访问层
 */
@Repository
public interface EvectionRepository extends JpaRepository<Evection,Integer> {

    @Query(value= "update tb_evection set state=1 where id=?", nativeQuery=true)
    @Modifying//表面此方法为修改方法:增加，删除，修改操作都需要加上此注解
    @Transactional//事务注解：增加，删除，修改操作都需要加上事务注解
    int startTask(Integer id);

    @Query(value = "update tb_evection set state=2 where id=?",nativeQuery=true)
    @Modifying//表面此方法为修改方法:增加，删除，修改操作都需要加上此注解
    @Transactional//事务注解：增加，删除，修改操作都需要加上事务注解
    int endTask(Integer id);

}
