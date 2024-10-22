package com.sparta.newsfeedproject.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDeleteRequestDto {
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "최소 8글자 최대 20글자로 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+={}:;\"'<>,.?/\\[\\]\\\\-])[A-Za-z\\d~!@#$%^&*()+|=]+$", message = "영문+숫자+특수문자가 1글자 포함되어야합니다.")
    private String password;
}
