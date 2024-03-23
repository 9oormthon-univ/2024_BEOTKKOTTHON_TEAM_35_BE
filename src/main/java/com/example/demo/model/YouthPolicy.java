package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class YouthPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bizId; // 정책 ID
    private String polyBizSecd; // 정책일련번호
    private String polyBizTy; // 기관 및 지자체 구분
    private String polyBizSjnm; // 정책명
    private String polyItcnCn; // 정책소개
    private String sporCn; // 지원내용
    private String sporScvl; // 지원규모
    private String bizPrdCn; // 사업운영기간내용
    private String prdRpttSecd; // 사업신청기간반복구분코드
    private String rqutPrdCn; // 사업신청기간내용
    private String ageInfo; // 연령 정보
    private String majrRqisCn; // 전공요건내용
    private String empmSttsCn; // 취업상태내용
    private String splzRlmRqisCn; // 특화분야내용
    private String accrRqisCn; // 학력요건내용
    private String prcpCn; // 거주지및소득조건내용
    private String aditRscn; // 추가단서사항내용
    private String prcpLmttTrgtCn; // 참여제한대상내용
    private String rqutProcCn; // 신청절차내용
    private String pstnPaprCn; // 제출서류내용
    private String jdgnPresCn; // 심사발표내용
    private String rqutUrla; // 신청사이트주소
    private String rfcSiteUrla1; // 참고사이트URL주소1
    private String rfcSiteUrla2; // 참고사이트URL주소2
    private String mngtMson; // 주관부처명
    private String mngtMrofCherCn; // 주관부처담당자이름
    private String cherCtpcCn; // 주관부처담당자연락처
    private String cnsgNmor; // 운영기관명
    private String tintCherCn; // 운영기관담당자이름
    private String tintCherCtpcCn; // 운영기관담당자연락처
    private String etct; // 기타사항
    private String polyRlmCd; // 정책분야코드

    private int viewCount; // 조회수
    private int bookmarkCount; // 북마크 수
}
