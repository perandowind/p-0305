package com.back.p0305.domain.post.entity;

import com.back.p0305.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Post extends BaseEntity {

    private String title;
    private String content;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}