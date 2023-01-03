package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

//에노테이션 없이 JPA 레파지토리 상속해서 IOC 자동 등록됨
public interface UserRepository extends JpaRepository<User,Integer> {
    // JPA query method
    // 규칙 https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation

    User findByUsername(String username); // username으로 찾아서 User로 리턴해줌

}
