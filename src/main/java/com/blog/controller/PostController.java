package com.blog.controller;

import com.blog.dto.*;
import com.blog.dto.PostDtos.*;
import com.blog.dto.CommentDtos.*;
import com.blog.service.BlogServices.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService posts;
    private final CommentService comments;

    @PostMapping
    @PreAuthorize("hasAnyRole('AUTHOR','ADMIN')")
    public ResponseEntity<?> create(
            @Valid @RequestBody PostRequest r,
            Authentication a) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(posts.create(r, a.getName()));
    }

    @GetMapping
    public PageResponse all(
            @RequestParam(required = false) Long categoryId,
            @PageableDefault(
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable p) {

        return posts.findAll(categoryId, p);
    }

    @GetMapping("/search")
    public PageResponse search(
            @RequestParam String keyword,
            @PageableDefault(size = 10) Pageable p) {

        return posts.search(keyword, p);
    }

    @GetMapping("/{id}")
    public PostResponse one(@PathVariable Long id) {
        return posts.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('AUTHOR','ADMIN')")
    public PostResponse update(
            @PathVariable Long id,
            @Valid @RequestBody PostRequest r,
            Authentication a) {

        return posts.update(id, r, a.getName());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('AUTHOR','ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id,
            Authentication a) {

        posts.delete(id, a.getName());
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<?> comment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequest r,
            Authentication a) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(comments.create(postId, r, a.getName()));
    }

    @GetMapping("/{postId}/comments")
    public List<?> comments(@PathVariable Long postId) {
        return comments.findByPost(postId);
    }
}
