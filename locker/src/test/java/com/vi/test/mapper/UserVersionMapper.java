package com.vi.test.mapper;

import com.vi.test.entity.UserVersion;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserVersionMapper {

	// 参数为POJO对象方式(推荐使用方式1)
	Integer updateUser(UserVersion user);

	// 参数为单个参数方式(推荐使用方式2)
	Integer updateUser(@Param("name") String name, @Param("password") String password, @Param("myVersion") Long version, @Param("id") Integer id);

	// 参数为Map方式(不推荐使用方式)
	Integer updateUser(Map<Object, Object> user);

	// 单个参数未带@Param，报错(严重不推荐使用方式)
	Integer updateUserError(String name, String password, Long version, Integer id);

	// 重置数据库数据
	void resetData(UserVersion user);

	// 重置数据库数据
	void initData(UserVersion user);

	//批量更新
	int updateUserList(List<UserVersion> users);

}
