package com.lyj.demo.test;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

@Component
public class Test {


    /**
     * 保存到user的concurrentHashMap中,key为传入的id(如果传入的参数是一个对象,那么也可以获取到对象的成员变量)
     * key可以使用单个值,也是通过字符串相加的形式拼接,如: @Cacheable(value = "user",key = "#id+','+#name")
     *
     * 先从缓存中获取,如果没有,则执行该方法获取,并保存到缓存中
     * @param id
     * @param name
     * @return
     */
    @Cacheable(value = "user",key = "#id+','+#name")
    public User select(int id,String name){
        System.out.println("查询");
        User user = new User(id, name);
        return user;
    }

    /**
     * 从concurrentHashMap中删除user,key为传入的id
     * 原则: 直接执行删除user的操作,删除成功之后,才会清除掉缓存中的user,按照传入的id作为key进行清除
     *      清除之后,缓存中就不存在key为传入为id的user对象,那么在再次执行获取user的方法时,
     *      会直接查询数据库,然后再将数据保存到缓存中
     * @param id
     */
    @CacheEvict(value = "user",key = "#id")
    public void delete(int id) {
        System.out.println("删除");
        System.out.println(new User(id,null));
    }

    /**
     * 先执行save方法,如果成功,那么将返回的user缓存
     * 如果失败,则不缓存
     * 如果执行成功,该注解不会查询缓存中是否存在,而是会直接覆盖掉缓存中的数据
     * @param id
     * @param name
     * @return
     */
    @CachePut(value = "user",key = "#id")
    public User save(int id, String name){
        System.out.println("保存");
        User user = new User(id, name);
        return user;
    }

    /**
     * 复杂的缓存规则:
     * 先从user缓存中根据id查找,或从user1缓存中根据name查找,如果找到了,则直接返回
     * 如果没找到,则执行该方法
     * 如有该方法执行成功,则将返回值的id当做key缓存到user缓存中和将返回值的name当做key缓存到user1缓存中
     * @param id
     * @param name
     * @return
     */
    @Caching(
            cacheable = {
                    @Cacheable(value = "user",key = "#id"),
                    @Cacheable(value = "user1",key = "#name")
            },
            put = {
                    @CachePut(value = "user",key = "#result.id"),
                    @CachePut(value = "user1",key = "#result.name")
            }
    )
    public User test2(int id,String name){
        System.out.println("复杂的缓存规则");
        User user = new User(id, name);
        return user;
    }

}
