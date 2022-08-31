package com.ll.exam.QueryDslExam;

import com.ll.exam.QueryDslExam.SiteUser.SiteUser;
import com.ll.exam.QueryDslExam.SiteUser.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
public class TestInitData {

    @Bean
    //CommandLineRunner : 주로 앱실행 직후 초기데이터 셋팅및 초기화에 사용
     CommandLineRunner init(SiteUserRepository siteUserRepository) {
         return args -> {

             SiteUser u1 = SiteUser.builder()
                     .username("user1")
                     .password("user1")
                     .email("user1@naver.com")
                     .build();

             SiteUser u2 = SiteUser.builder()
                     .username("user2")
                     .password("user2")
                     .email("user2@naver.com")
                     .build();

             u1.addInterestKeywordContent("축구");
             u1.addInterestKeywordContent("농구");
             siteUserRepository.save(u1);
             u1.addInterestKeywordContent("농구");
             u2.addInterestKeywordContent("클라이밍");
             u2.addInterestKeywordContent("마라톤");
            siteUserRepository.save(u2);

         };

     }
}
