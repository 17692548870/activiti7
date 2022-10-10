package com.hpe.ctrm.repository;

import com.hpe.ctrm.entity.Flow;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * flow表数据访问层
 */
@Repository
public interface FlowRepository extends JpaRepository<Flow,Integer> {
    //查询所有资源:根据创建时间排序，原生sql
     @Query(value = "select * from tb_flow order by create_time desc", nativeQuery=true)
     List<Flow> findAllByCreateTime();

     //根据id查询当前对应的流程资源
     Flow findOneById(Integer id);

     //根据id修改部署状态:改为已部署
    @Query(value = "update tb_flow set state = 0 where id = ?",nativeQuery = true)
    @Modifying//表面此方法为修改方法:增加，删除，修改操作都需要加上此注解
    @Transactional//事务注解：增加，删除，修改操作都需要加上事务注解
    int updateFlowStateById(Integer id);

}
