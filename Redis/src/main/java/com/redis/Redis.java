package com.redis;

import java.util.Iterator;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.SerializableUtil;
import com.entity.Person;
import com.entity.User;

import redis.clients.jedis.Jedis;

/**
 * Redis sample
 * @author hanliang
 *
 */
public class Redis {
	
	private Jedis jedisMaster;
	private Jedis jedisSlave;
	private static String MASTERIP = "192.168.233.128";
	private static String SLAVEIP ="127.0.0.1";
	
	/**
	 * connection to the master
	 */
	public void connectMaster(){
		//connection to the master server
		jedisMaster = new Jedis(MASTERIP,6379);
		//do the auth
		jedisMaster.auth("master");
		//clear db
		jedisMaster.flushAll();
		//save a key-value
		String key = "key";
		jedisMaster.set(key, "masterValue");
		String value = jedisMaster.get("key");
		System.out.println("Masteer gets value of "+key+" is "+value);
		
		//write some keys
		for (int i = 0; i < 100; i++) {
			jedisMaster.set(i+"", i+"");
		}
		
		//save the DB changes
		String save = jedisMaster.save();
		System.out.println("Master save result is "+save);
	}
	
	/**
	 * connection to the slave
	 * get the value saved in master
	 */
	public void connectSlave(){
		//connect to the slave server
		jedisSlave = new Jedis(SLAVEIP, 6379);
		//do the auth
		jedisSlave.auth("slave");
		//get the master key
		String key = "key";
		String value = jedisSlave.get(key);
		System.out.println("Slave gets value of "+key+" is "+value);
	}
	
	/**
	 * try write a value into a read only slave
	 */
	public void writeSlave(){
		String slaveKey = "slaveKey";
		jedisSlave.set(slaveKey, "slaveValue");
		String slaveValue = jedisSlave.get(slaveKey);
		System.out.println(slaveValue);
	}
	
	/**
	 * getAllKeysFrom master
	 */
	public void getMasterKeys(){
		//get all keys
		Set<String> masterKeysSet = jedisMaster.keys("*");
		Iterator<String> it = masterKeysSet.iterator();
		System.out.println("masterkeys are:");
		while(it.hasNext()){
			String key = it.next();
			System.out.println(key + " | " + jedisMaster.get(key));
		}
		System.out.println("masterkeys end");
	}
	
	/**
	 * getAllkeysFrom slave
	 */
	public void getSlaveKeys(){
		//get all keys
		Set<String> slaveKeysSet = jedisSlave.keys("*");
		Iterator<String> itSlave = slaveKeysSet.iterator();
		System.out.println("slavekeys are: ");
		while(itSlave.hasNext()){
			String key = itSlave.next();
			System.out.println(key + " | " + jedisSlave.get(key));
		}
		System.out.println("slavekeys end");
	}
	
	/**
	 * save an object which serialized and get it
	 */
	public void saveObjectSerializable(){
		//connect to the slave server
		jedisSlave = new Jedis(SLAVEIP, 6379);
		//do the auth
		jedisSlave.auth("slave");
		//clear data
		jedisSlave.flushAll();
		User user = new User("name","pass");
		jedisSlave.set("user".getBytes(), SerializableUtil.toByteArray(user));
		System.out.println("object user saved");
		byte[] bytes = jedisSlave.get("user".getBytes());
		User u = (User) SerializableUtil.unserializeByteArray(bytes);
		System.out.println(u.toString());
	}
	
	/**
	 * save an normal object and get it
	 */
	public void saveObjectJson(){
		Person person = new Person("person_name",18);
		String person_key = "personn_json";
		jedisSlave.set(person_key, JSON.toJSONString(person));
		String person_value = jedisSlave.get(person_key);
		System.out.println("direct person_json is "+person_value);
		Person p = JSON.parseObject(person_value,Person.class);
		System.out.println(p.toString());
	}
	
	/**
	 * save an serialized object
	 */
	public void saveSerializedJsonObject(){
		User user = new User("name_json","pass_json");
		String key = "user_json";
		jedisSlave.set(key, JSON.toJSONString(user));
		System.out.println("object user_json saved");
		String value = jedisSlave.get(key);
		System.out.println("direct user_json is "+value);
		User u = JSON.parseObject(value, User.class);
		System.out.println(u.toString());
	}
	
	public static void main(String[] args) {
		Redis redis = new Redis();
//		redis.connectMaster();
//		redis.connectSlave();
//		redis.writeSlave();
//		redis.getMasterKeys();
//		redis.getSlaveKeys();
		
		redis.saveObjectSerializable();
		redis.saveObjectJson();
		redis.saveSerializedJsonObject();
	}
}