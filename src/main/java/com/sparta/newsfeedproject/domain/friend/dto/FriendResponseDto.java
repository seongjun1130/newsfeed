package com.sparta.newsfeedproject.domain.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FriendResponseDto {
    private Long id;
    private String email;
    private String nickname;
    private String country;

}
