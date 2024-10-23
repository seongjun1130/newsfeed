package com.sparta.newsfeedproject.domain.member.controller;

import com.sparta.newsfeedproject.domain.jwt.JwtUtil;
import com.sparta.newsfeedproject.domain.member.command.MemberSignUpCommand;
import com.sparta.newsfeedproject.domain.member.dto.*;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import com.sparta.newsfeedproject.domain.member.resolver.util.LoginUser;
import com.sparta.newsfeedproject.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<MemberSignUpResponseDto> signUpMember(@Valid @RequestBody MemberSignUpRequestDto req) {
        MemberSignUpCommand command = new MemberSignUpCommand(req);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(memberService.signUpMember(command));
    }

    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponseDto> loginMember(@Valid @RequestBody MemberLoginRequestDto req, HttpServletResponse res) {
        MemberLoginResponseDto response = memberService.loginMember(req.getEmail(), req.getPassword());
        jwtUtil.addJwtToCookie(response.getToken(), res);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    //본인 프로필 조회
    @GetMapping("/profil")
    //현재 인증된 회원의 정보를 조회
    public ResponseEntity<String> getMyProfile (@LoginUser Member member) {
       String message = memberService.getMyProfile(member);
        return ResponseEntity.ok(message);
    }

    //타인 프로필 조회
    @GetMapping("/profil/{targetId}")
    public ResponseEntity<String> getOtherProfile (@PathVariable("targetId") Long targetId) {
        String message = memberService.getOtherProfile(targetId);
        return ResponseEntity.ok(message);
    }

    //프로필 수정
    @PutMapping("/profil")
    public ResponseEntity<String> updateProfile(@LoginUser Member member, @Valid @RequestBody ProfileUpdateRequestDto requestDto) {
        String message = memberService.updateProfile(member, requestDto);
        return ResponseEntity.ok(message);
    }
}
