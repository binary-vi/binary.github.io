package com.vi.test.test;

import com.vi.test.entity.UserVersion;
import com.vi.test.mapper.UserVersionMapper;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserVersionMapperTest {

	private static SqlSession sqlSession = null;
	private UserVersion user = new UserVersion();
	private static UserVersionMapper userVersionMapper = null;

	@BeforeClass
	public static void doInitTest() throws Exception {
		InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		sqlSession = sqlSessionFactory.openSession(true);
		userVersionMapper = sqlSession.getMapper(UserVersionMapper.class);
	}

	// 每次测试前都将数据库中的id为100的User的version设置成100
	@Before
	public void initPojo() throws Exception {
		user.setId(1);
		user.setName("李四");
		user.setPassword("123456");
		user.setMyVersion(1L);
		userVersionMapper.initData(user);
	}

	@After
	public void resetDatabaseTest() {
		user.setId(1);
		userVersionMapper.resetData(user);
	}

	@Test
	public void updateUserPojoTest() {
		user.setName("李四四");
		Integer result = userVersionMapper.updateUser(user);
		Assert.assertEquals(1L, Long.parseLong(result + ""));
	}

	@Test
	public void updateUserAtParamTest() {
		Integer result = userVersionMapper.updateUser("李四四", "pass", 1024L, 1);
		Assert.assertEquals(1L, Long.parseLong(result + ""));
	}


	@Test
	public void updateUserMapTest() {
		Map<Object, Object> param = new HashMap<>();
		param.put("name", "test");
		param.put("password", "test");
		param.put("myVersion", 1024L);
		param.put("id", 1);
		UserVersionMapper userMapper = sqlSession.getMapper(UserVersionMapper.class);
		Integer result = userMapper.updateUser(param);
		Assert.assertEquals(1L, Long.parseLong(result + ""));

	}

	@Test(expected = BindingException.class)
	public void updateUserErrorTest() {
		UserVersionMapper userMapper = sqlSession.getMapper(UserVersionMapper.class);
		Integer result = userMapper.updateUserError("test", "test", 100L, 1);
		Assert.assertEquals(1L, Long.parseLong(result + ""));
	}

	@Test
	public void updateUserListTest() {
		List<UserVersion> userlist = new ArrayList<UserVersion>();

		UserVersion user2 = new UserVersion();
		user2.setId(2);
		user2.setName("批量更新");
		user2.setPassword("test");
		user2.setMyVersion(1L);
		userVersionMapper.initData(user2);

		user.setName("第一条批量更新");
		user2.setName("第二条批量更新");
		userlist.add(user);
		userlist.add(user2);

		Integer result = userVersionMapper.updateUserList(userlist);
		Assert.assertEquals(2L, Long.parseLong(result + ""));

	}



}
