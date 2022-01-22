package booksys.service;

import java.util.List;

import booksys.domain.TableDTO;

public interface TableService {
	public List<TableDTO> TableList();					// 테이블 리스트 가져옴
	public TableDTO TableFindOneByNumber(int number);	// number가 일치하는 테이블 정보 가져옴
	public TableDTO TableFindOneByOid(int oid);			// oid가 일치하는 테이블 정보 가져옴
	public void TableSave(TableDTO table);				// 테이블 정보 저장
	public void TableUpdate(int number, int places);	// 테이블 정보 업데이트
	public void TableDeleteByNumber(int number);		// number가 일치하는 테이블 정보 삭제
}
