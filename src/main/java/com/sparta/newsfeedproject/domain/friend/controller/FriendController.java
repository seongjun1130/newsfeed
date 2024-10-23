package com.sparta.newsfeedproject.domain.friend.controller;

import com.sparta.newsfeedproject.domain.friend.dto.FriendNewsResponseDto;
import com.sparta.newsfeedproject.domain.friend.dto.FriendResponseDto;
import com.sparta.newsfeedproject.domain.friend.dto.MessageResponseDto;
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
    @PostMapping("/request/{targetId}")
    public ResponseEntity<MessageResponseDto> sendFriendRequest(@LoginUser Member member, @PathVariable Long targetId) {
        friendService.sendFriendRequest(member.getId(), targetId);
        MessageResponseDto response = new MessageResponseDto(
                member.getId(), targetId, "친구 신청이 완료되었습니다."
        );
        return ResponseEntity.ok(response);
    }

    //친구 수락 API
    @PostMapping("/accept/{targetId}")
    public ResponseEntity<MessageResponseDto> acceptFriendRequest(@LoginUser Member member, @PathVariable Long targetId) {
        friendService.acceptFriendRequest(member, targetId);
        MessageResponseDto response = new MessageResponseDto(
                member.getId(), targetId, "친구 요청을 수락하였습니다."
        );
        return ResponseEntity.ok(response);
    }

    //친구 삭제 API
    @DeleteMapping("/{targetId}")
    public ResponseEntity<MessageResponseDto> deleteFriend(@LoginUser Member member, @PathVariable Long targetId) {
        friendService.deleteFriend(member, targetId);
        MessageResponseDto response = new MessageResponseDto(
                member.getId(), targetId, "친구가 삭제되었습니다.");
        return ResponseEntity.ok(response);
    }

    //친구 목록 조회 API
    @GetMapping
    public ResponseEntity<List<FriendResponseDto>> getFriendList(@LoginUser Member member) {
        List<FriendResponseDto> friendList = friendService.getAllFriends(member);
        return ResponseEntity.ok(friendList);
    }



    //친구의 News 전체 조회
    @GetMapping("/news")
    public ResponseEntity<List<FriendNewsResponseDto>> getFriendNewsList(@LoginUser Member member) {
        List<FriendNewsResponseDto> friendList = friendService.getFriendNewsList(member);
        return ResponseEntity.ok(friendList);
    }

}

