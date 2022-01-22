package booksys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import booksys.domain.TableDTO;
import booksys.persistency.TableMapper;

@Service
public class TableServiceImpl implements TableService {
	@Autowired
	private TableMapper tableMapper;

	@Override
	public List<TableDTO> TableList() {
		List<TableDTO> table=tableMapper.findList();
		return table;
	}

	@Override
	public TableDTO TableFindOneByNumber(int number) {
		TableDTO table=tableMapper.findOneByNumber(number);
		return table;
	}

	@Override
	public TableDTO TableFindOneByOid(int oid) {
		TableDTO table=tableMapper.findOneByOid(oid);
		return table;
	}

	@Override
	public void TableSave(TableDTO table) {
		tableMapper.save(table);
	}

	@Override
	public void TableUpdate(int number, int places) {
		tableMapper.update(number, places);
	}

	@Override
	public void TableDeleteByNumber(int number) {
		tableMapper.deleteByNumber(number);
	}
}
