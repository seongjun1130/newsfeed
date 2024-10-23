package com.sparta.newsfeedproject.domain.friend.repository;

import com.sparta.newsfeedproject.domain.friend.entity.Friend;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    boolean existsByMemberAndFriend(Member requester, Member receiver);

    @Query("SELECT f FROM Friend f WHERE f.member.id = :memberId OR f.friend.id = :memberId")
    List<Friend> findAllByMemberId(@Param("memberId") Long memberId);

    //로그인한 사용자와 특정 친구간의 관계를 찾는 메서드
    //친구 관계를 설정할 때 양방향 관계이기 때문에 member_id,friend_id의 관계가 동일하게 유지되지 않는다
    //그로인해 양방향 관계 쿼리를 사용해서 로그인한 회원이 가진 id값이 friend_id이더라도 삭제할 수 있도록 Query추가
    @Query("SELECT f FROM Friend f WHERE (f.member = :member AND f.friend = :friend) OR (f.member = :friend AND f.friend = :member)")
    Optional<Friend> findByMemberAndFriend(@Param("member") Member member, @Param("friend") Member friend);

    // 친구 삭제
    void deleteByMemberAndFriend(@Param("member") Member member, @Param("friend") Member friend);

    @Modifying
    @Query("DELETE FROM Friend f WHERE f.member.id = :memberId OR f.friend.id = :memberId")
    void deleteAllByMemberOrFriend(@Param("memberId") Long memberId);

}
