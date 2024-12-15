package com.example.projectfinal.CrawlData.Repository;

import com.example.projectfinal.CrawlData.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// This interface is a repository for the User entity，which is used to manage User entities
// 为什么里面什么都没有？==》因为这个接口继承了JpaRepository，JpaRepository已经实现了一些基本的增删改查方法
// JpaRepository是一个泛型接口，有两个参数，第一个参数是实体类，第二个参数是实体类的主键类型
// JpaRepository接口提供了一些基本的增删改查方法，如save、delete、findAll、findById等
// JpaRepository接口继承了PagingAndSortingRepository接口，PagingAndSortingRepository接口继承了CrudRepository接口
// CrudRepository接口是一个泛型接口，有两个参数，第一个参数是实体类，第二个参数是实体类的主键类型
public interface UserRepository extends JpaRepository<User, Integer> {
}