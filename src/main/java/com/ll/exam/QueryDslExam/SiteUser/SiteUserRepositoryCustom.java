package com.ll.exam.QueryDslExam.SiteUser;

import java.util.List;

public interface SiteUserRepositoryCustom {
    SiteUser getQslUser(Long id);
    long getQslCount();
    SiteUser getQslUserOrderByIdAscOne();
    List<SiteUser> getQslUsersOrderByIdAsc();
    List<SiteUser> searchQsl(String str);

}
