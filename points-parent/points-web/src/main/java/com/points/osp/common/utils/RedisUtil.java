package com.points.osp.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisUtil {

	@Autowired
	private ShardedJedisPool shardedJedisPool;
	private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

	public long expire(String key, int seconds) {
		if ((key == null) || (key.equals(""))) {
			return 0L;
		}

		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			long l = shardedJedis.expire(key, seconds).longValue();
			return l;
		} catch (Exception ex) {
			logger.error("EXPIRE error[key=" + key + " seconds=" + seconds
					+ "]" + ex.getMessage(), ex);

			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return 0L;
	}

	public long expireAt(String key, int unixTimestamp) {
		if ((key == null) || (key.equals(""))) {
			return 0L;
		}

		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			long l = shardedJedis.expireAt(key, unixTimestamp).longValue();
			return l;
		} catch (Exception ex) {
			logger.error("EXPIRE error[key=" + key + " unixTimestamp="
					+ unixTimestamp + "]" + ex.getMessage(), ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return 0L;
	}

	public String trimList(String key, long start, long end) {
		if ((key == null) || (key.equals(""))) {
			return "-";
		}
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			String str = shardedJedis.ltrim(key, start, end);
			return str;
		} catch (Exception ex) {
			logger.error("LTRIM ³ö´í[key=" + key + " start=" + start + " end="
					+ end + "]" + ex.getMessage(), ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return "-";
	}

	public long countSet(String key) {
		if (key == null) {
			return 0L;
		}
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			long l = shardedJedis.scard(key).longValue();
			return l;
		} catch (Exception ex) {
			logger.error("countSet error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return 0L;
	}

	public boolean addSet(String key, int seconds, String value) {
		boolean result = addSet(key, value);
		if (result) {
			long i = expire(key, seconds);
			return i == 1L;
		}
		return false;
	}

	public boolean addSet(String key, int seconds, List<String> values) {
		boolean result = addSet(key, values);
		if (result) {
			long i = expire(key, seconds);
			return i == 1L;
		}
		return false;
	}

	public boolean addSet(String key, List<String> values) {
		if ((key == null) || (values == null) || (values.size() <= 0)) {
			return false;
		}
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			shardedJedis.sadd(key, (String[]) values.toArray(new String[0]));
			int i = 1;
			return i > 0;
		} catch (Exception ex) {
			logger.error("setList error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return false;
	}

	public boolean addSet(String key, String value) {
		if ((key == null) || (value == null)) {
			return false;
		}
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			shardedJedis.sadd(key, new String[] { value });
			int i = 1;
			return i > 0;
		} catch (Exception ex) {
			logger.error("setList error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return false;
	}

	public boolean containsInSet(String key, String value) {
		if ((key == null) || (value == null)) {
			return false;
		}
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			boolean bool = shardedJedis.sismember(key, value).booleanValue();
			return bool;
		} catch (Exception ex) {
			logger.error("setList error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return false;
	}

	public Set<String> getSet(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			Set<String> localSet = shardedJedis.smembers(key);
			return localSet;
		} catch (Exception ex) {
			logger.error("getList error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return null;
	}

	public boolean removeSetValue(String key, String value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			shardedJedis.srem(key, new String[] { value });
			int i = 1;
			return i > 0;
		} catch (Exception ex) {
			logger.error("getList error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return false;
	}

	public int removeListValue(String key, List<String> values) {
		return removeListValue(key, 1L, values);
	}

	public int removeListValue(String key, long count, List<String> values) {
		int result = 0;
		if ((values != null) && (values.size() > 0)) {
			for (String value : values) {
				if (removeListValue(key, count, value)) {
					result++;
				}
			}
		}
		return result;
	}

	public boolean removeListValue(String key, long count, String value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			shardedJedis.lrem(key, count, value);
			int i = 1;
			return i > 0;
		} catch (Exception ex) {
			logger.error("getList error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return false;
	}

	public List<String> rangeList(String key, long start, long end) {
		if ((key == null) || (key.equals(""))) {
			return null;
		}
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			List<String> localList = shardedJedis.lrange(key, start, end);
			return localList;
		} catch (Exception ex) {
			logger.error("rangeList ³ö´í[key=" + key + " start=" + start
					+ " end=" + end + "]" + ex.getMessage(), ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return null;
	}

	public long countList(String key) {
		if (key == null) {
			return 0L;
		}
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			long l = shardedJedis.llen(key).longValue();
			return l;
		} catch (Exception ex) {
			logger.error("countList error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return 0L;
	}

	public boolean addList(String key, int seconds, String value) {
		boolean result = addList(key, value);
		if (result) {
			long i = expire(key, seconds);
			return i == 1L;
		}
		return false;
	}

	public boolean addList(String key, String value) {
		if ((key == null) || (value == null)) {
			return false;
		}
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			shardedJedis.lpush(key, new String[] { value });
			int i = 1;
			return i > 0;
		} catch (Exception ex) {
			logger.error("setList error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return false;
	}

	public boolean addList(String key, List<String> list) {
		if ((key == null) || (list == null) || (list.size() == 0)) {
			return false;
		}
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			shardedJedis.lpush(key, (String[]) list.toArray(new String[0]));
			int i = 1;
			return i > 0;
		} catch (Exception ex) {
			logger.error("setList error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return true;
	}

	public List<String> getList(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			List<String> localList = shardedJedis.lrange(key, 0L, -1L);
			return localList;
		} catch (Exception ex) {
			logger.error("getList error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return null;
	}

	public List<String> getList(String key, int start, int end) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			List<String> localList = shardedJedis.lrange(key, start, end);
			return localList;
		} catch (Exception ex) {
			logger.error("getList error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return null;
	}

	public Long getListLength(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			Long localLong = shardedJedis.llen(key);
			return localLong;
		} catch (Exception ex) {
			logger.error("getList error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return null;
	}

	public boolean setHSet(String domain, String key, String value) {
		if (value == null)
			return false;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			shardedJedis.hset(domain, key, value);
			int i = 1;
			return i > 0;
		} catch (Exception ex) {
			logger.error("setHSet error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return false;
	}

	public String getHSet(String domain, String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			String str = shardedJedis.hget(domain, key);
			return str;
		} catch (Exception ex) {
			logger.error("getHSet error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return null;
	}

	public long delHSet(String domain, String key) {
		ShardedJedis shardedJedis = null;
		long count = 0L;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			count = shardedJedis.hdel(domain, new String[] { key }).longValue();
		} catch (Exception ex) {
			logger.error("delHSet error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return count;
	}

	public boolean existsHSet(String domain, String key) {
		ShardedJedis shardedJedis = null;
		boolean isExist = false;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			isExist = shardedJedis.hexists(domain, key).booleanValue();
		} catch (Exception ex) {
			logger.error("existsHSet error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return isExist;
	}

	public List<String> hvals(String domain) {
		ShardedJedis shardedJedis = null;
		List<String> retList = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			retList = shardedJedis.hvals(domain);
		} catch (Exception ex) {
			logger.error("hvals error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return retList;
	}

	public Set<String> hkeys(String domain) {
		ShardedJedis shardedJedis = null;
		Set<String> retList = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			retList = shardedJedis.hkeys(domain);
		} catch (Exception ex) {
			logger.error("hkeys error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return retList;
	}

	public long lenHset(String domain) {
		ShardedJedis shardedJedis = null;
		long retList = 0L;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			retList = shardedJedis.hlen(domain).longValue();
		} catch (Exception ex) {
			logger.error("hkeys error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return retList;
	}

	public boolean setSortedSet(String key, long score, String value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			shardedJedis.zadd(key, score, value);
			int i = 1;
			return i > 0;
		} catch (Exception ex) {
			logger.error("setSortedSet error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return false;
	}

	public Set<String> getSoredSet(String key, long startScore, long endScore,
			boolean orderByDesc) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			if (orderByDesc) {
				Set<String> localSet = shardedJedis.zrevrangeByScore(key,
						endScore, startScore);
				return localSet;
			}
			Set<String> localSet = shardedJedis.zrangeByScore(key, startScore,
					endScore);
			return localSet;
		} catch (Exception ex) {
			logger.error("getSoredSet error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return null;
	}

	public long countSoredSet(String key, long startScore, long endScore) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			Long count = shardedJedis.zcount(key, startScore, endScore);
			long l = count == null ? 0L : count.longValue();
			return l;
		} catch (Exception ex) {
			logger.error("countSoredSet error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return 0L;
	}

	public boolean delSortedSet(String key, String value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			long count = shardedJedis.zrem(key, new String[] { value })
					.longValue();
			int i = count > 0L ? 1 : 0;
			return i > 0;
		} catch (Exception ex) {
			logger.error("delSortedSet error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return false;
	}

	public Set<String> getSoredSetByRange(String key, int startRange,
			int endRange, boolean orderByDesc) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			if (orderByDesc) {
				Set<String> localSet = shardedJedis.zrevrange(key, startRange,
						endRange);
				return localSet;
			}
			Set<String> localSet = shardedJedis.zrange(key, startRange,
					endRange);
			return localSet;
		} catch (Exception ex) {
			logger.error("getSoredSetByRange error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return null;
	}

	public Double getScore(String key, String member) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			Double localDouble = shardedJedis.zscore(key, member);
			return localDouble;
		} catch (Exception ex) {
			logger.error("getSoredSet error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return null;
	}

	public boolean set(String key, String value, int second) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			shardedJedis.setex(key, second, value);
			int i = 1;
			return i > 0;
		} catch (Exception ex) {
			logger.error("set error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return false;
	}

	public boolean set(String key, String value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			shardedJedis.set(key, value);
			int i = 1;
			return i > 0;
		} catch (Exception ex) {
			logger.error("set error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return false;
	}

	public String get(String key, String defaultValue) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			String str = shardedJedis.get(key) == null ? defaultValue
					: shardedJedis.get(key);
			return str;
		} catch (Exception ex) {
			logger.error("get error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return defaultValue;
	}

	public boolean del(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			shardedJedis.del(key);
			int i = 1;
			return i > 0;
		} catch (Exception ex) {
			logger.error("del error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return false;
	}

	public long incr(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			long l = shardedJedis.incr(key).longValue();
			return l;
		} catch (Exception ex) {
			logger.error("incr error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return 0L;
	}

	public long decr(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			long l = shardedJedis.decr(key).longValue();
			return l;
		} catch (Exception ex) {
			logger.error("incr error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return 0L;
	}

	private void returnBrokenResource(ShardedJedis shardedJedis) {
		try {
			this.shardedJedisPool.returnBrokenResource(shardedJedis);
		} catch (Exception e) {
			logger.error("returnBrokenResource error.", e);
		}
	}

	private void returnResource(ShardedJedis shardedJedis) {
		try {
			this.shardedJedisPool.returnResource(shardedJedis);
		} catch (Exception e) {
			logger.error("returnResource error.", e);
		}
	}

	public Map<String, String> hgetAll(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			Map<String, String> localMap = shardedJedis.hgetAll(key);
			return localMap;
		} catch (Exception ex) {
			logger.error("set error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return new HashMap<String, String>();
	}

	public boolean hmset(String key, Map<String, String> hash) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			shardedJedis.hmset(key, hash);
			int i = 1;
			return i > 0;
		} catch (Exception ex) {
			logger.error("set error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return false;
	}

	public List<String> hmget(String key, String[] fields) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			List<String> localList = shardedJedis.hmget(key, fields);
			return localList;
		} catch (Exception ex) {
			logger.error("get error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return new ArrayList<String>();
	}

	public boolean lpush(String key, String[] values) {
		if ((key == null) || (values == null) || (values.length <= 0)) {
			return false;
		}
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			shardedJedis.lpush(key, values);
			int i = 1;
			return i > 0;
		} catch (Exception ex) {
			logger.error("setList error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return false;
	}

	public String rpop(String key) {
		if (key == null) {
			return null;
		}
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			String str = shardedJedis.rpop(key);
			return str;
		} catch (Exception ex) {
			logger.error("setList error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return null;
	}

	public Long llen(String key) {
		if (key == null) {
			return Long.valueOf(0L);
		}
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			Long localLong = shardedJedis.llen(key);
			return localLong;
		} catch (Exception ex) {
			logger.error("setList error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return Long.valueOf(0L);
	}

	public boolean exists(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			boolean bool = shardedJedis.exists(key).booleanValue();
			return bool;
		} catch (Exception ex) {
			logger.error("get error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return false;
	}

	public Boolean lock(String key, String value) {
		ShardedJedis shardedJedis = null;
		long ret = 0L;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
			ret = shardedJedis.setnx(key, value).longValue();
		} catch (Exception ex) {
			logger.error("get error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		if (ret == 1L)
			return Boolean.valueOf(true);
		return Boolean.valueOf(false);
	}
}