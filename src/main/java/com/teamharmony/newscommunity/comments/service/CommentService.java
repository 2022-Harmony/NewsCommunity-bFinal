package com.teamharmony.newscommunity.comments.service;

import com.teamharmony.newscommunity.comments.dto.CommentCreateRequestDto;
import com.teamharmony.newscommunity.comments.dto.CommentEditRequestDto;
import com.teamharmony.newscommunity.comments.dto.CommentResponseDto;
import com.teamharmony.newscommunity.comments.entity.Comment;
import com.teamharmony.newscommunity.comments.entity.Likes;
import com.teamharmony.newscommunity.comments.repository.CommentRepository;
import com.teamharmony.newscommunity.comments.repository.LikesRepository;
import com.teamharmony.newscommunity.exception.InvalidRequestException;
import com.teamharmony.newscommunity.users.dto.ProfileResponseDto;
import com.teamharmony.newscommunity.users.entity.User;
import com.teamharmony.newscommunity.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final LikesRepository likesRepository;

    @Transactional
    public void createComment(CommentCreateRequestDto commentCreateRequestDto, String username) {
        if (commentCreateRequestDto.getContent() == null || commentCreateRequestDto.getNewsId() == null) {
            throw new InvalidRequestException("댓글 내용 또는 뉴스아이디가 비어있습니다", ("댓글 내용 " + commentCreateRequestDto.getContent() + " 뉴스 아이디 " + commentCreateRequestDto.getNewsId()), "C401");
        }
        Comment comment = new Comment(commentCreateRequestDto);

        User user = getUser(username);
        user.addComment(comment);
    }

    /**
     * DB에서 newsId로 데이터를 모두 조회한 다음, 필요한 값들만 CommentResponseDto에 담는 함수
     * @param newsId
     * @return
     */
    public List<CommentResponseDto> findComments(String newsId, int page, int size, String currentUser) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> commentList = commentRepository.findAllByNewsId(newsId, pageable);


        if (commentList == null) {
            throw new InvalidRequestException("댓글을 불러올 수 없습니다", "뉴스 아이디가 댓글에 잘 저장됐는지 확인하세요", "C402");
        }
        return commentList.stream().map(comment -> CommentResponseDto.builder()
                        .commentId(comment.getCommentId())
                        .content(comment.getContent())
                        .modifiedAt(comment.getModifiedAt())
                        .createdAt(comment.getCreatedAt())
                        .profileResponseDto(new ProfileResponseDto(comment.getUser().getProfile()))
                        .like(likeCheck(comment.getCommentId(), currentUser))
                        .build())
                        .collect(Collectors.toList());
    }

    private Boolean likeCheck(Long commentId, String currentUser) {
        if(Objects.equals(currentUser, "=")) return false;
		    Likes likes = likesRepository.findByComment_CommentIdAndUser_Id(commentId, getUser(currentUser).getId());
        return likes != null;
    }

    public int getCommentCount(String newsId) {
        return commentRepository.countByNewsId(newsId);
    }

    @Transactional
    public void updateComment(Long id, CommentEditRequestDto commentEditRequestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new InvalidRequestException("해당 댓글은 이미 삭제되었기 때문에 수정할 수 없습니다", "삭제된 댓글 아이디 " + id, "C403")
        );
        comment.update(commentEditRequestDto);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public List<CommentResponseDto> getSortedCommentsDesc(String newsId) {
        List<Comment> commentList = commentRepository.findByNewsIdOrderByCreatedAtDesc(newsId);
        return commentList.stream().map(comment -> CommentResponseDto.builder()
                        .commentId(comment.getCommentId())
                        .content(comment.getContent())
                        .modifiedAt(comment.getModifiedAt())
                        .createdAt(comment.getCreatedAt())
                        .profileResponseDto(new ProfileResponseDto(comment.getUser().getProfile()))
                        .build())
                .collect(Collectors.toList());
    }

    public List<CommentResponseDto> getSortedCommentsAsc(String newsId) {
        List<Comment> commentList = commentRepository.findByNewsIdOrderByCreatedAtAsc(newsId);
        return commentList.stream().map(comment -> CommentResponseDto.builder()
                        .commentId(comment.getCommentId())
                        .content(comment.getContent())
                        .modifiedAt(comment.getModifiedAt())
                        .createdAt(comment.getCreatedAt())
                        .profileResponseDto(new ProfileResponseDto(comment.getUser().getProfile()))
                        .build())
                .collect(Collectors.toList());
    }

    public List<CommentResponseDto> getCommentsByUserId(String username, int page, int size, String currentUser) {
        User user = getUser(username);
        Long userId = user.getId();

        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> commentList = commentRepository.findByUser_Id(userId, pageable);
        return commentList.stream().map(comment -> CommentResponseDto.builder()
                        .commentId(comment.getCommentId())
                        .content(comment.getContent())
                        .modifiedAt(comment.getModifiedAt())
                        .createdAt(comment.getCreatedAt())
                        .profileResponseDto(new ProfileResponseDto(comment.getUser().getProfile()))
                        .like(likeCheck(comment.getCommentId(), currentUser))
                        .build())
                .collect(Collectors.toList());
    }
		
		private User getUser(String username) {
			return userRepository.findByUsername(username);
		}
}
