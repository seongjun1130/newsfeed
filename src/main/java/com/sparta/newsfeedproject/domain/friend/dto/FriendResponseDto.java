package com.sparta.newsfeedproject.domain.friend.dto;

import com.sparta.newsfeedproject.domain.friend.entity.Friend;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FriendResponseDto {
    private String email;
    private String nickname;
    private String country;
}
