package com.sparta.newsfeedproject.domain.friend.repository;

import com.sparta.newsfeedproject.domain.friend.entity.FriendRequest;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    boolean existsByRequesterAndReceiver(Member requester, Member receiver);

    Optional<FriendRequest> findByRequesterAndReceiver(Member receiver, Member member);

    @Modifying
    @Query("DELETE FROM FriendRequest fr WHERE fr.receiver.id = :memberId OR fr.requester.id = :memberId")
    void deleteByReceiverIdOrRequesterId(Long memberId);
}
