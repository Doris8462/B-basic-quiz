package com.thoughtworks.capability.gtb.entrancequiz.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Education {
    @NotNull(message = "教育经历描述不能为空")
    @Size(min = 1, max = 4096, message = "长度为1到4096位")
    private String description;
    @NotNull(message = "年份不能为空")
    private Long year;

    @NotNull(message = "教育经历标题不能为空")
    @Size(min = 1, max = 256, message = "长度为1到256位")
    private String title;
    private Long userId;

}