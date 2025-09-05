package com.gyojincompany.jpatest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.gyojincompany.jpatest.entity.Questiontbl;

public interface QuestionRepository extends JpaRepository<Questiontbl, Long> { //dao 클래스의 역할
	
	@Transactional
	public void deleteAllByQtitle(String qtitle); //질문 제목으로 삭제
	
	//최근 질문글이 가장 위로 오도록 정렬하여 모든 레코드를 반환
	public List<Questiontbl> findAllByOrderByQdateDesc();
	
	//두개의 조건이 일치하는 질문글 조회하기
	public Questiontbl findByQnumAndQtitle(Long qnum, String qtitle);
	
	//제목이 정확히 일치하는 질문글 조회하기
	public List<Questiontbl> findByQtitle(String qtitle);//-글4개 검색->List 타입으로 받아야함
	//public Questiontbl findByQtitle(String qtitle);
	
	//제목에 특정문자가 존재하면 조회하기->like->최근 글이 위로 오도록 정렬
	public List<Questiontbl> findAllByQtitleLikeOrderByQdateDesc(String keyword);
	
	//SQL문 직접 쓰기->JPA SQL문(JPQL)
	@Query("SELECT q FROM Questiontbl q WHERE q.qnum= :qnum")
	public Questiontbl findQuestionByQnum(@Param("qnum") Long qnum);
	
	//질문 제목에 특정 문자가 들어 있는 질문글 조회 
	@Query("SELECT q FROM Questiontbl q WHERE q.qtitle LIKE %:qtitle%")
	public Questiontbl findQuestionByQtitle(@Param("qtitle") String qtitle);
	
	//질문 글 번호가 특정 값보다 큰 질문 글만 조회
	@Query("SELECT q FROM Questiontbl q WHERE q.qnum >= :number")
	public Questiontbl findQuestionByQnumber(@Param("number") Long number);
	
	//Native SQL문(오리지널 SQL문) 쓰기
	@Query(value = "SELECT * FROM jpaquestiontbl WHERE qnum= :qnum", nativeQuery = true)
	public Questiontbl findQuestionNavtiveByQnum(@Param("qnum") Long qnum);
	
	//기타 JPA 문법
	public boolean existsByQnum(Long qnum); //qnum이 존재하는 번호면 true 반환
	
	//질문글 번호가 특정 값 보다 큰 질문들만 조회
	public List<Questiontbl> findByQnumGreaterThanEqual(Long qnum);
	
	//질문 내용 업데이트
	@Modifying
	@Transactional
	@Query("UPDATE Questiontbl q SET q.qcontent = :qcontent WHERE q.qnum = :qnum")
	public int updateQcontentByQnum(@Param("qcontent") String qcontent, @Param("qnum") Long qnum);
	
	@Modifying
	@Query(value = "UPDATE Questiontbl SET qcontent = :qcontent WHERE qnum= :qnum", nativeQuery = true)
	public int updateNativeQcontentByQnum(@Param("qcontent") String qcontent, @Param("qnum") Long qnum);
	
}
