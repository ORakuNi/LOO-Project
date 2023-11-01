package com.example.loo.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.example.loo.model.member.Member;
import com.example.loo.repository.PhonesMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PhonesService {
	
	private final PhonesMapper phonesMapper;

	public List<Member> findAllPhones(){
		List<Member> findAllPhones = phonesMapper.findAllPhones();	
		
		return findAllPhones;
	}
	
	public List<Member> findMember(@Param("member_name") String member_name, 
									@Param("member_mail") String member_mail, 
									@Param("phone") String phone, 
									@Param("department_name") String department_name){
		
		if(!(member_mail==null ||member_mail.isEmpty()) || 
			!(member_name==null||member_name.isEmpty()) ||  
        	!(department_name == null || department_name.isEmpty()) ||
        	!(phone ==null || phone.isEmpty())) {
			List<Member> member = 
					phonesMapper.findMember(member_name, member_mail, phone, department_name);
			
			return member;
		} else {
			return null;
		}
	}
	
	
}
