package com.kh.team4.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

//화면에서 전달되는 목록 관련된 데이터 DTO를 PageRequestDTO라는 이름으로 생성
//목록 페이지를 요청할 때 사용하는 데이터, 재사용 위해 생성
// 파라미터를 DTO로 선언하고 나중에 재사용
@Builder
@AllArgsConstructor
@Data
//재연
public class PageRequestDTO {
    private int page;
    private int size;

    //검색 처리 위함
    private String type;
    private String keyword;

    public PageRequestDTO() {
        //페이지 번호 등은 기본값을 가지는 것이 좋기 때문 1, 10 지정
        this.page = 1;
        this.size = 10;
    }

    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page -1, size, sort);
    }
}
