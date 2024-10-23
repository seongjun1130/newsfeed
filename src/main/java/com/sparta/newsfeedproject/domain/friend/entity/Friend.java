package com.sparta.newsfeedproject.domain.friend.entity;

import com.sparta.newsfeedproject.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "friend")
@NoArgsConstructor
@AllArgsConstructor
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;  //로그인 한 유저

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private Member friend; //로그인 한 유저의 친구

    public Friend(Member member, Member friend) {
        this.member = member;
        this.friend = friend;
    }
}
