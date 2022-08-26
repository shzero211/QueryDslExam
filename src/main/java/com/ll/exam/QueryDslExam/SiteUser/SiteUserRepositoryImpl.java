package com.ll.exam.QueryDslExam.SiteUser;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

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
}
