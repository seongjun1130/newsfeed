package com.sparta.newsfeedproject.domain.friend.service;

import com.sparta.newsfeedproject.domain.exception.CustomException;
import com.sparta.newsfeedproject.domain.exception.eunm.ErrorCode;
import com.sparta.newsfeedproject.domain.friend.dto.FriendNewsResponseDto;
import com.sparta.newsfeedproject.domain.friend.dto.FriendResponseDto;
import com.sparta.newsfeedproject.domain.friend.entity.Friend;
import com.sparta.newsfeedproject.domain.friend.entity.FriendRequest;
import com.sparta.newsfeedproject.domain.friend.repository.FriendRepository;
import com.sparta.newsfeedproject.domain.friend.repository.FriendRequestRepository;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import com.sparta.newsfeedproject.domain.member.repository.MemberRepository;
import com.sparta.newsfeedproject.domain.news.entity.News;
import com.sparta.newsfeedproject.domain.news.repository.NewsRepository;
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
    private final NewsRepository newsRepository;

    @Transactional
    public void sendFriendRequest(Long requesterId, Long targetId) {
        // 가입한 회원인지 확인
        Member requester = memberRepository.findById(requesterId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Member receiver = memberRepository.findById(targetId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        //예외처리 메서드
        validateFriendRequest(requesterId, targetId, requester, receiver);

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.sendRequest(requester, receiver);
        friendRequestRepository.save(friendRequest);
    }

    @Transactional
    public void acceptFriendRequest(Member member, Long targetId) {
        // 수신자 정보 확인
        Member receiver = memberRepository.findById(targetId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 친구 요청이 있는지 확인
        FriendRequest friendRequest = friendRequestRepository.findByRequesterAndReceiver(receiver, member)
                .orElseThrow(() -> new CustomException(ErrorCode.REQUEST_NOT_FOUND));

        // 로그인한 회원의 친구목록에 추가
        Friend friend1 = new Friend(member, receiver);
        Friend friend2 = new Friend(receiver, member);
        friendRepository.save(friend1);
        friendRepository.save(friend2);

        // 친구 요청 테이블에서 데이터 삭제
        friendRequestRepository.delete(friendRequest);
    }

    @Transactional
    public List<FriendResponseDto> getAllFriends(Member member) {
        // 로그인한 사용자의 ID를 가져옵니다.
        Long memberId = member.getId();

        // 친구 목록을 조회 (한쪽 방향으로만 조회)
        List<Friend> friends = friendRepository.findAllByMemberId(memberId);

        // 친구 정보를 Set을 사용하여 중복 제거
        Set<FriendResponseDto> friendResponseDtos = new HashSet<>();

        for (Friend friend : friends) {
            // member_id가 로그인한 사용자일 경우만 처리, 즉 한쪽 방향의 관계만 추가
            if (friend.getMember().getId().equals(memberId)) {
                friendResponseDtos.add(new FriendResponseDto(
                        friend.getFriend().getId(),
                        friend.getFriend().getEmail(),
                        friend.getFriend().getNickName(),
                        friend.getFriend().getCountry()
                ));
            }
        }

        // Set을 List로 변환하여 반환
        // 정렬 (닉네임 기준 오름차순 정렬)
        return friendResponseDtos.stream()
                .sorted(Comparator.comparing(FriendResponseDto::getNickname)) // 닉네임 기준으로 정렬
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteFriend(Member member, Long targetId) {
        // 삭제할 친구 조회
        Member friend = memberRepository.findById(targetId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 친구 관계 삭제 (member->friend, friend->member 모두 삭제)
        friendRepository.deleteAllByMemberAndFriend(member, friend);
    }

    @Transactional
    public List<FriendNewsResponseDto> getFriendNewsList(Member member) {
        Long memberId = member.getId();

        // 친구들의 ID 목록을 가져옴
        List<Long> friendIds = friendRepository.findFriendIdsByMemberId(memberId);

        // 친구들의 게시물만 가져오되 자신의 게시물은 제외
        List<News> friendsNewsList = newsRepository.findByMemberIdInAndExcludeOwn(friendIds, memberId);

        return friendsNewsList.stream()
                .sorted(Comparator.comparing(News::getModifiedAt).reversed())
                .map(news -> FriendNewsResponseDto.builder()
                        .newsId(news.getId())
                        .title(news.getTitle())
                        .content(news.getContent())
                        .authorNickname(news.getMember().getNickName())
                        .commentsCount(news.getComments().size())
                        .modifiedAt(news.getModifiedAt())
                        .build())
                .toList();
    }

    private void validateFriendRequest(Long requesterId, Long targetId, Member requester, Member target) {
        // 1. 자신에게 친구 요청을 보내는 경우 예외 처리
        if (requesterId.equals(targetId)) {
            throw new CustomException(ErrorCode.CANNOT_FRIEND_YOURSELF);
        }

        // 2. 이미 친구인 경우 예외 처리
        boolean friendStatus = friendRepository.existsByMemberAndFriend(requester, target);
        if (friendStatus) {
            throw new CustomException(ErrorCode.ALREADY_FRIEND);
        }

        // 3. 이미 요청된 경우 예외 처리
        boolean requestStatus = friendRequestRepository.existsByRequesterAndReceiver(requester, target);
        if (requestStatus) {
            throw new CustomException(ErrorCode.ALREADY_REQUEST);
        }

        // 4. 상대방이 이미 나에게 친구 요청을 보낸 경우 (교차 요청 방지)
        boolean isCrossRequestSent = friendRequestRepository.existsByRequesterAndReceiver(target, requester);
        if (isCrossRequestSent) {
            throw new CustomException(ErrorCode.ALREADY_REQUEST);
        }
    }


}
