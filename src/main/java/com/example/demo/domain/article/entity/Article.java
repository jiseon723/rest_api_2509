package com.example.demo.domain.article.entity;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Article extends BaseEntity {
    private String subject;
    private String content;

    @ManyToOne
    private Member member;
}
