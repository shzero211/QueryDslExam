package com.ll.exam.QueryDslExam.SiteUser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
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
    SiteUser siteUser9=SiteUser.builder()
            .username("user9")
            .password("{noop}1234")
            .email("user9@naver.com")
            .build();

        SiteUser siteUser10=SiteUser.builder()
                .username("user10")
                .password("{noop}1234")
                .email("user10@naver.com")
                .build();
        siteUserRepository.saveAll(Arrays.asList(siteUser9,siteUser10));
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
        assertEquals(8,count);
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
    @Test
    @DisplayName("like Test")
    public void searchQsl(){
        List<SiteUser> users=siteUserRepository.searchQsl("1");
        assertEquals("user10",users.get(0).getUsername());
    }
    @Test
    @DisplayName("검색, Page 리턴, id ASC, pageSize=1, page=0")
    void t8() {
        int pageSize = 1; // 한 페이지에 보여줄 아이템 개수
        int page=0;
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("id"));
        Pageable pageable = PageRequest.of(page,pageSize, Sort.by(sorts)); // 한 페이지에 10까지 가능
        Page<SiteUser> users = siteUserRepository.searchQsl("user", pageable);
        assertEquals(0,users.getNumber());
        assertEquals(8,users.getTotalPages());
        assertEquals(8,users.getTotalElements());
        // 검색어 : user1
        // 한 페이지에 나올 수 있는 아이템 수 : 1개
        // 현재 페이지 : 1
        // 정렬 : id 역순

        // 내용 가져오는 SQL
        /*
        SELECT site_user.*
        FROM site_user
        WHERE site_user.username LIKE '%user%'
        OR site_user.email LIKE '%user%'
        ORDER BY site_user.id ASC
        LIMIT 1, 1
         */

        // 전체 개수 계산하는 SQL
        /*
        SELECT COUNT(*)
        FROM site_user
        WHERE site_user.username LIKE '%user%'
        OR site_user.email LIKE '%user%'
         */
    }
    @Test
    @DisplayName("검색, Page 리턴, id DESC, pageSize=1, page=0")
    void t9() {
        long totalCount = siteUserRepository.count();
        int pageSize = 1; // 한 페이지에 보여줄 아이템 개수
        int totalPages = (int) Math.ceil(totalCount / (double) pageSize);
        int page = 0;
        String kw = "user";

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorts)); // 한 페이지에 10까지 가능
        Page<SiteUser> usersPage = siteUserRepository.searchQsl(kw, pageable);

        assertThat(usersPage.getTotalPages()).isEqualTo(totalPages);
        assertThat(usersPage.getNumber()).isEqualTo(page);
        assertThat(usersPage.getSize()).isEqualTo(pageSize);

        List<SiteUser> users = usersPage.get().toList();

        assertThat(users.size()).isEqualTo(pageSize);

        SiteUser u = users.get(0);

        assertThat(u.getId()).isEqualTo(8L);
        assertThat(u.getUsername()).isEqualTo("user8");
        assertThat(u.getEmail()).isEqualTo("user8@naver.com");
        assertThat(u.getPassword()).isEqualTo("user8");
    }

    @Test
    @Transactional
    @DisplayName("ManyToMany")
    void t10() {
        SiteUser u2 = siteUserRepository.getQslUser(2L);

        u2.addInterestKeywordContent("축구");
        u2.addInterestKeywordContent("롤");
        u2.addInterestKeywordContent("헬스");
        u2.addInterestKeywordContent("헬스"); // 중복등록은 무시

        siteUserRepository.save(u2);
        // 엔티티클래스 : InterestKeyword(interest_keyword 테이블)
        // 중간테이블도 생성되어야 함, 힌트 : @ManyToMany
        // interest_keyword 테이블에 축구, 롤, 헬스에 해당하는 row 3개 생성
    }
    @Test
    @Transactional
    @DisplayName("축구에 관심이있는 회원 검색")
    public void t11(){
        List<SiteUser> u1=siteUserRepository.getQslUserInterestKeyWord("축구");
        assertEquals("user1",u1.get(0).getUsername());
    }

    @Test
    @DisplayName("Spring Data JPA 기본, 축구에 관심이 있는 회원들 검색")
    void t12() {

        List<SiteUser> users = siteUserRepository.getUserByInterestKeyword("축구");

        assertThat(users.size()).isEqualTo(1);

        SiteUser u = users.get(0);

        assertThat(u.getId()).isEqualTo(1L);
        assertThat(u.getUsername()).isEqualTo("user1");
        assertThat(u.getEmail()).isEqualTo("user1@naver.com");
        assertThat(u.getPassword()).isEqualTo("user1");

    }

    @Test
    @DisplayName("u2=아이돌, u1=팬 u1은 u2의 팔로워 이다.")
    @Transactional
    @Rollback
    void t13() {
        SiteUser u1 = siteUserRepository.getQslUser(1L);
        SiteUser u2 = siteUserRepository.getQslUser(2L);

        u1.follow(u2);

        siteUserRepository.save(u2);
    }

    @Test
    @Transactional
    @DisplayName("본인이 본인을 follow 할 수 없다.")
    void t14() {
        SiteUser u1 = siteUserRepository.getQslUser(1L);

        u1.follow(u1);

        assertThat(u1.getFollowers().size()).isEqualTo(0);
    }
    @Test
    @Transactional
    @DisplayName("follow 양쪽 구현")
    void t15(){
        SiteUser u1=siteUserRepository.getQslUser(1L);
        SiteUser u2=siteUserRepository.getQslUser(2L);
        u1.follow(u2);
        u1.getFollowers().stream().forEach(siteUser -> System.out.println("u1_followers:"+siteUser.getUsername()));
        u1.getFollowings().stream().forEach(siteUser -> System.out.println("u1_followings:"+siteUser.getUsername()));

        u2.getFollowers().stream().forEach(siteUser -> System.out.println("u2_followers:"+siteUser.getUsername()));
        u2.getFollowings().stream().forEach(siteUser -> System.out.println("u2_followings:"+siteUser.getUsername()));
    }
    @Test
    @DisplayName("고아객체제거")
    @Transactional
    @Rollback(value = false)
    void t16(){
        SiteUser u1=siteUserRepository.getQslUser(1L);
        u1.removeInterestKeywordContent("농구");
    }
}