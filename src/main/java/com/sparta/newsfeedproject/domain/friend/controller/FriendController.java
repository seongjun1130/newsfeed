package com.sparta.newsfeedproject.domain.friend.controller;

import com.sparta.newsfeedproject.domain.friend.dto.FriendResponseDto;
import com.sparta.newsfeedproject.domain.friend.service.FriendService;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import com.sparta.newsfeedproject.domain.member.resolver.util.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member/friend")
public class FriendController {

    private final FriendService friendService;

    //친구 요청 API
    @PostMapping("/request/{receiverId}")
    public ResponseEntity<String> sendFriendRequest(@LoginUser Member member, @PathVariable Long receiverId) {
        friendService.sendFriendRequest(member.getId(), receiverId);
        return ResponseEntity.ok("친구 신청이 완료되었습니다.");
    }

    //친구 수락 API
    @PostMapping("/accept/{receiverId}")
    public ResponseEntity<String> acceptFriendRequest(@LoginUser Member member, @PathVariable Long receiverId) {
        friendService.acceptFriendRequest(member, receiverId);
        return ResponseEntity.ok("친구 요청을 수락하였습니다.");
    }

    //친구 목록 조회 API
    @GetMapping
    public ResponseEntity<List<FriendResponseDto>> getFriendList(@LoginUser Member member) {
        List<FriendResponseDto> friendList = friendService.getAllFriends(member);
        return ResponseEntity.ok(friendList);
    }

    //친구 삭제 API
    @DeleteMapping("/{friendId}")
    public ResponseEntity<String> deleteFriend(@LoginUser Member member , @PathVariable Long friendId) {
        friendService.deleteFriend(member, friendId);
        return ResponseEntity.ok("친구가 삭제되었습니다.");
    }

}

