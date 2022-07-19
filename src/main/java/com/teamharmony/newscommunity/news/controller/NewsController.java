package com.teamharmony.newscommunity.news.controller;

import com.amazonaws.Response;
import com.teamharmony.newscommunity.common.ApiResponse;
import com.teamharmony.newscommunity.news.dto.CreateNewsAccessLogRequestDto;
import com.teamharmony.newscommunity.news.dto.NewsDetailResponseDto;
import com.teamharmony.newscommunity.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 뉴스 일괄 조회, 뉴스 상세 조회, 뉴스 접근 로그 생성, 조회수 증가 기능을 하는 컨트롤러
 * @author  hyeoKing
 */
@RequestMapping("/api/news")
@RequiredArgsConstructor
@RestController
public class NewsController {
    private final NewsService newsService;

    /**
     * @return 뉴스정보(NewsTable)리스트가 담긴 ResponseNewsDTO를 리턴
     */
    @GetMapping("")
    public ResponseEntity<List<NewsDetailResponseDto>> getNews(){
        return ResponseEntity.status(HttpStatus.OK).body(newsService.getNews());
    }

    /**
     *  해당 id의 뉴스 정보를 리턴해주는 역할을 한다.
     * @param       newsId 해당 뉴스 id
     * @return      뉴스 상세 정보가 담긴 ResponseNewsDetailDTO
     */
    @GetMapping("/details/{newsId}")
    public ResponseEntity<NewsDetailResponseDto> getNewsDetial(@PathVariable String newsId){
        return ResponseEntity.status(HttpStatus.OK).body(newsService.getNewsDetail(newsId));
    }

    /**
     * @param requestCreateNewsAccessLogDTO newsId, userId, newsTitle
     * @return 뉴스 접근 로그의  id
     * @See NewsService#setNewsAccessLog
     */
    @PostMapping("/logs")
    public ResponseEntity<String> createNewsAccessLog(@RequestBody CreateNewsAccessLogRequestDto requestCreateNewsAccessLogDTO){
        String newsId = newsService.setNewsAccessLog(requestCreateNewsAccessLogDTO);
        return ResponseEntity.status(HttpStatus.OK).body(newsId);
    }

    /**
     * @param newsId 조회수 업데이트를 위해 뉴스를 찾는데 필요한 newsId
     * @return 조회수 업데이트 성공여부
     */
    @PutMapping("/views/{newsId}")
    public ResponseEntity<String> UpdateView(@PathVariable String newsId){
        newsService.addView(newsId);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }
}
