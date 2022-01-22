package booksys.persistency;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import booksys.domain.CustomerDTO;

@Mapper
public interface CustomerMapper {
	public List<CustomerDTO> findList();						// 고객 리스트 가져옴
	public CustomerDTO findOneByUserId(String userId);			// userId가 일치하는 고객 정보 가져옴
	public CustomerDTO findOneByOid(int oid);					// oid가 일치하는 고객 정보 가져옴
	public void save(CustomerDTO customer);						// 고객 정보 저장
	public void update(CustomerDTO customer);					// 고객 정보 업데이트
	public void updateReservationCount(CustomerDTO customer);	// 고객 reservationCount 업데이트
	public void deleteByUserId(String userId);					// userId가 일치하는 고객 정보 삭제
}
