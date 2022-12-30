package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

//에노테이션 없이 JPA 레파지토리 상속해서 IOC 자동 등록됨
public interface UserRepository extends JpaRepository<User,Integer> {


}
