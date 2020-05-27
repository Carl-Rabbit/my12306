package com.dbpp.my12306.service;

import com.dbpp.my12306.entity.Admin;
import com.dbpp.my12306.mapper.AdminMapper;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

	private final AdminMapper adminMapper;

	public AdminService(AdminMapper adminMapper) {
		this.adminMapper = adminMapper;
	}

	public Admin getSelective(Integer adminId, String adminName){
		return adminMapper.select(adminId, adminName);
	}

	public Integer add(Admin record) {
		return adminMapper.insert(record);
	}

	public Integer delete(Integer adminId) {
		return adminMapper.deleteByPrimaryKey(adminId);
	}
}
