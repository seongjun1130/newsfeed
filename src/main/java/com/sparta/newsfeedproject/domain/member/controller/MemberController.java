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

    @DeleteMapping("/profil")
    public ResponseEntity<MemberDeleteResponseDto> deleteMember(@LoginUser Member member, @Valid @RequestBody MemberDeleteRequestDto req) {
        memberService.deleteMember(member.getId(), req);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    //본인 프로필 조회
    @GetMapping("/profil")
    //현재 인증된 회원의 정보를 조회
    public ResponseEntity<MemberProfileResponseDto> getMyProfile(@LoginUser Member member) {
        MemberProfileResponseDto profile = memberService.getMyProfile(member);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(profile);
    }

    //타인 프로필 조회
    @GetMapping("/profil/{targetId}")
    public ResponseEntity<MemberProfileResponseDto> getOtherProfile(@PathVariable("targetId") Long targetId) {
        MemberProfileResponseDto profile = memberService.getOtherProfile(targetId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(profile);
    }

    //프로필 수정
    @PutMapping("/profil")
    public ResponseEntity<ProfileUpdateResponseDto> updateProfile(@LoginUser Member member, @Valid @RequestBody ProfileUpdateRequestDto requestDto) {
        String message = memberService.updateProfile(member, requestDto);
        ProfileUpdateResponseDto responseDto = new ProfileUpdateResponseDto(member.getId(), message);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }
}
