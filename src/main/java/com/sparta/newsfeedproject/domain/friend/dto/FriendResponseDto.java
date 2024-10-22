package com.sparta.newsfeedproject.domain.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FriendResponseDto {
    private Long friendId; //친구의 유저 ID
    private String nickname; // 친구의 유저 이름
}
