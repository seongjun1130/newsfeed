package com.sparta.newsfeedproject.domain.friend.repository;

import com.sparta.newsfeedproject.domain.friend.entity.Friend;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    boolean existsByMemberAndFriend(Member requester, Member receiver);
}
