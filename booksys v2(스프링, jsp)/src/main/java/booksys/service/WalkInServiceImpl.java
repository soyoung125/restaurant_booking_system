package booksys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import booksys.domain.WalkInDTO;
import booksys.persistency.WalkInMapper;

@Service
public class WalkInServiceImpl implements WalkInService {
	@Autowired
	private WalkInMapper walkInMapper;

	@Override
	public List<WalkInDTO> WalkInFindList() {
		List<WalkInDTO> walkIn=walkInMapper.findList();
		return walkIn;
	}

	@Override
	public WalkInDTO WalkInFindOneByOid(int oid) {
		WalkInDTO walkIn=walkInMapper.findOneByOid(oid);
		return walkIn;
	}

	@Override
	public void WalkInSave(WalkInDTO walkIn) {
		walkInMapper.save(walkIn);
	}

	@Override
	public void WalkInUpdate(WalkInDTO walkIn) {
		walkInMapper.update(walkIn);
	}

	@Override
	public void WalkInDeleteByUserId(int oid) {
		walkInMapper.deleteByOid(oid);
	}

}
