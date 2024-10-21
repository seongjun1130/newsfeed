package com.sparta.newsfeedproject.domain.friend.controller;

import com.sparta.newsfeedproject.domain.friend.dto.FriendRequestDto;
import com.sparta.newsfeedproject.domain.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/member/friend")
public class FriendController {

    private final FriendService friendService;

    //친구 요청 API
    @PostMapping
    public ResponseEntity<String> sendFriendRequest(@RequestBody FriendRequestDto requestDto) {
        friendService.sendFriendRequest(requestDto.getRequesterId(), requestDto.getReceiverId());
        return ResponseEntity.ok("친구 요청이 전송되었습니다.");
    }

    //친구 수락 API

    //친구 목록 조회 API

    //친구 삭제 API

}

