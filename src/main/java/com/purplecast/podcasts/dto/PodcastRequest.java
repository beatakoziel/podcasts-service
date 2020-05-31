package com.purplecast.podcasts.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PodcastRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String category;
    @NotBlank
    private String imageUrl;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Float length;
    @NotBlank
    private String fileName;
    private boolean blocked;
}
