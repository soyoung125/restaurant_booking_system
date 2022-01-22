package booksys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import booksys.domain.CustomerDTO;
import booksys.persistency.CustomerMapper;

public interface CustomerService {
	public List<CustomerDTO> UserFindList();							// 고객 리스트 가져옴
	public CustomerDTO UserFindOneByUserId(String userId);				// userId가 일치하는 고객 정보 가져옴
	public CustomerDTO UserFindOneByOid(int oid);						// oid가 일치하는 고객 정보 가져옴
	public void CustomerSave(CustomerDTO customer);						// 고객 정보 저장
	public void CustomerUpdate(CustomerDTO customer);					// 고객 정보 업데이트
	public void CustomerReservationCountUpdate(CustomerDTO customer);	// 고객 reservationCount 업데이트
	public void UserDeleteByUserId(String userId);						// userId가 일치하는 고객 정보 삭제
}
