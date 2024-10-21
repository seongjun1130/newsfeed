package com.sparta.newsfeedproject.domain.friend.entity;

import com.sparta.newsfeedproject.domain.friend.enumPac.FriendRequestStatus;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "friend_request")
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private Member requester; // 친구 요청을 보낸 멤버

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Member receiver; // 친구 요청을 받은 유저

    @Enumerated(EnumType.STRING)
    private FriendRequestStatus status; //친구 요청의 상태 -> PENDING : 요청중
                                                        //ACCEPTED : 수락됨
                                                        //REJECTED : 거절됨

}
