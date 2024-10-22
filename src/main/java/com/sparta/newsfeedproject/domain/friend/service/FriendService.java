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

import java.util.List;
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

    public List<FriendResponseDto> getFriendList(Member member) {

        //친구 목록 조회
        List<Friend> friends = friendRepository.findByMember(member);

        //조회된 친구 목록을 DTO로 변환
        return friends.stream()
                .map(FriendResponseDto::fromFriend)
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
