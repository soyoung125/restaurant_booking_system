package booksys.service;

import booksys.domain.OidDTO;

public interface OidService {
	public OidDTO OidFind();			// oid 정보 가져옴
	public void OidUpdate(OidDTO oid);	// oid 정보 업데이트
}
