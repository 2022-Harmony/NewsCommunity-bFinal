package com.teamharmony.newscommunity.supports.service;

import com.teamharmony.newscommunity.supports.dto.SupportRequestDto;
import com.teamharmony.newscommunity.supports.dto.SupportRequestUpdateDto;
import com.teamharmony.newscommunity.supports.dto.SupportResponseDto;
import com.teamharmony.newscommunity.supports.entity.Support;
import com.teamharmony.newscommunity.supports.repository.SupportRepository;
import com.teamharmony.newscommunity.users.entity.User;
import com.teamharmony.newscommunity.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupportService {
    private final SupportRepository supportRepository;
    private final UserRepository userRepository;

    /**
     * 게시글 생성: 게시글 생성에 필요한 정보인 requestDto를 username과 연관시켜 db에 등록
     *
     * @param 		requestDto 게시글 제목, 게시글 내용, 작성자 email주소
     * @param 		username 유저이름(User 테이블에 등록되어있는 정보)
     * @return 		Support 객체
     */
    @Transactional
    public Support generateSupport(SupportRequestDto requestDto, String username){
        Support support = new Support(requestDto, username);
        User user = userRepository.findByUsername(username);
        user.addSupports(support);
        supportRepository.save(support);
        return support;
    }

    /**
     * 게시글 전체 조회: supportRepository에서 최신 생성일이 맨 위에 오는 순서로 게시글 리스트 획득
     * @return 	Support 객체 리스트
     */
    public List<SupportResponseDto> getSupportsList() {
        List<Support> supportList = supportRepository.findAllByOrderByCreatedAtDesc();
        List<SupportResponseDto> resultList = new LinkedList<>();
        for (Support supportItems : supportList) {
            SupportResponseDto supportResponseDto = SupportResponseDto.builder()
                    .username(supportItems.getUsername())
                    .post_title(supportItems.getPost_title())
                    .post_content(supportItems.getPost_content())
                    .created_at(supportItems.getCreatedAt())
                    .modified_at(supportItems.getModifiedAt())
                    .id(supportItems.getSupport_id())
                    .build();
            resultList.add(supportResponseDto);
        }
        return resultList;
    }

    /**
     * 유저별 게시글 조회: supportRepository에서 username에 해당하는 게시글 리스트를 최신 생성일이 맨 위에 오는 순서로 획득
     * @param username 유저이름(User 테이블에 등록되어있는 정보)
     * @return Support 객체 리스트
     */
    public List<SupportResponseDto> getMySupportList(String username) {
        List<Support> supportList = supportRepository.findAllByUsernameOrderByCreatedAtDesc(username);
        List<SupportResponseDto> resultList = new LinkedList<>();
        for (Support supportItems : supportList) {
            SupportResponseDto supportResponseDto = SupportResponseDto.builder()
                    .username(supportItems.getUsername())
                    .post_title(supportItems.getPost_title())
                    .post_content(supportItems.getPost_content())
                    .created_at(supportItems.getCreatedAt())
                    .modified_at(supportItems.getModifiedAt())
                    .id(supportItems.getSupport_id())
                    .build();
            resultList.add(supportResponseDto);
        }
        return resultList;
    }

    /**
     * 게시글 삭제: parameter로 주어지는 contentId를 찾아서 삭제
     * @param contentId 삭제하려는 support객체의 contentId
     * @param username 로그인한 사람 이름(unigue)
     * @return contentId 제하려는 support객체의 contentId
     */
    public Long removeContent(Long contentId, String username) {
        Support supportObject = supportRepository.findById(contentId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
        Long supportsUserID = supportObject.getUser().getId(); //현재 게시글에서 userID정보획득
        User currentUser = userRepository.findByUsername(username); // 현재 로그인한 사람 이름(unigue)으로 user정보 획득
        Long loginUserId = currentUser.getId();// 로그인한 사용자ID 정보(Long) 획득

        if (supportsUserID == loginUserId){ //글 쓴 사람의 Id번호와 지금 로그인한 사람의 ID 번호 동일
            supportRepository.deleteById(contentId);
        }
        return contentId;
    }

    /**
     * 게시글 수정: parameter로 주어지는 contentId를 찾아서 삭제
     * @param support_id 수정하려는 support객체의 Id
     * @param requestUpdateDto 수정해서 등록하려는 게시글 내용
     * @param username 유저정보
     * @return support_id 수정하려는 support객체의 Id
     */
    @Transactional
    public Long updateSuppport(Long support_id, SupportRequestUpdateDto requestUpdateDto, String username) {
        Support supportObject = supportRepository.findById(support_id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );

        Long supportsUserID = supportObject.getUser().getId(); //현재 게시글에서 userID정보획득
        User currentUser = userRepository.findByUsername(username); // 현재 로그인한 사람 이름(unigue)으로 user정보 획득
        Long loginUserId = currentUser.getId();// 로그인한 사용자ID 정보(Long) 획득

        if (supportsUserID == loginUserId){ //글 쓴 사람의 Id번호와 지금 로그인한 사람의 ID 번호 동일
            supportObject.setPost_content(requestUpdateDto.getPost_content());
            supportObject.update(requestUpdateDto);
        }
        return support_id;
    }
}