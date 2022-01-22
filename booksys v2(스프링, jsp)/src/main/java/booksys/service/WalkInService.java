package booksys.service;

import java.util.List;

import booksys.domain.WalkInDTO;

public interface WalkInService {
	public List<WalkInDTO> WalkInFindList();		// 워크인 리스트 가져옴
	public WalkInDTO WalkInFindOneByOid(int oid);	// oid가 일치하는 워크인 정보 가져옴
	public void WalkInSave(WalkInDTO walkIn);		// 워크인 정보 저장
	public void WalkInUpdate(WalkInDTO walkIn);		// 워크인 정보 업데이트
	public void WalkInDeleteByUserId(int oid);		// oid가 일치하는 워크인 정보 삭제
}
