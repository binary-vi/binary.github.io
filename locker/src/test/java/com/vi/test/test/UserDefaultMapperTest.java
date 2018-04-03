package com.vi.test.test;

import com.vi.test.entity.UserDefault;
import com.vi.test.mapper.UserDefaultMapper;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.*;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class UserDefaultMapperTest {

    private static SqlSession sqlSession = null;
    private UserDefault user = new UserDefault();
    private static UserDefaultMapper userDefaultMapper;

    @BeforeClass
    public static void doInitTest() throws Exception {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sqlSessionFactory.openSession(true);
        userDefaultMapper = sqlSession.getMapper(UserDefaultMapper.class);

    }

    //初始化数据
    @Before
    public void initPojo() throws Exception {
        user.setId(1);
        user.setName("张三");
        user.setPassword("123456");
        user.setVersion(100L);
        userDefaultMapper.initData(user);
    }

    @After
    public void resetDatabaseTest() {
        user.setId(1);
        userDefaultMapper.resetData(user);
    }

    @Test
    public void updateUserPojoTest() {
        user.setName("张三三");
        user.setPassword("654321");
        Integer result = userDefaultMapper.updateUser(user);
        Assert.assertEquals(1L, Long.parseLong(result + ""));

//
    }

    @Test
    public void updateUserAtParamTest() {
        Integer result = userDefaultMapper.updateUser("张三三", "654321", 100L, 1);
        Assert.assertEquals(1L, Long.parseLong(result + ""));
    }

    @Test
    public void updateUserMapTest() {
        Map<Object, Object> param = new HashMap<>();
        param.put("name", "test");
        param.put("password", "test");
        param.put("version", 100L);
        param.put("id", 1);
        Integer result = userDefaultMapper.updateUser(param);
        Assert.assertEquals(1L, Long.parseLong(result + ""));
    }

	@Test(expected = BindingException.class)
	public void updateUserErrorTest() {
		Integer result = userDefaultMapper.updateUserError("test", "test", 100L, 1);
		Assert.assertEquals(1L, Long.parseLong(result + ""));
	}



}
