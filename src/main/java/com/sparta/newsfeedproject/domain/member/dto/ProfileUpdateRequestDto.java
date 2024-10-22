package com.sparta.newsfeedproject.domain.member.dto;

import com.sparta.newsfeedproject.domain.validator.Country;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileUpdateRequestDto {

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    @Size(min = 8, max = 20, message = "최소 8글자 최대 20글자로 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+={}:;\"'<>,.?/\\[\\]\\\\-])[A-Za-z\\d~!@#$%^&*()+|=]+$", message = "영문+숫자+특수문자가 1글자 포함되어야합니다.")
    private String password;
    
    @NotBlank(message = "거주 국가를 입력해주세요.")
    @Country(message = "존재하지 않는 국가 이거나 국가명을 영문으로 입력해주세요.")
    private String country;
}
