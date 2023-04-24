package com.example.accessingdatamysql;


import org.springframework.data.repository.CrudRepository;

/**
 * This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository 这将由Spring自动实现到一个名为userRepository的Bean中
 * CRUD refers Create, Read, Update, Delete CRUD指的是创建、读取、更新、删除
 * 其实是以前的xxDao!!!
 * 其他项目会加入@Repository，这个貌似没加也能用
 * Integer 应该对应User的id为该类型，但是写Long没报错，没有测试是否可以，在其他项目中观测到Long比较通用，包括设置Id为Long类型
 * 比如是接口类型，如果是class会报错
 */
public interface UserRepository extends CrudRepository<User, Integer> {
}
