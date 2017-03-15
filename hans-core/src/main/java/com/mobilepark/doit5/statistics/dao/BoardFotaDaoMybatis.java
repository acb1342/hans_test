package com.mobilepark.doit5.statistics.dao;

import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.board.model.BoardFota;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardFotaDaoMybatis{

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public List<BoardFota> selectSearchList(BoardFota board) {
		return sqlSessionTemplate.selectList("boardFota.selectSearchList", board);
	}

	public int selectSearchCount(BoardFota board) {
		Object obj = sqlSessionTemplate.selectOne("boardFota.selectSearchCount", board);
		if (obj == null) return 0;
		return (Integer) obj;
	}

	public void create(BoardFota boardFota) {
		sqlSessionTemplate.insert("boardFota.create", boardFota);
	}

	public int delete(Map<String, Object> param) {
		return sqlSessionTemplate.delete("boardFota.delete", param);
	}

	public void update(BoardFota boardFota) {
		sqlSessionTemplate.insert("boardFota.update", boardFota);
	}

	public BoardFota get(BoardFota boardFota) {
		return sqlSessionTemplate.selectOne("boardFota.get", boardFota);
	}


}
