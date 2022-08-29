package com.ll.exam.QueryDslExam.SiteUser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
class SiteUserRepositoryTest {
    @Autowired
    private SiteUserRepository siteUserRepository;

    @Test
    public void 회원생성(){
    SiteUser siteUser3=SiteUser.builder()
            .username("user3")
            .password("{noop}1234")
            .email("user3@naver.com")
            .build();

        SiteUser siteUser4=SiteUser.builder()
                .username("user4")
                .password("{noop}1234")
                .email("user4@naver.com")
                .build();
        siteUserRepository.saveAll(Arrays.asList(siteUser3,siteUser4));
    }

    @Test
    public void 회원생성Builder(){
        SiteUser siteUser=SiteUser.builder()
                .username("userM")
                .password("userM")
                .email("userM@naver.com")
                .build();
        siteUserRepository.save(siteUser);
    }
    @Test
    public void getQslUser(){
        SiteUser siteUser=siteUserRepository.getQslUser(1L);
        assertEquals("user1",siteUser.getUsername());
    }
    @Test
    @DisplayName("모든 회원의 수")
    public void getQslCount(){
        long count=siteUserRepository.getQslCount();
        assertEquals(2,count);
    }
    @Test
    @DisplayName("가장오래된 회원 1명 ")
    public void getQslUserOrderByIdAscOne(){
        SiteUser siteUser=siteUserRepository.getQslUserOrderByIdAscOne();
        assertEquals(siteUser.getUsername(),"user1");
    }
    @Test
    @DisplayName("전체 회원 오래된순")
    public void getQslUsersOrderByIdAsc(){
       List<SiteUser> users=siteUserRepository.getQslUsersOrderByIdAsc();
       assertEquals(users.get(1).getUsername(),"user2");
    }

}