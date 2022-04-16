package com.ithawk.demo.spring5.webflux.repository;

import com.ithawk.demo.spring5.webflux.bean.Student;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

// 第一个泛型：当前Repository操作的实体类
// 第二个泛型：当前实体类的主键类型
@Repository
public interface StudentRepository 
			extends ReactiveMongoRepository<Student, String> {

	/**
	 * 根据年龄上下限进行查询
	 * @param below 年龄下限（不包含此下限）
	 * @param top 年龄上限（不包含此上限）
	 * @return
	 */
	Flux<Student> findByAgeBetween(int below, int top);

	@Query("{'age':{'$gte':?0, '$lte':?1}}")
	Flux<Student> queryByAge(int below, int top);
}



