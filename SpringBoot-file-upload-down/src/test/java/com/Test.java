package com;

public class Test {
    public static void main(String[] args) {
        System.out.println("无异常测试开始！");
        try(Io io = new Io()){
            io.work();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("有异常测试开始！");
        try(Io io = new Io()){
            io.workThrowExcption();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

class Io implements AutoCloseable{
    void work(){
        System.out.println("工作中！");
    }

    void workThrowExcption(){
        System.out.println("工作中！产生异常！");
        throw new RuntimeException();

    }

    @Override
    public void close() throws Exception {
        System.out.println("自动调用关闭方法！");
    }
}
