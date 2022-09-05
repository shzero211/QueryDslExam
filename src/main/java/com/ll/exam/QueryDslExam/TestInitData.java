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
             SiteUser u3 = SiteUser.builder()
                     .username("user3")
                     .password("user3")
                     .email("user3@naver.com")
                     .build();
             SiteUser u4 = SiteUser.builder()
                     .username("user4")
                     .password("user4")
                     .email("user4@naver.com")
                     .build();
             SiteUser u5 = SiteUser.builder()
                     .username("user5")
                     .password("user5")
                     .email("user5@naver.com")
                     .build();
             SiteUser u6 = SiteUser.builder()
                     .username("user6")
                     .password("user6")
                     .email("user6@naver.com")
                     .build();
             SiteUser u7 = SiteUser.builder()
                     .username("user7")
                     .password("user7")
                     .email("user7@naver.com")
                     .build();
             SiteUser u8 = SiteUser.builder()
                     .username("user8")
                     .password("user8")
                     .email("user8@naver.com")
                     .build();

             u1.addInterestKeywordContent("축구");
             u1.addInterestKeywordContent("농구");
             u1.addInterestKeywordContent("농구");
             u2.addInterestKeywordContent("클라이밍");
             u2.addInterestKeywordContent("마라톤");
             siteUserRepository.saveAll(Arrays.asList(u1,u2,u3,u4,u5,u6,u7,u8));
             u8.follow(u1);
             u8.follow(u2);

             u7.follow(u6);
             u7.follow(u5);
             u7.follow(u4);
             u7.follow(u3);

            siteUserRepository.saveAll(Arrays.asList(u1,u2,u3,u4,u5,u6,u7,u8));

         };

     }
}
