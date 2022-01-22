package booksys.persistency;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import booksys.domain.TableDTO;


@Mapper
public interface TableMapper {
	public List<TableDTO> findList();				// 테이블 리스트 가져옴
	public TableDTO findOneByNumber(int number);	// number가 일치하는 테이블 정보 가져옴
	public TableDTO findOneByOid(int oid);			// oid가 일치하는 테이블 정보 가져옴
	public void save(TableDTO table);				// 테이블 정보 저장
	public void update(int number, int places);		// 테이블 정보 업데이트
	public void deleteByNumber(int number);			// number가 일치하는 테이블 정보 삭제
}