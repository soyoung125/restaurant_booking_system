package booksys.persistency;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import booksys.domain.ReservationDTO;

@Mapper
public interface ReservationMapper {
	public List<ReservationDTO> findList();			// 예약 리스트 가져옴
	public ReservationDTO findOneByOid(int oid);	// oid가 일치하는 예약 정보 가져옴
	public void save(ReservationDTO reservation);	// 예약 정보 저장
	public void update(ReservationDTO reservation);	// 예약 정보 업데이트
	public void deleteByOid(int oid);				// oid가 일치하는 예약 정보 삭제
}
