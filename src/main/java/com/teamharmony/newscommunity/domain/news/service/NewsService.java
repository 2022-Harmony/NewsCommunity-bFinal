package com.teamharmony.newscommunity.domain.news.service;

import com.teamharmony.newscommunity.domain.news.dto.CreateNewsAccessLogRequestDto;
import com.teamharmony.newscommunity.domain.news.repository.NewsRepository;
import com.teamharmony.newscommunity.exception.InvalidRequestException;
import com.teamharmony.newscommunity.domain.news.dto.NewsDetailResponseDto;
import com.teamharmony.newscommunity.domain.news.entity.NewsAccessLog;
import com.teamharmony.newscommunity.domain.news.entity.NewsTable;
import com.teamharmony.newscommunity.domain.news.repository.NewsAccessLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;


/**
 *  뉴스 정보 조회 기능을 수행하는 Service
 * @author hyeoKing
 */
@RequiredArgsConstructor
@Service
public class NewsService {
    private final NewsRepository newsRepository;
    private final NewsAccessLogRepository newsAccessLogRepository;


    /**
     * /api/news의 경로로 요청된 GET 메서드의 응답을 처리하는 메서드
     * @param None
     * @Return 뉴스 제목, 요약, 이미지_url, 실제뉴스URL, 설명, 작성시간이 담긴 List<NewsDetailResponseDto>
     **/

    public List<NewsDetailResponseDto> getNews(){
        List<NewsTable> newsTableList = newsRepository.findAll();
        return newsTableList.stream().map(NewsDetailResponseDto::toDto).collect(toList());
    }


    /** /api/news/details의 경로로 요청된 GET 메서드의 응답을 처리하는 메서드, news의 id로 뉴스 내용 조회
     * @param: newsId
     * @Return: ResponseNewsDetailDTO // newsid, title, summary, image_url, news_url, write_time, view 가 담김
     **/

    public NewsDetailResponseDto getNewsDetail(String newsId){
        NewsTable newsTable = newsRepository.findById(newsId).orElseThrow(
                ()-> InvalidRequestException.builder()
                        .message("존재하지 않는 newsId에 대해 요청했습니다.")
                        .invalidValue("newsId: "+ newsId)
                        .code("N401")
                        .build()
        );
        return NewsDetailResponseDto.builder()
                .id(newsTable.getId())
                .title(newsTable.getTitle())
                .summary(newsTable.getSummary())
                .image_url(newsTable.getImage_url())
                .news_url(newsTable.getNews_url())
                .write_time(newsTable.getWrite_time())
                .view(newsTable.getView())
                .build();
    }


    /**
     * /api/news/logs의 경로로 요청된 POST 메서드의 응답을 처리하는 메서드, 클릭한 news의 id와 유저 id로 접근
     * @param requestCreateNewsAccessLogDTO
     * @return
     */

    public String setNewsAccessLog(CreateNewsAccessLogRequestDto requestCreateNewsAccessLogDTO){
        NewsAccessLog newsAccessLog = NewsAccessLog.builder()
                .requestCreateNewsAccessLogDTO(requestCreateNewsAccessLogDTO)
                .build();                               // newsAccessLog를 requestCreateNewsAccessLogDTO로 초기화
        newsAccessLogRepository.save(newsAccessLog);    // newsAccessLogRepository를 통해 newsAccessRepository에 newsAccessLog 저장
        return newsAccessLog.getNews_id();
    }



    /**
     * /api/news/view의 경로로 요청된 메서드의 응답을 처리하는 함수, 해당 newsId의 view 컬럼의 값을 1 추가
     * @param newsId
     */

    @Transactional
    public void addView(String newsId) {
        NewsTable newsTable = newsRepository.findById(newsId).orElseThrow(
                ()-> InvalidRequestException.builder()
                                        .message("존재하지 않는 newsId에 대해 요청했습니다.")
                                        .invalidValue("newsId: "+ newsId)
                                        .code("N402")
                                        .build()
        );
        newsTable.updateView();
        newsRepository.save(newsTable);
    }
}