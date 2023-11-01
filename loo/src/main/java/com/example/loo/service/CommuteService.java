package com.example.loo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.loo.model.commute.Commute;
import com.example.loo.repository.CommuteMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommuteService {
	
	private final CommuteMapper commuteMapper;
	public void attendanceCommute(Commute commute) {
		
		commuteMapper.attendanceCommute(commute);
	}
	
	public void leaveCommute(Long commute_id) {
		Commute findCommute = commuteMapper.findCommute(commute_id);
		commuteMapper.leaveCommute(findCommute);
	}
	
	public Commute findCommute(Long commute_id) {
		Commute findCommute = commuteMapper.findCommute(commute_id);
		
		return findCommute;
	}
	
	public void updateAdminCommute(Commute commute) {
		commuteMapper.updateAdminCommute(commute);
	}
	
	public List<Commute> findAllCommutes(String member_mail) {
		List<Commute> findAllCommutes = commuteMapper.findAllCommutes(member_mail);
		
		return findAllCommutes;
	}

}
