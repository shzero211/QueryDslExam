package com.ll.exam.QueryDslExam.SiteUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SiteUserRepositoryCustom {
    SiteUser getQslUser(Long id);
    long getQslCount();
    SiteUser getQslUserOrderByIdAscOne();
    List<SiteUser> getQslUsersOrderByIdAsc();
    List<SiteUser> searchQsl(String str);

    Page<SiteUser> searchQsl(String str, Pageable pageable);

}
