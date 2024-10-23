package com.sparta.newsfeedproject.domain.friend.service;

import com.sparta.newsfeedproject.domain.exception.CustomException;
import com.sparta.newsfeedproject.domain.exception.eunm.ErrorCode;
import com.sparta.newsfeedproject.domain.friend.dto.FriendResponseDto;
import com.sparta.newsfeedproject.domain.friend.entity.Friend;
import com.sparta.newsfeedproject.domain.friend.entity.FriendRequest;
import com.sparta.newsfeedproject.domain.friend.repository.FriendRepository;
import com.sparta.newsfeedproject.domain.friend.repository.FriendRequestRepository;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import com.sparta.newsfeedproject.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FriendService {

    private final FriendRepository friendRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void sendFriendRequest(Long requesterId, Long receiverId) {
        // 가입한 회원인지 확인
        Member requester = memberRepository.findById(requesterId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Member receiver = memberRepository.findById(receiverId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        //예외처리 메서드
        validateFriendRequest(requesterId, receiverId, requester, receiver);

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.sendRequest(requester, receiver);
        friendRequestRepository.save(friendRequest);
    }

    @Transactional
    public void acceptFriendRequest(Member member, Long receiverId) {
        // 수신자 정보 확인
        Member receiver = memberRepository.findById(receiverId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 친구 요청이 있는지 확인
        FriendRequest friendRequest = friendRequestRepository.findByRequesterAndReceiver(receiver, member)
                .orElseThrow(() -> new CustomException(ErrorCode.REQUEST_NOT_FOUND));

        // 로그인한 회원의 친구목록에 추가
        Friend friend = new Friend(member, receiver);
        friendRepository.save(friend);

        // 친구 요청 테이블에서 데이터 삭제
        friendRequestRepository.delete(friendRequest);
    }

    @Transactional
    public List<FriendResponseDto> getAllFriends(Member member) {
        Long memberId = member.getId(); // 로그인한 사용자의 ID를 가져옵니다.

        // 회원 ID를 기반으로 친구 목록을 조회
        List<Friend> friends = friendRepository.findAllByMemberId(memberId);

        // 친구 정보를 Set을 사용하여 중복 제거
        Set<FriendResponseDto> friendResponseDtos = new HashSet<>();

        for (Friend friend : friends) {
            // member_id가 로그인한 사용자일 경우
            if (friend.getMember().getId().equals(memberId)) {
                friendResponseDtos.add(new FriendResponseDto(friend.getFriend().getEmail(), friend.getFriend().getNickName(), friend.getFriend().getCountry()));
            } else { // friend_id가 로그인한 사용자일 경우
                friendResponseDtos.add(new FriendResponseDto(friend.getMember().getEmail(), friend.getMember().getNickName(), friend.getMember().getCountry()));
            }
        }

        // Set을 List로 변환하여 반환
        // 정렬 (닉네임 기준 오름차순 정렬)
        return friendResponseDtos.stream()
                .sorted(Comparator.comparing(FriendResponseDto::getNickname)) // 닉네임 기준으로 정렬
                .collect(Collectors.toList());
    }

    private void validateFriendRequest(Long requesterId, Long receiverId, Member requester, Member receiver) {
        // 1. 자신에게 친구 요청을 보내는 경우 예외 처리
        if (requesterId.equals(receiverId)) {
            throw new CustomException(ErrorCode.CANNOT_FRIEND_YOURSELF);
        }

        // 2. 이미 친구인 경우 예외 처리
        boolean friendStatus = friendRepository.existsByMemberAndFriend(requester, receiver);
        if (friendStatus) {
            throw new CustomException(ErrorCode.ALREADY_FRIEND);
        }

        // 3. 이미 요청된 경우 예외 처리
        boolean requestStatus = friendRequestRepository.existsByRequesterAndReceiver(requester, receiver);
        if (requestStatus) {
            throw new CustomException(ErrorCode.ALREADY_REQUEST);
        }

        // 4. 상대방이 이미 나에게 친구 요청을 보낸 경우 (교차 요청 방지)
        boolean isCrossRequestSent = friendRequestRepository.existsByRequesterAndReceiver(receiver, requester);
        if (isCrossRequestSent) {
            throw new CustomException(ErrorCode.ALREADY_REQUEST);
        }
    }
}
