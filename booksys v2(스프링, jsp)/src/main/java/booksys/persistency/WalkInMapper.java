package booksys.persistency;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import booksys.domain.WalkInDTO;

@Mapper
public interface WalkInMapper {
	public List<WalkInDTO> findList();		// 워크인 리스트 가져옴
	public WalkInDTO findOneByOid(int oid);	// oid가 일치하는 워크인 정보 가져옴
	public void save(WalkInDTO walkIn);		// 워크인 정보 저장
	public void update(WalkInDTO walkIn);	// 워크인 정보 업데이트
	public void deleteByOid(int oid);		// oid가 일치하는 워크인 정보 삭제
}
