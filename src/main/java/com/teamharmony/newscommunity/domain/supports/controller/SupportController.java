package com.teamharmony.newscommunity.domain.supports.controller;

import com.teamharmony.newscommunity.common.annotation.CurrentUser;
import com.teamharmony.newscommunity.domain.supports.dto.SupportRequestDto;
import com.teamharmony.newscommunity.domain.supports.dto.SupportRequestUpdateDto;
import com.teamharmony.newscommunity.domain.supports.dto.SupportResponseDto;
import com.teamharmony.newscommunity.domain.supports.entity.Support;
import com.teamharmony.newscommunity.domain.supports.service.SupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class SupportController {
    private final SupportService supportService;

    /**
     * 게시글 생성 : requestBody로 넘어오는 게시글 제목과 내용, 작성자의 Email주소를 받아서 저장. Email주소는 유효성을 검사.
     * @param requestDto
     * @param username
     * @return support 객체
     * @see SupportService#generateSupport
     */
    @PostMapping("/user/supports")
    public Support createSupport(@RequestBody @Valid SupportRequestDto requestDto, @CurrentUser String username) {
        return supportService.generateSupport(requestDto, username);
    }

    /**
     * 전체 Support 게시글 조회 : 전체 게시글 불러옴
     * @return support 객체 리스트
     * @see SupportService#getSupportsList
     */
    @GetMapping("/supports")
    public ResponseEntity<List<SupportResponseDto>> readSupport() {
        List<SupportResponseDto> supportList = supportService.getSupportsList();
        return ResponseEntity.ok().body(supportList);
    }

    /**
     * 유저별 Support 게시글 조회 : 각 유저가 생성한 게시글만 불러옴
     * @param username
     * @return support 객체
     * @see SupportService#getMySupportList
     */
    @GetMapping("/user/supports/mine")
    public ResponseEntity<List<SupportResponseDto>> getMySupportList(@CurrentUser String username) {
        List<SupportResponseDto> supportList = supportService.getMySupportList(username);
        return ResponseEntity.ok().body(supportList);
    }
    /**
     * 게시글 수정 : requestBody인 supportRequestUpdateDto로 넘어오는 게시글 내용으로 content_id에 해당하는 게시글 업데이트
     * @param content_id
     * @param supportRequestUpdateDto
     * @param username
     * @return 수정된 댓글의 contentId
     * @see SupportService#updateSuppport
     */
    @PutMapping("/user/supports/{content_id}")
    public ResponseEntity<Long> modifySupportContent(@PathVariable Long content_id, @RequestBody SupportRequestUpdateDto supportRequestUpdateDto, @CurrentUser String username) {
        Long modifyContentId = supportService.updateSuppport(content_id, supportRequestUpdateDto, username);
        return ResponseEntity.ok().body(modifyContentId);
    }
    /**
     * 게시글 삭제 : content_id에 해당하는 게시글 삭제
     * @param content_id
     * @param username
     * @return 삭제된 댓글의 content_id
     * @see SupportService#removeContent
     */
    @DeleteMapping("/user/supports/{content_id}")
    public ResponseEntity<Long> deleteSupport(@PathVariable Long content_id, @CurrentUser String username) {
        Long deleteContentId = supportService.removeContent(content_id, username);
        return ResponseEntity.ok().body(deleteContentId);
    }
}