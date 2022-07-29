package com.teamharmony.newscommunity.domain.bookmarks.service;

import com.teamharmony.newscommunity.domain.bookmarks.dto.BookmarkRequestDto;
import com.teamharmony.newscommunity.domain.bookmarks.dto.BookmarkResponseDto;
import com.teamharmony.newscommunity.domain.bookmarks.entity.Bookmarks;
import com.teamharmony.newscommunity.domain.bookmarks.repository.BookmarkRepository;
import com.teamharmony.newscommunity.exception.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;

    /**
     * /api/bookmarks의 경로로 요청된 POST 메서드의 응답을 처리하는 메서드, 북마크 로그를 생성
     * @param requestBookmarkDTO
     * @return 저장된 북마크 로그의 id가 리턴
     */

    public String createBookmark(BookmarkRequestDto requestBookmarkDTO){
        if (checkBookmarkDuplicate(requestBookmarkDTO) != null){
            throw InvalidRequestException.builder()
                    .message("해당 사용자는 이미 해당 뉴스에 북마크 되어있기 때문에 북마크 요청을 할 수 없습니다.")
                    .invalidValue(("newsId: " + requestBookmarkDTO.getNewsId() + ", userId: "+ requestBookmarkDTO.getNewsId()))
                    .code("B402")
                    .build();
        }

        Bookmarks bookmark = Bookmarks.builder()
                .newsId(requestBookmarkDTO.getNewsId())
                .userId(requestBookmarkDTO.getUserId())
                .title(requestBookmarkDTO.getTitle())
                .build();
        return bookmarkRepository.save(bookmark).getId();
    }

    /**
     * /api/bookmarks의 경로로 요청된 GET 메서드의 응답을 처리하는 메서드, 북마크 여부 검증을 함
     * @param requestBookmarkDTO
     * @return 해당 newsId, userId의 복합키가 북마크 db에 존재하는지 여부 리턴(true||false)
     **/

    public Boolean isBookmarkCheck(BookmarkRequestDto requestBookmarkDTO){
        if (checkBookmarkDuplicate(requestBookmarkDTO) == null){
            return false;
        }
        return true;
    }

    /**
     * /api/bookmarks의 경로로 요청된 DELETE 메서드의 응답을 처리하는 메서드. 북마크 취소의 기능으로, db에 있는 해당 조건의 기록을 삭제
     * @param requestBookmarkDTO
     * @return 해당 newsId, userId의 복합키가 북마크 db에 존재하는지 여부 리턴(true||false)
     **/

    @Transactional
    public String deleteBookmark(BookmarkRequestDto requestBookmarkDTO){
        // + DONE: 북마크가 존재하지 않으면 예외처리
        if (checkBookmarkDuplicate(requestBookmarkDTO) == null){
            throw InvalidRequestException.builder()
                    .message("해당 사용자는 북마크 하지 않았기 때문에 북마크 해제 요청을 할 수 없습니다.")
                    .invalidValue(("newsId: " + requestBookmarkDTO.getNewsId() + ", userId: "+ requestBookmarkDTO.getNewsId()))
                    .code("B401")
                    .build();
        }
        // + TODO: 북마크가 존재하지 않으면 예외처리
        bookmarkRepository.deleteBookmarkByNewsIdAndUserId(requestBookmarkDTO.getNewsId(), requestBookmarkDTO.getUserId());
        return requestBookmarkDTO.getNewsId();
    }

    /**
     * /api/bookmarks/profiles/{userId}의 경로로 요청된 GET 메서드의 응답을 처리하는 메서드, 해당 userId가 포함된 bookmark리스트 리턴
     * @param userId
     * @return 해당 userId가 포함된 bookmark 리스트
     **/

    public List<BookmarkResponseDto> selectAllUserBookmark(String userId) {
        List<Bookmarks> bookmarksList = bookmarkRepository.findAllByUserId(userId);
        List<BookmarkResponseDto> bookmarkDtoList = bookmarksList.stream().map(BookmarkResponseDto::toDto).collect(toList());

        return bookmarkDtoList;
    }

    /**
     * 북마크 여부 파악을 위한 메서드
     * @param requestBookmarkDTO
     * @return 북마크 존재시 true, 그 외 false
     **/

    public Bookmarks checkBookmarkDuplicate(BookmarkRequestDto requestBookmarkDTO){
        Bookmarks bookmarks = bookmarkRepository.findByNewsIdAndUserId(requestBookmarkDTO.getNewsId(), requestBookmarkDTO.getUserId());
        return bookmarks;
    }
}