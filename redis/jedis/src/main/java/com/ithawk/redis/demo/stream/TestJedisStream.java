package com.ithawk.redis.demo.stream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ithawk.redis.demo.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.StreamEntry;
import redis.clients.jedis.StreamEntryID;

/**
 * redis 5 版本上的Stream
 *
 */
public class TestJedisStream {
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("StreamEntryID.NEW_ENTRY=" + StreamEntryID.NEW_ENTRY);
        System.out.println("StreamEntryID.LAST_ENTRY=" + StreamEntryID.LAST_ENTRY);
        System.out.println("StreamEntryID.UNRECEIVED_ENTRY=" + StreamEntryID.UNRECEIVED_ENTRY);
        System.out.println();

        //添加stream 流
        xadd();
        xlen();
        System.out.println();
        xread();
        xrange();
        xrevrange();
        System.out.println();
        xdel();
        xlen();
        System.out.println();
        xadd();
        xadd();
        xlen();
        xtrim();
        xlen();

        try(Jedis jd = JedisUtil.getJedisUtil().getJedis()) {
            jd.del("k");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void xadd() {
        try(Jedis jd = JedisUtil.getJedisUtil().getJedis()) {
            Map<String, String> hash = new HashMap<>();
            hash.put("name", "Tom");
            hash.put("age", "13");
            //key, id, hash
            StreamEntryID id = jd.xadd("k", StreamEntryID.NEW_ENTRY, hash);
//			StreamEntryID id = jd.xadd("k", new StreamEntryID("1-1"), hash);
            System.out.println("发送流： xadd1 id:" + id.toString());
            Map<String, String> hash2 = new HashMap<>();
            hash2.put("name", "Jerry");
            hash2.put("age", "12");
            //key, id, hash, len, ~(false)
            StreamEntryID id2 = jd.xadd("k", StreamEntryID.NEW_ENTRY, hash2, 5, false);
            System.out.println("发送流： xadd2 id:" + id2.toString());
            System.out.println("发送流完成。。。");
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void xlen() {
        try(Jedis jd = JedisUtil.getJedisUtil().getJedis()) {
            long len = jd.xlen("k");
            System.out.println("接收流（key=k）len=" + len);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void xread() {
        try(Jedis jd = JedisUtil.getJedisUtil().getJedis()) {
            //count, block, key-id...
            List<Entry<String, List<StreamEntry>>> list = jd.xread(1, 1000, new MyJedisEntry("k", "0"), new MyJedisEntry("k", "0"));
            System.out.println("读取流\n"+list+"\n");
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void xrange() {
        try(Jedis jd = JedisUtil.getJedisUtil().getJedis()) {
            //key, start, end, count
            //null表示无穷小或者无穷大
            List<StreamEntry> list = jd.xrange("k", null, null, 100);
            System.out.println("读取流\nxrange:" + list);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void xrevrange() {
        try(Jedis jd = JedisUtil.getJedisUtil().getJedis()) {
            //key, end, start, count
            List<StreamEntry> list = jd.xrevrange("k", null, null, 100);
            System.out.println("读取流\nxrevrange:" + list);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void xdel() {
        try(Jedis jd = JedisUtil.getJedisUtil().getJedis()) {
            List<Entry<String, List<StreamEntry>>> getid = jd.xread(1, 0, new MyJedisEntry("k", "0"));
            Entry<String, List<StreamEntry>> k_entrylist = getid.get(0);
            List<StreamEntry> entrylist = k_entrylist.getValue();
            String id = entrylist.get(0).getID().toString();
            //key, id...
            long result = jd.xdel("k", new StreamEntryID(id));
            System.out.println("读取流\nxdel " + id + " return:" + result);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void xtrim() {
        try(Jedis jd = JedisUtil.getJedisUtil().getJedis()) {
            //key, len, ~(false)
            long result = jd.xtrim("k", 2, false);
            System.out.println("读取流\nxtrim 2 return=" + result);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}
class MyJedisEntry implements Entry<String, StreamEntryID>{
    private String k;
    private StreamEntryID id;
    public MyJedisEntry(String key, String id){
        this.k = key;
        if("0".equals(id)) {
            this.id = new StreamEntryID();
        }else {
            this.id = new StreamEntryID(id);
        }
    }
    public MyJedisEntry(String key, StreamEntryID ID) {
        this.k = key;
        this.id = ID;
    }
    @Override
    public String getKey() {
        return k;
    }
    @Override
    public StreamEntryID getValue() {
        return id;
    }
    @Override
    public StreamEntryID setValue(StreamEntryID value) {
        this.id = value;
        return id;
    }
}
