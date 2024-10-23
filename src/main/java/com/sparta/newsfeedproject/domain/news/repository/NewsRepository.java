package com.sparta.newsfeedproject.domain.news.repository;

import com.sparta.newsfeedproject.domain.news.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    void deleteByMemberId(Long id);

    List<News> findByMemberIdIn(List<Long> friendIds);

    // 친구의 뉴스를 가져오는 메서드
    @Query("SELECT n FROM News n WHERE n.member.id IN :friendIds AND n.member.id <> :memberId")
    List<News> findByMemberIdInAndExcludeOwn(@Param("friendIds") List<Long> friendIds, @Param("memberId") Long memberId);
}
