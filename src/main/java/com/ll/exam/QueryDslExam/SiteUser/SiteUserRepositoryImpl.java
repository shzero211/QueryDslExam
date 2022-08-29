package com.ll.exam.QueryDslExam.SiteUser;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.ll.exam.QueryDslExam.SiteUser.QSiteUser.*;

@RequiredArgsConstructor
public class SiteUserRepositoryImpl implements  SiteUserRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public SiteUser getQslUser(Long id) {
        return jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .where(siteUser.id.eq(id))
                .fetchOne();
    }

    @Override
    public long getQslCount() {
      return jpaQueryFactory
              .select(siteUser.count())
              .from(siteUser)
              .fetchOne();
    }

    @Override
    public SiteUser getQslUserOrderByIdAscOne() {
       return jpaQueryFactory
               .select(siteUser)
               .from(siteUser)
               .orderBy(siteUser.id.asc())
               .limit(1)
               .fetchOne();
    }

    @Override
    public List<SiteUser> getQslUsersOrderByIdAsc() {
        return jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .orderBy(siteUser.id.asc())
                .fetch();
    }

    @Override
    public List<SiteUser> searchQsl(String str) {
       return jpaQueryFactory.select(siteUser)
               .from(siteUser)
               .where(
                       siteUser.username.contains(str)
                       .or(siteUser.email.contains(str))
               )
               .orderBy(siteUser.id.desc())
               .fetch();
    }

}
