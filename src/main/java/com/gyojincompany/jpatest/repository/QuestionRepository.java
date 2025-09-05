package com.gyojincompany.jpatest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
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
	public List<Questiontbl> findAllByQtitle(String qtitle);
	
	//제목에 특정문자가 존재하면 조회하기->like->최근 글이 위로 오도록 정렬
	public List<Questiontbl> findAllByQtitleLikeOrderByQdateDesc(String keyword);
	
	//SQL문 직접 쓰기
	@Query("SELECT q FROM Questiontbl q WHERE q.qnum= :qnum")
	public Questiontbl findQuestionByQnum(@Param("qnum") Long qnum);
	
}
