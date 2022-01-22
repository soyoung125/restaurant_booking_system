package booksys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import booksys.domain.OidDTO;
import booksys.persistency.OidMapper;

@Service
public class OidServiceImpl implements OidService {
	@Autowired
	private OidMapper oidMapper;
	
	@Override
	public OidDTO OidFind() {
		OidDTO oid=oidMapper.find();
		return oid;
	}

	@Override
	public void OidUpdate(OidDTO oid) {
		oidMapper.update(oid);
	}
}
