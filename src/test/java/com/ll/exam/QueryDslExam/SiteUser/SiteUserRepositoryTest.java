package com.ll.exam.QueryDslExam.SiteUser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SiteUserRepositoryTest {
    @Autowired
    private SiteUserRepository siteUserRepository;
    @Test
    public void 회원생성(){
    SiteUser siteUser=new SiteUser(null,"user1","user1","user1@naver.com");
    siteUserRepository.save(siteUser);
    }
}