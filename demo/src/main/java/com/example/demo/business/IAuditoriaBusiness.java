package com.example.demo.business;

import java.util.List;

import com.example.demo.model.Auditoria;

public interface IAuditoriaBusiness {
	public Auditoria load(int id) throws BusinessException, NotFoundException;

	public Auditoria save(Auditoria auditoria) throws BusinessException;

	public void delete(int id) throws BusinessException, NotFoundException;

	public List<Auditoria> list() throws BusinessException;

}
