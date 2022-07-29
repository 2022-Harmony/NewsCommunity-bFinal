package com.teamharmony.newscommunity.domain.bookmarks.repository;

import com.teamharmony.newscommunity.domain.bookmarks.entity.Bookmarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**북마크 정보를 담을 BookmarkRepository, 아래는 관력 역할
 * @GET: 북마크 여부 검증, 해당 user의 북마크 리스트 조회          :(/api/bookmarks)와 (/api/bookmarks/profile/{userId})
 * @POST: 북마크 생성                                          : (/api/bookmarks)
 * @PUT: NULL                                                :
 * @DELETE: 북마크 삭제                                        : (/api/bookmarks)
 * = 뉴스정보 조회를 위한 역할과 조회수 업데이트를 위한 역할수행
 */
@Repository
public interface BookmarkRepository extends JpaRepository<Bookmarks, String> {
    Bookmarks findByNewsIdAndUserId(String newsId, String userId);       // 북마크 생성시 예외처리와 검증시에 필요한 조회 쿼리
    List<Bookmarks> findAllByUserId(String userId);                      // user의 북마크 리스트 조회를 위해 필요한 쿼리
    void deleteBookmarkByNewsIdAndUserId(String newsId, String userId); // 북마크 삭제를 위해 필요한 쿼리
}