package com.sparta.newsfeedproject.domain.friend.dto;

import com.sparta.newsfeedproject.domain.friend.entity.Friend;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FriendResponseDto {
    private Long friendId; //친구의 유저 ID
    private String nickname; // 친구의 유저 이름

    public static FriendResponseDto fromFriend(Friend friend) {
        return new FriendResponseDto(friend.getFriend().getId(), friend.getFriend().getNickName());
    }
}
