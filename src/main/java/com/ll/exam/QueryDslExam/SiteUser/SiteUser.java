package com.ll.exam.QueryDslExam.SiteUser;

import com.ll.exam.QueryDslExam.Interestkeyword.InterestKeyword;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Builder
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "siteUser")
    private Set<InterestKeyword> interestKeywords=new HashSet<>();

    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<SiteUser> followers = new HashSet<>();

    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<SiteUser> followings = new HashSet<>();

    public void addInterestKeywordContent(String keywordContent) {
        InterestKeyword interestKeyword=new InterestKeyword(keywordContent,this);
        interestKeywords.add(interestKeyword);
    }

    public void follow(SiteUser following){
        if (this == following) return;
        if (following == null) return;
        if (this.getId() == following.getId()) return;

        following.followers.add(this);
        this.followings.add(following);
    }

}
