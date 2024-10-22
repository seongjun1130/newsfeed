package com.sparta.newsfeedproject.domain.friend.repository;

import com.sparta.newsfeedproject.domain.friend.entity.Friend;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    boolean existsByMemberAndFriend(Member requester, Member receiver);

    List<Friend> findByMember(Member member);
}
