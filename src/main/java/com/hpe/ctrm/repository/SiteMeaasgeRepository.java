package com.hpe.ctrm.repository;

import com.hpe.ctrm.entity.SiteMessage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * sitemessage表数据访问层
 */
@Repository
public interface SiteMeaasgeRepository extends JpaRepository<SiteMessage,Integer> {
}
