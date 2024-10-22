package com.sparta.newsfeedproject.domain.friend.controller;

import com.sparta.newsfeedproject.domain.friend.dto.FriendRequestDto;
import com.sparta.newsfeedproject.domain.friend.service.FriendService;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import com.sparta.newsfeedproject.domain.member.resolver.util.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/member/friend")
public class FriendController {

    private final FriendService friendService;

    //친구 요청 API
    @PostMapping("/request/{receiverId}")
    public ResponseEntity<String> sendFriendRequest(@LoginUser Member member, @PathVariable Long receiverId) {
        friendService.sendFriendRequest(member.getId(), receiverId);
        return ResponseEntity.ok("친구 요청이 전송되었습니다.");
    }

    //친구 수락 API
    @PostMapping("/accept/{receiverId}")
    public ResponseEntity<String> acceptFriendRequest(@LoginUser Member member, @PathVariable Long receiverId) {
        // member.getId : 로그인 한 회원의 아이디
        // receiverId : 로그인 한 회원의 친구 요청 목록중 수락 하려하는 회원의 아이디
        friendService.acceptFriendRequest(member, receiverId);
        return ResponseEntity.ok("친구 요청이 수락되었습니다.");
    }

    //친구 목록 조회 API

    //친구 삭제 API

}

