package com.redis;

import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * connection pool
 * @author hanliang
 *
 */
public class RedisPool {
	
	private static JedisPool jpMaster;
	private static JedisPool jpSlave;
	
	public static JedisPool getJedisMasterPoolInstance(){
		if(jpMaster == null){
			String host = "192.168.233.128";
			int port = 6379;
			int timeout = 10000;
			String password = "master";
			
			ResourceBundle rb = ResourceBundle.getBundle("RedisPool");
			int maxIdle = Integer.parseInt(rb.getString("maxIdle"));
			int maxTotal = Integer.parseInt(rb.getString("maxTotal"));
			long maxWaitMillis = Long.parseLong(rb.getString("maxWaitMillis"));
			boolean testOnBorrow = Boolean.parseBoolean(rb.getString("testOnBorrow"));
			boolean testOnReturn = Boolean.parseBoolean(rb.getString("testOnReturn"));
			JedisPoolConfig jpc = setConfigs(maxIdle, maxTotal, maxWaitMillis, testOnBorrow, testOnReturn);
			jpMaster = new JedisPool(jpc, host, port, timeout, password);
		}
		return jpMaster;
	}
	
	public static JedisPool getJedisSlavePoolInstance(){
		if(jpSlave == null){
			String host = "127.0.0.1";
			int port = 6379;
			int timeout = 10000;
			String password = "slave";
			
			ResourceBundle rb = ResourceBundle.getBundle("RedisPool");
			int maxIdle = Integer.parseInt(rb.getString("maxIdle"));
			int maxTotal = Integer.parseInt(rb.getString("maxTotal"));
			long maxWaitMillis = Long.parseLong(rb.getString("maxWaitMillis"));
			boolean testOnBorrow = Boolean.parseBoolean(rb.getString("testOnBorrow"));
			boolean testOnReturn = Boolean.parseBoolean(rb.getString("testOnReturn"));
			JedisPoolConfig jpc = setConfigs(maxIdle, maxTotal, maxWaitMillis, testOnBorrow, testOnReturn);
			jpSlave = new JedisPool(jpc, host, port, timeout, password);
		}
		return jpSlave;
	}
	
	private static JedisPoolConfig setConfigs(int maxIdle, int maxTotal, long maxWaitMillis, boolean testOnBorrow, boolean testOnReturn){
		JedisPoolConfig jpc = new JedisPoolConfig();
		jpc.setMaxIdle(maxIdle);
		jpc.setMaxTotal(maxTotal);
		jpc.setMaxWaitMillis(maxWaitMillis);
		jpc.setTestOnBorrow(testOnBorrow);
		jpc.setTestOnReturn(testOnReturn);
		return jpc;
	}

	public static void main(String[] args) {
		JedisPool jp = RedisPool.getJedisSlavePoolInstance();
		Jedis jedis = jp.getResource();
		System.out.println(jedis.ping());
	}
	
	/**
	 * connection to the master
	 */
	public void connectMaster(Jedis master){
		//do the auth
		master.auth("master");
		//clear db
		master.flushAll();
		//save a key-value
		String key = "key";
		master.set(key, "masterValue");
		String value = master.get("key");
		System.out.println("Masteer gets value of "+key+" is "+value);
		
		//write some keys
		for (int i = 0; i < 100; i++) {
			master.set(i+"", i+"");
		}
		
		//save the DB changes
		String save = master.save();
		System.out.println("Master save result is "+save);
	}
	
	/**
	 * connection to the slave
	 * get the value saved in master
	 */
	public void connectSlave(Jedis slave){
		//do the auth
		slave.auth("slave");
		//get the master key
		String key = "key";
		String value = slave.get(key);
		System.out.println("Slave gets value of "+key+" is "+value);
	}
	
	/**
	 * try write a value into a read only slave
	 */
	public void writeSlave(Jedis slave){
		String slaveKey = "slaveKey";
		slave.set(slaveKey, "slaveValue");
		String slaveValue = slave.get(slaveKey);
		System.out.println(slaveValue);
	}
	
	/**
	 * getAllKeysFrom master
	 */
	public void getMasterKeys(Jedis master){
		//get all keys
		Set<String> masterKeysSet = master.keys("*");
		Iterator<String> it = masterKeysSet.iterator();
		System.out.println("masterkeys are:");
		while(it.hasNext()){
			String key = it.next();
			System.out.println(key + " | " + master.get(key));
		}
		System.out.println("masterkeys end");
	}
	
	/**
	 * getAllkeysFrom slave
	 */
	public void getSlaveKeys(Jedis slave){
		//get all keys
		Set<String> slaveKeysSet = slave.keys("*");
		Iterator<String> itSlave = slaveKeysSet.iterator();
		System.out.println("slavekeys are: ");
		while(itSlave.hasNext()){
			String key = itSlave.next();
			System.out.println(key + " | " + slave.get(key));
		}
		System.out.println("slavekeys end");
	}
	
}
