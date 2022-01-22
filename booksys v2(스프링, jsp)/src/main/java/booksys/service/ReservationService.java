package booksys.service;

import java.util.List;

import booksys.domain.ReservationDTO;

public interface ReservationService {
	public List<ReservationDTO> ReservationFindList();			// 예약 리스트 가져옴
	public ReservationDTO ReservaionFindOneByOid(int oid);		// oid가 일치하는 예약 정보 가져옴
	public void ReservationSave(ReservationDTO reservation);	// 예약 정보 저장
	public void ReservaionUpdate(ReservationDTO reservation);	// 예약 정보 업데이트
	public void ReservationDeleteByUserId(int oid);				// oid가 일치하는 예약 정보 삭제
}
