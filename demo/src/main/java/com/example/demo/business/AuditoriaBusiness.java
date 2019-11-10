package com.example.demo.business;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Auditoria;
import com.example.demo.persistence.AuditoriaRepository;

@Service
public class AuditoriaBusiness implements IAuditoriaBusiness {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AuditoriaRepository auditoriaRepository;
	
	@Override
	public Auditoria load(int id) throws BusinessException, NotFoundException {
		Optional<Auditoria> op = null;
		try {
			op = auditoriaRepository.findById(id);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		if (!op.isPresent())
			throw new NotFoundException("No se encuentra la auditoria con id=" + id);
		return op.get();
	}

	@Override
	public Auditoria save(Auditoria auditoria) throws BusinessException {
		try {
			return auditoriaRepository.save(auditoria);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void delete(int id) throws BusinessException, NotFoundException {
		Optional<Auditoria> op = null;

		try {
			op = auditoriaRepository.findById(id);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

		if (!op.isPresent())
			throw new NotFoundException("No se encuentra la auditoria con id=" + id);
		try {
			auditoriaRepository.deleteById(id);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

	@Override
	public List<Auditoria> list() throws BusinessException {
		try {
			return auditoriaRepository.findAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException(e);
		}
	}
}
