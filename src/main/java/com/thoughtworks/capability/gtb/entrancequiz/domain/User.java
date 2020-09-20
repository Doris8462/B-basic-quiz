package com.thoughtworks.capability.gtb.entrancequiz.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Long id;
    @NotNull(message = "姓名不能为空")
    @Size(min = 1, max = 128, message = "姓名长度为1到128位")
    private String name;

    @NotNull(message = "年龄不能为空")
    @Min(value = 16, message = "年纪不能小于16岁")
    private Long age;

    @NotNull(message = "头像图片链接地址不能为空")
    @Size(min = 8, max = 512, message = "长度为8到512位")
    private String avatar;
    @Size(min = 0, max = 1024, message = "长度为0到1024位")
    private String description;
}