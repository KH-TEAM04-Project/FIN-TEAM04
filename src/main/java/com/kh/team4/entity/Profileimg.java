package com.kh.team4.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "profileimg")
@SequenceGenerator(
        name = "profileimg_seq_generator"  //시퀀스 제너레이터 이름
        , sequenceName = "profileimg_seq"  //시퀀스 이름
        , initialValue = 1  //시작값
        , allocationSize = 1  //메모리를 통해 할당할 범위 사이즈
)
public class Profileimg  extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profileimg_seq_generator")
    private Long fno;

    @Column
    private String originalFilename;

    @Column
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member_mno")
    private Member mno;

    public static Profileimg toFiles(Member mno, String originalFilename, String storedFileName) {

        Profileimg files = Profileimg.builder()
                .originalFilename(originalFilename)
                .storedFileName(storedFileName)
                .mno(mno)
                .build();
        return files;
    }

}
