package com.sparta.newsfeedproject.domain.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FriendRequestDto {
    private Long receiverId;
    // 진짜 사용성이 없다고 판단되면 개발 종료와 함께 삭제 예정
}
