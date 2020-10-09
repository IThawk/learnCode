package com.ithawk.demo.spring.v1.dubbo.spring.demo.action;


import com.ithawk.demo.spring.v1.vip.spring5.demo.model.Member;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class MemberAction {


	@GetMapping(value="/getByName.json")
	@ResponseBody
	public Mono<Member> getByName(@RequestParam String name){
		Member member = new Member();
		member.setName(name);
		return Mono.justOrEmpty(member);
	}


	@GetMapping("/getAll.json")
	@ResponseBody
	public Flux<Member> preview(HttpServletRequest request, HttpServletResponse response){

		return null;
	}
	

	@PostMapping("/remove.json")
	@ResponseBody
	public Mono<Member> remove(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}
	
	/**
	 * 获取上传进度
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value="/edit.json")
	@ResponseBody
	public Flux<Member> edit(HttpServletRequest request, HttpServletResponse response){
		return null;
	}
	
}
