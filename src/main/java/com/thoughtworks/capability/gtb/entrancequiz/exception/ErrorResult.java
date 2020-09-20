package com.thoughtworks.capability.gtb.entrancequiz.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResult {
    private Integer status;
    private String error;
    private String message;
    private Instant timestamp;
}
