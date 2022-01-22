package booksys.persistency;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import booksys.domain.OidDTO;

@Mapper
public interface OidMapper {
	public OidDTO find();			// oid 정보 가져옴
	public void update(OidDTO oid);	// oid 정보 업데이트
}
