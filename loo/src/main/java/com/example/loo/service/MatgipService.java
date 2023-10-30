package com.example.loo.service;

import org.springframework.stereotype.Service;

import com.example.loo.model.matgip.Matgip;
import com.example.loo.repository.MatgipMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MatgipService {
	
	private final MatgipMapper matgipMapper;
	
	public void saveMatgip(Matgip restaurant) {		
		matgipMapper.saveMatgip(restaurant);	
	}
	
	public Matgip findMatgip(String matgip_title, String member_mail) {
		Matgip findMatgip = matgipMapper.findMatgip(matgip_title, member_mail);
		return findMatgip;
	}
	
}
