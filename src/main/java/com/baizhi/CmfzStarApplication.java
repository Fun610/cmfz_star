package com.baizhi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.baizhi.dao")
public class CmfzStarApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmfzStarApplication.class, args);
    }

    @Bean
    public Jedis getJedis(){
        return new Jedis("192.168.81.137",6379);
    }

}
