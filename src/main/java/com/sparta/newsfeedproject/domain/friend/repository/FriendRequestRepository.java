package com.sparta.newsfeedproject.domain.friend.repository;

import com.sparta.newsfeedproject.domain.friend.entity.FriendRequest;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    boolean existsByRequesterAndReceiver(Member requester, Member receiver);
}
