package com.ithawk.demo.spring5.webflux.controller;

import com.ithawk.demo.spring5.webflux.bean.Student;
import com.ithawk.demo.spring5.webflux.repository.StudentRepository;
import com.ithawk.demo.spring5.webflux.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//import javax.validation.Valid;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentRepository repository;

	@GetMapping("/all")
	public Flux<Student> getAll() {
		return repository.findAll();
	}

	@GetMapping(value = "/sse/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Student> getAllSSE() {
		return repository.findAll();
	}

	@PostMapping("/save")
	public Mono<Student> saveHandle( @RequestBody Student student) {
		// 验证姓名的合法性
		ValidateUtil.valideName(student.getName());
		return repository.save(student);
	}

	// 无状态删除
	@DeleteMapping("/delcommon/{id}")
	public Mono<Void> delCommonHandle(@PathVariable("id") String id) {
		return repository.deleteById(id);
	}

	// 有状态删除
	// 需求：若删除的对象存在，且删除成功，则返回响应码200，否则返回响应码404
	// map()与flatMap()均可做映射，但这两个方法与Stream编程中的两个同名方法没有任何关系
	// map()：同步方法
	// flatMap()：异步方法
	// 一般选择的标准是：若映射的内容中包含有耗时方法，则选择flatMap()，否则选择map()
	@DeleteMapping("/delstat/{id}")
	public Mono<ResponseEntity<Void>> delStatusHandle(@PathVariable("id") String id) {
		return repository.findById(id)
						 .flatMap(stu -> repository.delete(stu).
								           then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
						 .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}

	// 修改操作
	// 需求：若修改成功，则返回修改后的对象数据及200；若指定的id对象不存在，则返回404
	@PutMapping("/update/{id}")
	public Mono<ResponseEntity<Student>> updateHanel(@PathVariable("id") String id,
													 @RequestBody Student student ) {
		// 验证姓名的合法性
		ValidateUtil.valideName(student.getName());
		return repository.findById(id)
						 .flatMap(stu -> {
						 	stu.setName(student.getName());
						 	stu.setAge(student.getAge());
						 	return repository.save(stu);
						 })
				         .map(stu -> new ResponseEntity<Student>(stu, HttpStatus.OK))
						 .defaultIfEmpty(new ResponseEntity<Student>(HttpStatus.NOT_FOUND));
	}

	// 根据id查询
	// 需求：若查询成功，则返回指定id的对象及200；若指定的id对象不存在，则返回404
	@GetMapping("/find/{id}")
	public Mono<ResponseEntity<Student>> findHandle(@PathVariable("id") String id) {
		return repository.findById(id)
						.map(stu -> new ResponseEntity<Student>(stu, HttpStatus.OK))
						.defaultIfEmpty(new ResponseEntity<Student>(HttpStatus.NOT_FOUND));
	}

	// 根据年龄查询（普通返回）
	@GetMapping("/age/{below}/{up}")
	public Flux<Student> findByAgeHandle(@PathVariable("below") int below, @PathVariable("up") int up) {
		return repository.findByAgeBetween(below, up);
	}

	// 根据年龄查询（普通返回）
	@GetMapping(value = "/sse/age/{below}/{up}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Student> findByAgeSSEHandle(@PathVariable("below") int below, @PathVariable("up") int up) {
		return repository.findByAgeBetween(below, up);
	}

	// 根据年龄查询（普通返回）
	@GetMapping("/find/age/{below}/{up}")
	public Flux<Student> findByAgeHandle2(@PathVariable("below") int below, @PathVariable("up") int up) {
		return repository.findByAge(below, up);
	}

	// 根据年龄查询（普通返回）
	@GetMapping(value = "/sse/find/age/{below}/{up}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Student> findByAgeSSEHandle2(@PathVariable("below") int below, @PathVariable("up") int up) {
		return repository.findByAge(below, up);
	}
}



























