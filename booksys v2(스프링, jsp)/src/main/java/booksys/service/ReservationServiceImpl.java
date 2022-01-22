package booksys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import booksys.domain.ReservationDTO;
import booksys.persistency.ReservationMapper;

@Service
public class ReservationServiceImpl implements ReservationService {
	@Autowired
	private ReservationMapper reservationMapper;

	@Override
	public List<ReservationDTO> ReservationFindList() {
		List<ReservationDTO> reservation=reservationMapper.findList();
		return reservation;
	}

	@Override
	public ReservationDTO ReservaionFindOneByOid(int oid) {
		ReservationDTO reservation=reservationMapper.findOneByOid(oid);
		return reservation;
	}

	@Override
	public void ReservationSave(ReservationDTO reservation) {
		reservationMapper.save(reservation);
	}

	@Override
	public void ReservaionUpdate(ReservationDTO reservation) {
		reservationMapper.update(reservation);
	}

	@Override
	public void ReservationDeleteByUserId(int oid) {
		reservationMapper.deleteByOid(oid);
	}

}
