package com.sparta.newsfeedproject.domain.member.controller;

import com.sparta.newsfeedproject.domain.jwt.JwtUtil;
import com.sparta.newsfeedproject.domain.member.command.MemberSignUpCommand;
import com.sparta.newsfeedproject.domain.member.dto.MemberLoginRequestDto;
import com.sparta.newsfeedproject.domain.member.dto.MemberLoginResponseDto;
import com.sparta.newsfeedproject.domain.member.dto.MemberSignUpRequestDto;
import com.sparta.newsfeedproject.domain.member.dto.MemberSignUpResponseDto;
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
    @GetMapping("/index")
    public void test(@LoginUser Member member){
        System.out.println(member.getNickName());
    }
}
