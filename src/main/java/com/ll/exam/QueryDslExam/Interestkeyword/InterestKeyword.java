package com.ll.exam.QueryDslExam.Interestkeyword;

import com.ll.exam.QueryDslExam.SiteUser.SiteUser;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(InterestKeywordId.class)
public class InterestKeyword  {
    @Id
    private String content;

    @Id
    @ManyToOne
    private SiteUser siteUser;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InterestKeyword)) return false;

        InterestKeyword that = (InterestKeyword) o;

        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }
}
