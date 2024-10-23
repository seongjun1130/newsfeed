package com.sparta.newsfeedproject.domain.member.service;

import com.sparta.newsfeedproject.domain.config.security.PasswordEncoder;
import com.sparta.newsfeedproject.domain.exception.CustomException;
import com.sparta.newsfeedproject.domain.friend.repository.FriendRepository;
import com.sparta.newsfeedproject.domain.friend.repository.FriendRequestRepository;
import com.sparta.newsfeedproject.domain.jwt.JwtUtil;
import com.sparta.newsfeedproject.domain.like.repository.LikeRepository;
import com.sparta.newsfeedproject.domain.member.command.MemberSignUpCommand;
import com.sparta.newsfeedproject.domain.member.dto.*;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import com.sparta.newsfeedproject.domain.member.eunm.MembershipStatus;
import com.sparta.newsfeedproject.domain.member.repository.MemberRepository;
import com.sparta.newsfeedproject.domain.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sparta.newsfeedproject.domain.exception.eunm.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final NewsRepository newsRepository;
    private final FriendRepository friendRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final LikeRepository likeRepository;
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
                .id(member.getId())
                .message("회원가입 되었습니다.")
                .build();
    }

    public MemberLoginResponseDto loginMember(String email, String password) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new CustomException(LOGIN_FAILED));
        if (member.isInactive()) {
            throw new CustomException(INACTIVE_MEMBER);
        }
        if (!member.isValidPassword(password, passwordEncoder)) {
            throw new CustomException(LOGIN_FAILED);
        }
        String token = jwtUtil.createToken(email);
        return MemberLoginResponseDto.builder()
                .id(member.getId())
                .token(token)
                .message("로그인 되었습니다.")
                .build();
    }

    //본인 프로필 조회
    public MemberProfileResponseDto getMyProfile(Member member) {
        if (member.isInactive()) {
            throw new CustomException(INACTIVE_MEMBER);
        }
        return new MemberProfileResponseDto(member);
    }

    //타인 프로필 조회
    public MemberProfileResponseDto getOtherProfile(Long targetId) {
        // ID로 회원을 조회
        Member member = memberRepository.findById(targetId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND)); //ErrorCode enum 사용

        //회원탈퇴 확인
        if (member.getStatus() == MembershipStatus.INACTIVE) {
            throw new CustomException(INACTIVE_MEMBER);
        }

        // Member Entity로부터 MemberProfileResponseDto를 생성
        return new MemberProfileResponseDto(member);
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

    @Transactional
    public MemberDeleteResponseDto deleteMember(Long id, MemberDeleteRequestDto req) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        if (!member.isValidPassword(req.getPassword(), passwordEncoder)) {
            throw new CustomException(LOGIN_FAILED);
        }
        member.anonymizeMember();
        memberRepository.save(member);
        likeRepository.deleteByMemberId(member.getId());
        newsRepository.deleteByMemberId(member.getId());
        friendRepository.deleteAllByMemberOrFriend(member.getId());
        friendRequestRepository.deleteByReceiverIdOrRequesterId(member.getId());
        return MemberDeleteResponseDto
                .builder()
                .id(member.getId())
                .message("회원탈퇴 되었습니다.")
                .build();
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