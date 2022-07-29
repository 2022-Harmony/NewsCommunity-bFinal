package com.teamharmony.newscommunity.news.service;

import com.teamharmony.newscommunity.domain.news.dto.CreateNewsAccessLogRequestDto;
import com.teamharmony.newscommunity.domain.news.dto.NewsDetailResponseDto;
import com.teamharmony.newscommunity.domain.news.entity.NewsAccessLog;
import com.teamharmony.newscommunity.domain.news.entity.NewsTable;
import com.teamharmony.newscommunity.domain.news.repository.NewsAccessLogRepository;
import com.teamharmony.newscommunity.domain.news.repository.NewsRepository;
import com.teamharmony.newscommunity.domain.news.service.NewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;


/**
 * NewsService에 대한 서비스 계층의 테스트코드
 * @author hyoeKing
 */
@ExtendWith(MockitoExtension.class)
class NewsServiceTest {

    @Nested
    @DisplayName("뉴스 접근 기록 관련 테스트")
    class NewsAccessTest {
        @Mock                                                            // 레포지 토리 직접 접근을 막기 위해, 목 객체로 주입받아 사용
        private NewsAccessLogRepository newsAccessLogRepository;         // newsAccess관련 서비스를 위한 레포지토리

        @InjectMocks                                                    // 목을 주입받아 사용할 service 등록
        private NewsService newsService;                                // 뉴스 접근 기록 생성, 조회수 증가 및 뉴스 정보 listing등의 기능을 하는 서비스


        @Test
        @DisplayName("뉴스 접근 기록 생성 테스트")
        void setNewsAccessLog() {
            when(this.newsAccessLogRepository.save(any(NewsAccessLog.class))) //when// // 2. save를 통해 인자값 입력시 첫번째 arg 리턴
                    .then(AdditionalAnswers.returnsFirstArg());

            CreateNewsAccessLogRequestDto expected =                          //given//   // 1. 예측값 생성, (Test Title라는 문자열을 title로 가지고있는 newsTable 객체)
                    CreateNewsAccessLogRequestDto.builder()
                            .news_id(UUID.randomUUID().toString())
                            .user_id("테스트용 아이디입니다")
                            .title("테스트용 타이틀입니다.")
                            .build();
            String actual = this.newsService.setNewsAccessLog(expected);      //then//  //3. 생성한 예측값을 Mock으로 주입받은 newsService를 통해 add
            assertEquals(expected.getNews_id(), actual);
        }
    }


    @Nested
    @DisplayName("뉴스 정보 관련 테스트")
    class NewsSeletTest{

        //0. 목 객체 주입 및 세팅 //
        @Mock
        private NewsRepository newsRepository;
        @InjectMocks
        private NewsService newsService;
        private NewsTable newsTable = NewsTable.builder()
                .id("test-uuid")
                .title("테스트 제목")
                .summary("테스트 요약")
                .image_url("테스트 이미지주소")
                .news_url("테스트 뉴스 주소")
                .explains("테스트 설명")
                .write_time("테스트작성 시간")
                .view(1L)
                .build();
        @BeforeEach
        void setup() {
            newsTable.setId("test-uuid");
            newsTable.setTitle("테스트 제목");
            newsTable.setSummary("테스트 요약");
            newsTable.setImage_url("테스트 이미지주소");
            newsTable.setNews_url("테스트 뉴스 주소");
            newsTable.setExplains("테스트 설명");
            newsTable.setWrite_time("테스트작성 시간");
            newsTable.setView(1L);
        }

        @DisplayName("뉴스 상세 정보 조회 테스트")
        @Test
        void getNewsDetail() {
            Optional<NewsTable> optional = Optional.of(newsTable);                      //1. given(1): optioanl로 지정한 newsTable을 리턴
            given(this.newsRepository.findById("test-uuid"))                            //1. given(2): newsRepository에 findById 요청을 보냈을 때,
                    .willReturn(optional);

            NewsDetailResponseDto actual = this.newsService.getNewsDetail("test-uuid");                  //2. when(1): newsService의 getNewsDetail로 test-uuid가 넘겨졌을 때, 실제값(actual)에 저장
            NewsDetailResponseDto expect = NewsDetailResponseDto.builder().id(optional.get().getId())
                    .title(optional.get().getTitle())
                    .summary(optional.get().getSummary())
                    .image_url(optional.get().getImage_url())
                    .news_url(optional.get().getNews_url())
                    .explains(optional.get().getExplains())
                    .write_time(optional.get().getWrite_time())
                    .view(optional.get().getView())
                    .build();   //2. when(2): 기존에 정의해둔 newsTable의 값을 expect값에 build

            assertEquals(actual.getId(), expect.getId());                               //3. then: actual과 expect를 비교
            assertEquals(actual.getNews_url(), expect.getNews_url());
            assertEquals(actual.getSummary(), expect.getSummary());
            assertEquals(actual.getTitle(), expect.getTitle());
            assertEquals(actual.getWrite_time(), expect.getWrite_time());
            assertEquals(actual.getView(), expect.getView());
            assertEquals(actual.getImage_url(), expect.getImage_url());
        }


        @DisplayName("뉴스 정보 일괄 조회 테스트")
        @Test
        void getNews(){
            doReturn(newsList()).when(newsRepository).findAll();                             // 1. given: newsRepository에서 findall을 호출시, 아래 newsList()를 통해 생성된 List<newsTable>이 반환되도록
            final List<NewsDetailResponseDto> expect = newsService.getNews();                // 2. when:  newsService의 getNews()가 호출되었을 때, 나온 ResponseNewsDTO값을 expect에 할당
            assertThat(expect.size()).isEqualTo(5);                                 // 3. Then:   expect의 크기가 5인지(예상하는 리스트 크기(5)) 확인
        }
        private List<NewsTable> newsList() {
            // 위 getNews() 테스트에서 사용할 newsTableList를 만들기 위한 함수
            List<NewsTable> newsTableList = new ArrayList<>();
            for (int i = 0; i < 5; i ++){
                newsTableList.add(newsTable);
            }
            return newsTableList;
        }


        @DisplayName("조회수 증가 테스트")
        @Test
        void addView() {
            NewsTable expected = newsTable;             //1. given: 예상값 expected의 Views가 0인 상태로 세팅한다
            expected.setView(0L);
            expected.updateView();                      //2. when: 예상값 expected에 updateView를 통해 조회수 증가를 시도한다
            assertEquals(expected.getView(), 1); //3. then: 예상하는 조회수(1)인지 확인
        }
    }
}