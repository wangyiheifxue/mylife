package com.mylife.redis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@PropertySource("classpath:config/redis.properties")
public class RedisService {

	@Autowired
	private RedisTemplate<String,Object> redisTemplate;

	@Value("${redis.keyPrefix}")
	private String keyPrefix;

	public RedisService(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 指定缓存失效时间
	 * @param key 键
	 * @param time 时间(秒)
	 * @return
	 */
	public boolean expire(String key,long time){
		try {
			key = buildKey(key);
			if(time > 0){
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 根据key 获取过期时间
	 * @param key 键 不能为null
	 * @return 时间(秒) 返回0代表为永久有效
	 */
	public long getExpire(String key){
		return redisTemplate.getExpire(buildKey(key),TimeUnit.SECONDS);
	}

	/**
	 * 判断key是否存在
	 * @param key 键
	 * @return true 存在 false不存在
	 */
	public boolean hasKey(String key){
		try {
			return redisTemplate.hasKey(buildKey(key));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 删除缓存
	 * @param keys 可以传一个值 或多个
	 */
	@SuppressWarnings("unchecked")
	public void removeCache(String ... keys){
		if(keys != null && keys.length > 0){
			if(keys.length == 1){
				redisTemplate.delete(buildKey(keys[0]));
			}else{
				List<String> keyList = new ArrayList<>(keys.length);
				for (String key:keys) {
					keyList.add(buildKey(key));
				}
				redisTemplate.delete(keyList);
			}
		}
	}

	//============================String=============================
	/**
	 * 普通缓存获取
	 * @param key 键
	 * @return 值
	 */
	public <T> T getCache(String key){
		key = buildKey(key);
		return key == null ? null : (T) redisTemplate.opsForValue().get(key);
	}

	/**
	 * 普通缓存放入
	 * @param key 键
	 * @param value 值
	 * @return true成功 false失败
	 */
	public boolean putCache(String key,Object value) {
		try {
			redisTemplate.opsForValue().set(buildKey(key), value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 普通缓存放入并设置时间
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
	 * @return true成功 false 失败
	 */
	public boolean putCache(String key,Object value,long time){
		try {
			key = buildKey(key);
			if(time > 0){
				redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
			}else{
				putCache(key, value);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 递增
	 * @param key 键
	 * @param delta 要增加几(大于0)
	 * @return
	 */
	public long incr(String key, long delta){
		if(delta < 0){
			throw new RuntimeException("递增因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(buildKey(key), delta);
	}

	/**
	 * 递减
	 * @param key 键
	 * @param delta 要减少几(小于0)
	 * @return
	 */
	public long decr(String key, long delta){
		if(delta < 0){
			throw new RuntimeException("递减因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(buildKey(key), -delta);
	}

	//================================Map=================================
	/**
	 * HashGet
	 * @param key 键 不能为null
	 * @param hashKey 项 不能为null
	 * @return 值
	 */
	public Object hashGet(String key,String hashKey){
		return redisTemplate.opsForHash().get(buildKey(key), hashKey);
	}

	/**
	 * 获取hashKey对应的所有键值
	 * @param key 键
	 * @return 对应的多个键值
	 */
	public Map<String,Object> hashMapGet(String key){
		Map<String,Object> result = null;
		Map<Object,Object> map = redisTemplate.opsForHash().entries(buildKey(key));
		// 转化为Map<String,Object>
		if(map != null){
			result = new HashMap<>(map.size());
			for (Map.Entry<Object,Object> entry :map.entrySet()) {
				result.put(entry.getKey().toString(),entry.getValue());
			}
		}
		return result;
	}

	/**
	 * HashSet
	 * @param key 键
	 * @param map 对应多个键值
	 * @return true 成功 false 失败
	 */
	public boolean hashMapSet(String key, Map<String,Object> map){
		try {
			redisTemplate.opsForHash().putAll(buildKey(key), map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * HashSet 并设置时间
	 * @param key 键
	 * @param map 对应多个键值
	 * @param time 时间(秒)
	 * @return true成功 false失败
	 */
	public boolean hashMapSet(String key, Map<String,Object> map, long time){
		try {
			key = buildKey(key);
			redisTemplate.opsForHash().putAll(key, map);
			if(time > 0){
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 向一张hash表中放入数据,如果不存在将创建
	 * @param key 键
	 * @param hashKey 项
	 * @param value 值
	 * @return true 成功 false失败
	 */
	public boolean hashSet(String key,String hashKey,Object value) {
		try {
			redisTemplate.opsForHash().put(buildKey(key), hashKey, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 向一张hash表中放入数据,如果不存在将创建
	 * @param key 键
	 * @param hashKey 项
	 * @param value 值
	 * @param time 时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
	 * @return true 成功 false失败
	 */
	public boolean hashSet(String key,String hashKey,Object value,long time) {
		try {
			key = buildKey(key);
			redisTemplate.opsForHash().put(key, hashKey, value);
			if(time > 0){
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 删除hash表中的值
	 * @param key 键 不能为null
	 * @param hashKeys 项 可以使多个 不能为null
	 */
	public void hashDel(String key, Object... hashKeys){
		redisTemplate.opsForHash().delete(buildKey(key),hashKeys);
	}

	/**
	 * 判断hash表中是否有该项的值
	 * @param key 键 不能为null
	 * @param hashKey 项 不能为null
	 * @return true 存在 false不存在
	 */
	public boolean hashHasKey(String key, String hashKey){
		return redisTemplate.opsForHash().hasKey(buildKey(key), hashKey);
	}

	/**
	 * hash递增 如果不存在,就会创建一个 并把新增后的值返回
	 * @param key 键
	 * @param hashKey 项
	 * @param by 要增加几(大于0)
	 * @return
	 */
	public double hashIncr(String key, String hashKey,double by){
		return redisTemplate.opsForHash().increment(buildKey(key), hashKey, by);
	}

	/**
	 * hash递减
	 * @param key 键
	 * @param hashKey 项
	 * @param by 要减少记(小于0)
	 * @return
	 */
	public double hashDecr(String key, String hashKey,double by){
		return redisTemplate.opsForHash().increment(buildKey(key), hashKey,-by);
	}

	//============================set=============================
	/**
	 * 根据key获取Set中的所有值
	 * @param key 键
	 * @return
	 */
	public Set<Object> setGet(String key){
		try {
			return redisTemplate.opsForSet().members(buildKey(key));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据value从一个set中查询,是否存在
	 * @param key 键
	 * @param value 值
	 * @return true 存在 false不存在
	 */
	public boolean setHasKey(String key,Object value){
		try {
			return redisTemplate.opsForSet().isMember(buildKey(key), value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将数据放入set缓存
	 * @param key 键
	 * @param values 值 可以是多个
	 * @return 成功个数
	 */
	public long setSet(String key, Object...values) {
		try {
			return redisTemplate.opsForSet().add(buildKey(key), values);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 将set数据放入缓存
	 * @param key 键
	 * @param time 时间(秒)
	 * @param values 值 可以是多个
	 * @return 成功个数
	 */
	public long setSetAndTime(String key,long time,Object...values) {
		try {
			key = buildKey(key);
			Long count = redisTemplate.opsForSet().add(key, values);
			if(time > 0) {
				expire(key, time);
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获取set缓存的长度
	 * @param key 键
	 * @return
	 */
	public long setGetSetSize(String key){
		try {
			return redisTemplate.opsForSet().size(buildKey(key));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 移除值为value的
	 * @param key 键
	 * @param values 值 可以是多个
	 * @return 移除的个数
	 */
	public long setRemove(String key, Object ...values) {
		try {
			Long count = redisTemplate.opsForSet().remove(buildKey(key), values);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	//===============================list=================================

	/**
	 * 获取list缓存的内容
	 * @param key 键
	 * @param start 开始
	 * @param end 结束  0 到 -1代表所有值
	 * @return
	 */
	public List<Object> listGet(String key, long start, long end){
		try {
			return redisTemplate.opsForList().range(buildKey(key), start, end);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取list缓存的长度
	 * @param key 键
	 * @return
	 */
	public long listGetListSize(String key){
		try {
			return redisTemplate.opsForList().size(buildKey(key));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 通过索引 获取list中的值
	 * @param key 键
	 * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
	 * @return
	 */
	public Object listGetIndex(String key,long index){
		try {
			return redisTemplate.opsForList().index(buildKey(key), index);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将list放入缓存
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public boolean listSet(String key, Object value) {
		try {
			redisTemplate.opsForList().rightPush(buildKey(key), value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将list放入缓存
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒)
	 * @return
	 */
	public boolean listSet(String key, Object value, long time) {
		try {
			key = buildKey(key);
			redisTemplate.opsForList().rightPush(key, value);
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将list放入缓存
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public boolean listSet(String key, List<Object> value) {
		try {
			redisTemplate.opsForList().rightPushAll(buildKey(key), value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将list放入缓存
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒)
	 * @return
	 */
	public boolean listSet(String key, List<Object> value, long time) {
		try {
			key = buildKey(key);
			redisTemplate.opsForList().rightPushAll(key, value);
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 根据索引修改list中的某条数据
	 * @param key 键
	 * @param index 索引
	 * @param value 值
	 * @return
	 */
	public boolean listUpdateIndex(String key, long index,Object value) {
		try {
			redisTemplate.opsForList().set(buildKey(key), index, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 移除N个值为value
	 * @param key 键
	 * @param count 移除多少个
	 * @param value 值
	 * @return 移除的个数
	 */
	public long listRemove(String key,long count,Object value) {
		try {
			Long remove = redisTemplate.opsForList().remove(buildKey(key), count, value);
			return remove;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 模糊查询获取key值
	 * @param pattern
	 * @return
	 */
	public Set keys(String pattern){
		return redisTemplate.keys(pattern);
	}

	/**
	 * 使用Redis的消息队列
	 * @param channel
	 * @param message 消息内容
	 */
	public void convertAndSend(String channel, Object message){
		redisTemplate.convertAndSend(channel,message);
	}


	//=========BoundListOperations 用法 start============

	/**
	 * 将数据添加到Redis的list中（从右边添加）
	 * @param listKey
	 * @param expireEnum 有效期的枚举类
	 * @param values 待添加的数据
	 */
	public void addToListRight(String listKey, Status.ExpireEnum expireEnum, Object... values) {
		//绑定操作
		BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(buildKey(listKey));
		//插入数据
		boundValueOperations.rightPushAll(values);
		//设置过期时间
		boundValueOperations.expire(expireEnum.getTime(),expireEnum.getTimeUnit());
	}
	/**
	 * 根据起始结束序号遍历Redis中的list
	 * @param listKey
	 * @param start  起始序号
	 * @param end  结束序号
	 * @return
	 */
	public List<Object> rangeList(String listKey, long start, long end) {
		//绑定操作
		BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(buildKey(listKey));
		//查询数据
		return boundValueOperations.range(start, end);
	}
	/**
	 * 弹出右边的值 --- 并且移除这个值
	 * @param listKey
	 */
	public Object rightPop(String listKey){
		//绑定操作
		BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(buildKey(listKey));
		return boundValueOperations.rightPop();
	}

	//=========BoundListOperations 用法 End============


	/**
	 * @description : 转化key（添加前缀）
	 * @author : wyh
	 * @date : 2020/6/28 14:30
	 * @params : [key]
	 * @return : java.lang.String
	 **/
	private String buildKey(String key){
		if(StringUtils.isNotBlank(key)){
			return keyPrefix + ":" + key;
		}
		return null;
	}
}
