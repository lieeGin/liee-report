package com.liee.core.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.liee.core.dao.SysMenu;
import com.liee.core.log.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {

	private static Logger log = Logger.getLogger();

	private static JedisPool jedisPool = null;
	// Redis服务器IP
	private static String ADDR = "120.77.172.226";
	// Redis的端口号
	private static int PORT = 6379;
	// 访问密码
	private static String AUTH = "redis123";

	// 初始化redis连接池
	public static void init() {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			// 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
			config.setBlockWhenExhausted(true);
			// 设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
			config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
			// 是否启用pool的jmx管理功能, 默认true
			config.setJmxEnabled(true);
			// 最大空闲连接数, 默认8个 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
			config.setMaxIdle(8);
			// 最大连接数, 默认8个
			config.setMaxTotal(200);
			// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
			config.setMaxWaitMillis(1000 * 100);
			// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
			config.setTestOnBorrow(true);
			jedisPool = new JedisPool(config, ADDR, PORT, 3000, AUTH);
		} catch (Exception e) {
			log.error("==>初始化redis错误", e);
		}
	}

	public static void main(String[] args) {

		JedisPoolConfig config = new JedisPoolConfig();
		// 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
		config.setBlockWhenExhausted(true);
		// 设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
		config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
		// 是否启用pool的jmx管理功能, 默认true
		config.setJmxEnabled(true);
		// 最大空闲连接数, 默认8个 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
		config.setMaxIdle(8);
		// 最大连接数, 默认8个
		config.setMaxTotal(200);
		// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
		config.setMaxWaitMillis(1000 * 100);
		// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
		config.setTestOnBorrow(true);
		jedisPool = new JedisPool(config, ADDR, PORT, 3000, AUTH);

		Jedis r = jedisPool.getResource();

		/*SysMenu m = new SysMenu();
		m.setMenuName("测试");
		m.setUrl("qweqweqe/qweqweqe");*/
		Test t = new Test();
		t.setName("11111");
		t.setPhone("2222");
		JedisUtil.setJson("jsonObj", t);
		
		Map mm = (Map)JedisUtil.getJson("jsonObj");
		System.out.println("result:"+mm.get("name")+":"+ mm.get("phone"));
	}

	/**
	 * 获取Jedis实例
	 * 
	 * @return
	 */
	private synchronized static Jedis getJedis() {
		try {
			if (jedisPool != null) {
				Jedis resource = jedisPool.getResource();
				return resource;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	public static void close(final Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

	/**
	 * java序列化设值
	 * 
	 * @param key
	 * @param object
	 * @return
	 */
	public static String setObject(String key, Object object) {
		Jedis jedis = getJedis();
		return jedis.set(key.getBytes(), SerializeUtil.serialize(object));
	}

	/**
	 * 根据key获取反序列化对象
	 * 
	 * @param key
	 * @return
	 */
	public static Object getObject(String key) {
		Jedis jedis = getJedis();
		byte[] value = jedis.get(key.getBytes());
		return SerializeUtil.unserialize(value);
	}

	/** delete a key **/
	public static boolean delObject(String key) {
		Jedis jedis = getJedis();
		return jedis.del(key.getBytes()) > 0;
	}

	/**
	 * 以json格式设置
	 * 
	 * @param key
	 * @param object
	 * @return
	 */
	public static String setJson(String key, Object object) {
		Jedis jedis = getJedis();
		Gson gson = new Gson();
		String res = gson.toJson(object);
		return jedis.set(key, res);
	}

	/**
	 * 根据key获取json
	 * @param key
	 * @return
	 */
	public static Object getJson(String key) {
		Jedis jedis = getJedis();
		String value = jedis.get(key);
		Gson gson = new Gson();
		Object obj = gson.fromJson(value, Object.class);
		return obj;
	}

	/**
	 * 设置字符串内容
	 * 
	 * @param key
	 * @param value
	 */
	public static String setString(String key, String value) {
		Jedis jedis = getJedis();
		return jedis.set(key, value);
	}

	/**
	 * 根据key值获取字符串内容
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		Jedis jedis = getJedis();
		return jedis.get(key);
	}

	/**
	 * 根据key值删除字符串
	 * 
	 * @param key
	 */
	public static boolean delString(String key) {
		Jedis jedis = getJedis();
		return jedis.del(key) > 0;
	}

	/**
	 * 设置map
	 * 
	 * @param key
	 * @param map
	 */
	public static String setMap(String key, Map map) {
		Jedis jedis = getJedis();
		return jedis.hmset(key, map);
	}

	/**
	 * 根据key值，以及fields 获取map
	 * 
	 * @param key
	 * @param fields
	 * @return
	 */
	public static Map<String, String> getMap(String key, String... fields) {
		Jedis jedis = getJedis();

		List<String> list = jedis.hmget(key, fields);
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < fields.length; i++) {
			if (list.size() > i) {
				map.put(fields[i], list.get(0));
			} else {
				map.put(fields[i], "");
			}
		}
		return map;
	}

	/**
	 * 删除map中的某个值
	 * 
	 * @param key
	 * @param field
	 */
	public static boolean delMap(String key, String field) {
		Jedis jedis = getJedis();
		return jedis.hdel(key, field) > 0;
	}

	/**
	 * 获取map中值的个数
	 * 
	 * @param key
	 * @return
	 */
	public static Long getMapLen(String key) {
		Jedis jedis = getJedis();
		return jedis.hlen(key);
	}

	/**
	 * 是否存在key的map
	 * 
	 * @param key
	 * @return
	 */
	public static boolean mapExists(String key) {
		Jedis jedis = getJedis();
		return jedis.exists(key);
	}

	/**
	 * 返回map中所有的key
	 * 
	 * @param key
	 * @return
	 */
	public static Set<String> getMapKeys(String key) {
		Jedis jedis = getJedis();
		return jedis.hkeys(key);
	}

	/**
	 * 获取map所有的value
	 * 
	 * @param key
	 * @return
	 */
	public static List<String> getMapValues(String key) {
		Jedis jedis = getJedis();
		return jedis.hvals(key);
	}

	/**
	 * 删除list
	 * 
	 * @param key
	 */
	public static boolean delList(String key) {
		Jedis jedis = getJedis();
		return jedis.del(key) > 0;
	}

	/**
	 * list设置值
	 * 
	 * @param key
	 * @param value
	 */
	public static Long setList(String key, String value) {
		Jedis jedis = getJedis();
		return jedis.lpush(key, value);
	}

	/**
	 * 获取list所有值
	 * 
	 * @param key
	 * @return
	 */
	public static List<String> getList(String key) {
		Jedis jedis = getJedis();
		return jedis.lrange(key, 0, -1);
	}

	/**
	 * jedis操作Set
	 */
	public static void testSet(Jedis jedis) {
		// 添加
		jedis.sadd("user", "liuling");
		jedis.sadd("user", "xinxin");
		jedis.sadd("user", "ling");
		jedis.sadd("user", "zhangxinxin");
		jedis.sadd("user", "who");
		// 移除noname
		jedis.srem("user", "who");
		System.out.println(jedis.smembers("user"));// 获取所有加入的value
		System.out.println(jedis.sismember("user", "who"));// 判断 who
															// 是否是user集合的元素
		System.out.println(jedis.srandmember("user"));
		System.out.println(jedis.scard("user"));// 返回集合的元素个数
	}

	/**
	 * 字符串测试
	 * 
	 * @param jedis
	 */
	public static void testString(Jedis jedis) {
		jedis.set("name", "xxxx");// 向key-->name中放入了value-->xinxin
		System.out.println(jedis.get("name"));// 执行结果：xinxin

		jedis.append("name", " is my lover"); // 拼接
		System.out.println(jedis.get("name"));

		jedis.del("name"); // 删除某个键
		System.out.println(jedis.get("name"));
		// 设置多个键值对
		jedis.mset("name", "某某某", "age", "24", "qq", "476777XXX");
		jedis.incr("age"); // 进行加1操作
		System.out.println(jedis.get("name") + "-" + jedis.get("age") + "-" + jedis.get("qq"));
	}

	/**
	 * map 用法
	 * 
	 * @param jedis
	 */
	public static void testMap(Jedis jedis) {
		// -----添加数据----------
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "xinxin");
		map.put("age", "22");
		map.put("qq", "123456");
		jedis.hmset("user", map);
		// 取出user中的name，执行结果:[minxr]-->注意结果是一个泛型的List
		// 第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数
		List<String> rsmap = jedis.hmget("user", "name", "age", "qq");
		System.out.println(rsmap);

		// 删除map中的某个键值
		jedis.hdel("user", "age");
		System.out.println(jedis.hmget("user", "age")); // 因为删除了，所以返回的是null
		System.out.println(jedis.hlen("user")); // 返回key为user的键中存放的值的个数2
		System.out.println(jedis.exists("user"));// 是否存在key为user的记录 返回true
		System.out.println(jedis.hkeys("user"));// 返回map对象中的所有key
		System.out.println(jedis.hvals("user"));// 返回map对象中的所有value

		Iterator<String> iter = jedis.hkeys("user").iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			System.out.println(key + ":" + jedis.hmget("user", key));
		}
	}

	/**
	 * jedis操作List
	 */
	public static void testList(Jedis jedis) {
		// 开始前，先移除所有的内容
		jedis.del("java framework");
		System.out.println(jedis.lrange("java framework", 0, -1));
		// 先向key java framework中存放三条数据
		jedis.lpush("java framework", "spring");
		jedis.lpush("java framework", "struts");
		jedis.lpush("java framework", "hibernate");
		// 再取出所有数据jedis.lrange是按范围取出，
		// 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有
		System.out.println(jedis.lrange("java framework", 0, -1));

		jedis.del("java framework");
		jedis.rpush("java framework", "spring");
		jedis.rpush("java framework", "struts");
		jedis.rpush("java framework", "hibernate");
		System.out.println(jedis.lrange("java framework", 0, -1));
	}

}
