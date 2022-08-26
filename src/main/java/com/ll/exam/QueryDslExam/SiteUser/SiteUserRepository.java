package com.ll.exam.QueryDslExam.SiteUser;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteUserRepository extends JpaRepository<SiteUser,Long>,SiteUserRepositoryCustom {
}
