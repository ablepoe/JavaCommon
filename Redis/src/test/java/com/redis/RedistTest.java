package com.redis;


import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.redis.Redis;
import com.redis.RedisPool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis tests
 * @author hanliang
 *
 */
public class RedistTest {
	
	private Redis redis;
	private RedisPool redisPool;
	private JedisPool jpMaster;
	private JedisPool jpSlave;
	private Jedis jedisMaster;
	private Jedis jedisSlave;
	
	@Before
	public void init(){
		redis = new Redis();
		redisPool = new RedisPool();
		jpMaster = RedisPool.getJedisMasterPoolInstance();
		jpSlave = RedisPool.getJedisSlavePoolInstance();
		jedisMaster = jpMaster.getResource();
		jedisSlave = jpSlave.getResource();
	}

	@Test
	@Ignore
	public void testConnectMaster() {
		redis.connectMaster();
	}

	@Test
	@Ignore
	public void testConnectSlave() {
		redis.connectSlave();
	}

	@Test
	@Ignore
	public void testWriteSlave() {
		redis.connectSlave();
		redis.writeSlave();
	}

	@Test
	@Ignore
	public void testGetMasterKeys() {
		redis.connectMaster();
		redis.getMasterKeys();
	}

	@Test
	@Ignore
	public void testGetSlaveKeys() {
		redis.connectSlave();
		redis.getSlaveKeys();
	}

	@Test
	@Ignore
	public void testPoolConnectMaster() {
		redisPool.connectMaster(jedisMaster);
	}

	@Test
	@Ignore
	public void testPoolConnectSlave() {
		redisPool.connectSlave(jedisSlave);
	}

	@Test
	@Ignore
	public void testPoolWriteSlave() {
		redisPool.writeSlave(jedisSlave);
	}

	@Test
	@Ignore
	public void testPoolGetMasterKeys() {
		redisPool.getMasterKeys(jedisMaster);
	}

	@Test
	@Ignore
	public void testPoolGetSlaveKeys() {
		redisPool.getSlaveKeys(jedisSlave);
	}
	
	
}
