package com.vi.test.entity;

import java.io.Serializable;

/**
 * 自定义乐观锁
 */
public class UserVersion implements Serializable {

    private Integer id;
    private String name;
    private String password;
    private Long myVersion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getMyVersion() {
        return myVersion;
    }

    public void setMyVersion(Long myVersion) {
        this.myVersion = myVersion;
    }

    @Override
    public String toString() {
        return "UserVersion{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", myVersion=" + myVersion +
                '}';
    }
}
