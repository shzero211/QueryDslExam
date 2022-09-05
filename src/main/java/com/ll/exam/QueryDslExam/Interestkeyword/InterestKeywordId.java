package com.ll.exam.QueryDslExam.Interestkeyword;

import com.ll.exam.QueryDslExam.SiteUser.SiteUser;
import com.querydsl.codegen.Serializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterestKeywordId implements Serializable {
    private String content;
    private SiteUser siteUser;
}
