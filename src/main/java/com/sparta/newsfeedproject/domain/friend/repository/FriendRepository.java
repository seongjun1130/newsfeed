package com.sparta.newsfeedproject.domain.friend.repository;

import com.sparta.newsfeedproject.domain.friend.entity.Friend;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    boolean existsByMemberAndFriend(Member requester, Member receiver);

    @Query("SELECT f FROM Friend f WHERE f.member.id = :memberId OR f.friend.id = :memberId")
    List<Friend> findAllByMemberId(@Param("memberId") Long memberId);

    @Modifying
    @Query("DELETE FROM Friend f WHERE f.member.id = :memberId OR f.friend.id = :memberId")
    void deleteAllByMemberOrFriend(@Param("memberId") Long memberId);
}
