package com.sparta.newsfeedproject.domain.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FriendRequestDto {
    private Long requesterId;
    private Long receiverId;
}
