package com.ll.exam.QueryDslExam.SiteUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SiteUserRepository extends JpaRepository<SiteUser,Long>,SiteUserRepositoryCustom {
    @Query("select s from SiteUser s " +
            "join s. interestKeywords ik " +
            "where ik.content= :content ")
    List<SiteUser> getUserByInterestKeyword(@Param("content") String content);
}
