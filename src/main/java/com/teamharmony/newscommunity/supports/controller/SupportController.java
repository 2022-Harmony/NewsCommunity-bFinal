package com.teamharmony.newscommunity.supports.controller;

import com.teamharmony.newscommunity.supports.dto.SupportRequestDto;
import com.teamharmony.newscommunity.supports.dto.SupportRequestUpdateDto;
import com.teamharmony.newscommunity.supports.dto.SupportResponseDto;
import com.teamharmony.newscommunity.supports.entity.Support;
import com.teamharmony.newscommunity.supports.repository.SupportRepository;
import com.teamharmony.newscommunity.supports.service.SupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/")
public class SupportController {
    private final SupportService supportService;

    //bean이면 서비스에서 주입이 가능한데, bean이 아니면 불가해서 controllerd에서 서비스로 넘겨줘야 한다.
    //생성
    @PostMapping("/supports")
    @ResponseBody
    public Support createSupport(@RequestBody SupportRequestDto requestedDto, @AuthenticationPrincipal UserDetails user) {
        String username = user.getUsername();
        return supportService.generateSupport(requestedDto, username);
    }

    //조회
    @GetMapping("/supports")
    @ResponseBody
    public List<SupportResponseDto> readSupport() {
        return supportService.getSupportsList();
    }

    //내가 작성한 글만 조회하기
    @GetMapping("/supports/mine")
    @ResponseBody
    public List<SupportResponseDto> getMySupports(@AuthenticationPrincipal UserDetails user) {
        String username = user.getUsername();
        return supportService.getSupportsListWrittenByMe(username);
    }
    //수정
    @PutMapping("/supports/{content_id}")
    @ResponseBody
    public String modifySupportContent(@PathVariable Long content_id, @RequestBody SupportRequestUpdateDto requestedDto, @AuthenticationPrincipal UserDetails user) {
        return supportService.update(content_id, requestedDto, user);
    }

    //삭제
    @DeleteMapping("/supports/{content_id}")
    @ResponseBody
    public String deleteSupport(@PathVariable Long content_id, @AuthenticationPrincipal UserDetails user) {
        return supportService.removeContent(content_id, user);
    }
}