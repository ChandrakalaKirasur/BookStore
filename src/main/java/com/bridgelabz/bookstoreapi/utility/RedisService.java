package com.bridgelabz.bookstoreapi.utility;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstoreapi.entity.Book;

@Service
public class RedisService {

	private RedisTemplate<String, Object> redisTemplate;

	private HashOperations<String, Integer, Object> hashOperations;

	private final String TABEL_NAME = "book";
	
	@Autowired
	public RedisService(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void intializeHashOperations() {
		hashOperations = redisTemplate.opsForHash();
	}
	
	public void save(List<Book> books, Integer pageNo) {
		hashOperations.put(TABEL_NAME, pageNo, books);
//		hashOperations.delete(TABEL_NAME, pageNo);
	}
	
	public Object getBooks(Integer pageNo) {	
		if(hashOperations.hasKey(TABEL_NAME, pageNo))
			return hashOperations.get(TABEL_NAME, pageNo);
		return null;
	}
}
