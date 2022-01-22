package booksys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import booksys.domain.CustomerDTO;
import booksys.domain.OidDTO;
import booksys.domain.ReservationDTO;
import booksys.domain.TableDTO;
import booksys.domain.WalkInDTO;
import booksys.service.CustomerService;
import booksys.service.OidService;
import booksys.service.ReservationService;
import booksys.service.TableService;
import booksys.service.WalkInService;

@Controller
public class DatabaseController {
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private OidService oidService;
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private TableService tableService;
	
	@Autowired
	private WalkInService walkInService;
	
	@RequestMapping(value="/api/customer/list")				// 고객 리스트 가져옴
	@ResponseBody
	public List<CustomerDTO> UserFindList() {
		return customerService.UserFindList();
	}
	
	@RequestMapping(value="/api/customer/find/{userId}")	// userId가 일치하는 고객 정보 가져옴
	@ResponseBody
	public CustomerDTO UserFindOneByUserId(@PathVariable(value="userId") String userId) {
		CustomerDTO customer=customerService.UserFindOneByUserId(userId);
		return customer;
	}
	
	@RequestMapping(value="/api/customer/find2/{oid}")		// oid가 일치하는 고객 정보 가져옴
	@ResponseBody
	public CustomerDTO UserFindOneByOid(@PathVariable(value="oid") int oid) {
		CustomerDTO customer=customerService.UserFindOneByOid(oid);
		return customer;
	}

	@RequestMapping(value="/api/customer/save")				// 고객 정보 저장
	@ResponseBody
	public void CustomerSave(@RequestBody CustomerDTO customer) {
		customerService.CustomerSave(customer);
	}
	
	@RequestMapping(value="api/customer/update")			// 고객 정보 업데이트
	@ResponseBody
	public void CustomerUpdate(@RequestBody CustomerDTO customer) {
		customerService.CustomerUpdate(customer);
	}
	
	@RequestMapping(value="api/customer/reservationCount/update")	// 고객 reservationCount 업데이트
	@ResponseBody
	public void CustomerReservationCountUpdate(@RequestBody CustomerDTO customer) {
		customerService.CustomerReservationCountUpdate(customer);
	}
	
	@RequestMapping(value="api/customer/delete/{userId}")	// userId가 일치하는 고객 정보 삭제
	@ResponseBody
	public void UserDeleteByUserId(@PathVariable(value="userId") String userId) {
		customerService.UserDeleteByUserId(userId);
	}		
	
	@RequestMapping(value="/api/oid/find")	// oid 정보 가져옴
	@ResponseBody
	public OidDTO OidFind() {
		OidDTO oid=oidService.OidFind();
		return oid;
	}
	
	@RequestMapping(value="api/oid/update")	// oid 정보 업데이트
	@ResponseBody
	public void OidUpdate(@RequestBody OidDTO oid) {
		oidService.OidUpdate(oid);
	}
	
	@RequestMapping(value="/api/reservation/list")			// 예약 리스트 가져옴
	@ResponseBody
	public List<ReservationDTO> ReservationFindList() {
		List<ReservationDTO> reservation=reservationService.ReservationFindList();
		return reservation;
	}
	
	@RequestMapping(value="/api/reservation/find/{oid}")	// oid가 일치하는 예약 정보 가져옴
	@ResponseBody
	public ReservationDTO ReservaionFindOneByOid(@PathVariable(value="oid") int oid) {
		ReservationDTO reservation=reservationService.ReservaionFindOneByOid(oid);
		return reservation;
	}
	
	@RequestMapping(value="/api/reservation/save")			// 예약 정보 저장
	@ResponseBody
	public void ReservationSave(@RequestBody ReservationDTO reservation) {
		reservationService.ReservationSave(reservation);
	}
	
	@RequestMapping(value="api/reservation/update")			// 예약 정보 업데이트
	@ResponseBody
	public void ReservaionUpdate(@RequestBody ReservationDTO reservation) {
		reservationService.ReservaionUpdate(reservation);
	}
	
	@RequestMapping(value="api/reservation/delete/{oid}")	// oid가 일치하는 예약 정보 삭제
	@ResponseBody
	public void ReservationDeleteByUserId(@PathVariable(value="oid") int oid) {
		reservationService.ReservationDeleteByUserId(oid);
	}
	
	@RequestMapping(value="/api/table/list")			// 테이블 리스트 가져옴
	@ResponseBody
	public List<TableDTO> TableList() {
		List<TableDTO> table=tableService.TableList();
		return table;
	}
	
	@RequestMapping(value="/api/table/find/{number}")	// number가 일치하는 테이블 정보 가져옴
	@ResponseBody
	public TableDTO TableFindOneByNumber(@PathVariable(value="number") int number) {
		TableDTO table=tableService.TableFindOneByNumber(number);
		return table;
	}
	
	@RequestMapping(value="/api/table/find2/{oid}")		// oid가 일치하는 테이블 정보 가져옴
	@ResponseBody
	public TableDTO TableFindOneByOid(@PathVariable(value="oid") int oid) {
		TableDTO table=tableService.TableFindOneByOid(oid);
		return table;
	}
	
	@RequestMapping(value="/api/table/save")			// 테이블 정보 저장
	@ResponseBody
	public void TableSave(@RequestBody TableDTO table) {
		tableService.TableSave(table);
	}
	
	@RequestMapping(value="/api/table/update/{number}/{places}")	// 테이블 정보 업데이트
	@ResponseBody
	public void TableUpdate(@PathVariable(value="number") int number, @PathVariable(value="places") int places) {
		tableService.TableUpdate(number, places);
	}
	
	@RequestMapping(value="/api/table/delete/{number}")	// number가 일치하는 테이블 정보 삭제
	@ResponseBody
	public void TableDeleteByNumber(@PathVariable(value="number") int number) {
		tableService.TableDeleteByNumber(number);
	}
	
	@RequestMapping(value="/api/walkin/list")		// 워크인 리스트 가져옴
	@ResponseBody
	public List<WalkInDTO> WalkInFindList() {
		List<WalkInDTO> walkIn=walkInService.WalkInFindList();
		return walkIn;
	}
	
	@RequestMapping(value="/api/walkin/find/{oid}")	// oid가 일치하는 워크인 정보 가져옴
	@ResponseBody
	public WalkInDTO WalkInFindOneByOid(@PathVariable(value="oid") int oid) {
		WalkInDTO walkIn=walkInService.WalkInFindOneByOid(oid);
		return walkIn;
	}
	
	@RequestMapping(value="/api/walkin/save")		// 워크인 정보 저장
	@ResponseBody
	public void WalkInSave(@RequestBody WalkInDTO walkIn) {
		walkInService.WalkInSave(walkIn);
	}
	
	@RequestMapping(value="api/walkin/update")		// 워크인 정보 업데이트
	@ResponseBody
	public void WalkInUpdate(@RequestBody WalkInDTO walkIn) {
		walkInService.WalkInUpdate(walkIn);
	}
	
	@RequestMapping(value="api/walkin/delete/{oid}")	// oid가 일치하는 워크인 정보 삭제
	@ResponseBody
	public void WalkInDeleteByUserId(@PathVariable(value="oid") int oid) {
		walkInService.WalkInDeleteByUserId(oid);
	}
}
