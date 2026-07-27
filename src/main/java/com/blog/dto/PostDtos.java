package com.blog.dto;
import jakarta.validation.constraints.*; import java.time.Instant;
public final class PostDtos { private PostDtos(){} public record PostRequest(@NotBlank @Size(max=180) String title,@NotBlank @Size(min=20) String content,@Size(max=500) String imageUrl,boolean published,Long categoryId){} public record PostResponse(Long id,String title,String slug,String content,String imageUrl,boolean published,Instant createdAt,Instant updatedAt,AuthDtos.UserResponse author,CategoryDtos.CategoryResponse category){} }
