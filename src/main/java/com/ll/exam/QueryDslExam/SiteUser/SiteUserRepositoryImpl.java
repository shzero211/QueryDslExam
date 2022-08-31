package com.ll.exam.QueryDslExam.SiteUser;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import javax.transaction.Transaction;
import java.util.List;

import static com.ll.exam.QueryDslExam.Interestkeyword.QInterestKeyword.interestKeyword;
import static com.ll.exam.QueryDslExam.SiteUser.QSiteUser.siteUser;


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

    @Override
    public Page<SiteUser> searchQsl(String str, Pageable pageable) {
       JPAQuery<SiteUser> userQuery = jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .where(
                        siteUser.username.contains(str)
                                .or(siteUser.email.contains(str))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
       for(Sort.Order o:pageable.getSort()){
           PathBuilder pathBuilder=new PathBuilder(siteUser.getType(),siteUser.getMetadata());
           userQuery.orderBy(new OrderSpecifier(o.isAscending()?Order.ASC :  Order.DESC,pathBuilder.get(o.getProperty())));
       }
       List<SiteUser> users=userQuery.fetch();

        //return new PageImpl<>(users,pageable,userQuery.fetchCount());

        JPAQuery<Long> usersCountQuery = jpaQueryFactory
                .select(siteUser.count())
                .from(siteUser)
                .where(
                        siteUser.username.contains(str)
                                .or(siteUser.email.contains(str))
                );

        return PageableExecutionUtils.getPage(users, pageable, usersCountQuery::fetchOne);
    }

    @Override
    public List<SiteUser> getQslUserInterestKeyWord(String keyword) {
        return jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .leftJoin(siteUser.interestKeywords, interestKeyword)
                .where(interestKeyword.content.eq(keyword))
                .fetch();

    }
}
