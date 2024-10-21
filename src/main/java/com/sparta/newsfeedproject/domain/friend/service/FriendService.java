package com.sparta.newsfeedproject.domain.friend.service;

import com.sparta.newsfeedproject.domain.friend.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class FriendService {

    private final FriendRepository friendRepository;
}
