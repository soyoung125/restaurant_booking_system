package booksys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import booksys.domain.CustomerDTO;
import booksys.persistency.CustomerMapper;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerMapper customerMapper;

	@Override
	public List<CustomerDTO> UserFindList() {
		List<CustomerDTO> customer=customerMapper.findList();
		return customer;
	}

	@Override
	public CustomerDTO UserFindOneByUserId(String userId) {
		CustomerDTO customer=customerMapper.findOneByUserId(userId);
		return customer;
	}

	@Override
	public CustomerDTO UserFindOneByOid(int oid) {
		CustomerDTO customer=customerMapper.findOneByOid(oid);
		return customer;
	}

	@Override
	public void CustomerSave(CustomerDTO customer) {
		customer.setPassword(BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt()));	// 비밀번호는 암호화해서 저장
		customerMapper.save(customer);
	}

	@Override
	public void CustomerUpdate(CustomerDTO customer) {
		if(customer.getPassword()!=null) customer.setPassword(BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt()));	// 비밀번호는 암호화해서 저장
		customerMapper.update(customer);
	}

	@Override
	public void CustomerReservationCountUpdate(CustomerDTO customer) {
		customerMapper.updateReservationCount(customer);
	}

	@Override
	public void UserDeleteByUserId(String userId) {
		customerMapper.deleteByUserId(userId);
	}
}
