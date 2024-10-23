package com.sparta.newsfeedproject.domain.member.service;

import com.sparta.newsfeedproject.domain.config.security.PasswordEncoder;
import com.sparta.newsfeedproject.domain.exception.CustomException;
import com.sparta.newsfeedproject.domain.jwt.JwtUtil;
import com.sparta.newsfeedproject.domain.member.command.MemberSignUpCommand;
import com.sparta.newsfeedproject.domain.member.dto.MemberLoginResponseDto;
import com.sparta.newsfeedproject.domain.member.dto.MemberProfileResponseDto;
import com.sparta.newsfeedproject.domain.member.dto.MemberSignUpResponseDto;
import com.sparta.newsfeedproject.domain.member.dto.ProfileUpdateRequestDto;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import com.sparta.newsfeedproject.domain.member.eunm.MembershipStatus;
import com.sparta.newsfeedproject.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sparta.newsfeedproject.domain.exception.eunm.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public MemberSignUpResponseDto signUpMember(MemberSignUpCommand command) {
        this.isDuplicateMember(command);
        String password = passwordEncoder.encode(command.getPassword());
        Member member = Member.builder()
                .email(command.getEmail())
                .nickName(command.getNickName())
                .password(password)
                .country(command.getCountry())
                .status(MembershipStatus.ACTIVE)
                .build();
        memberRepository.save(member);
        return MemberSignUpResponseDto.builder()
                .nickName(member.getNickName())
                .id(member.getId())
                .build();
    }

    public MemberLoginResponseDto loginMember(String email, String password) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new CustomException(LOGIN_FAILED));
        if (!member.isValidPassword(password, passwordEncoder)) {
            throw new CustomException(LOGIN_FAILED);
        }
        String token = jwtUtil.createToken(email);
        return MemberLoginResponseDto.builder()
                .id(member.getId())
                .token(token)
                .nickName(member.getNickName())
                .build();
    }

    //본인 프로필 조회
    public String getMyProfile (Member member) {
        return "N/A";
    }

    //타인 프로필 조회
    public String getOtherProfile(Long targetId) {
        // ID로 회원을 조회
        Member member = memberRepository.findById(targetId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND)); //ErrorCode enum 사용

        //회원탈퇴 확인
        if (member.getStatus() == MembershipStatus.INACTIVE) {
            throw new CustomException(INACTIVE_MEMBER);
        }

        // Member Entity로부터 MemberProfileResponseDto를 생성
        return "N/A";
    }

    //프로필 수정
    @Transactional
    public String updateProfile(Member member, ProfileUpdateRequestDto requestDto) {
        if (!member.isValidPassword(requestDto.getPassword(), passwordEncoder)) {
            throw new CustomException(INVALID_PASSWORD);
        }

        if (!member.getNickName().equals(requestDto.getNickname()) &&
                memberRepository.existsByNickName(requestDto.getNickname())) {
            throw new CustomException(ALREADY_NICKNAME);
        }

        member.update(requestDto.getNickname(), requestDto.getCountry());
        memberRepository.save(member);

        return "프로필이 수정되었습니다.";
    }

    private void isDuplicateMember(MemberSignUpCommand command) {
        Optional<Member> foundMember = memberRepository.findByEmailOrNickName(
                command.getEmail(), command.getNickName()
        );
        if (foundMember.isPresent()) {
            Member existingMember = foundMember.get();
            if (existingMember.getEmail().equals(command.getEmail())) {
                throw new CustomException(ALREADY_EMAIL);
            }

            if (existingMember.getNickName().equals(command.getNickName())) {
                throw new CustomException(ALREADY_NICKNAME);
            }
        }
    }
}