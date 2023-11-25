package hello.core.singleton;

public class SingletonService {
    //  자기자신 객체를 생성 , 자바가 실행될 때 static final은 static영역에 객체 하나만 만들어짐
    private static final SingletonService instance  = new SingletonService();

    //이 객체 인스턴스가 필요하면 getInstance()메소드를 통해서만 조회될 수 있다.
    //이 메서드를 호출하면 항상 같은 인스턴스를 반환한다.
    public static SingletonService getInstance() {
        return instance;
    }
    //private 생성자를 만든다. 딱 1개의 객체 인스턴스만 존재해야하므로
    // 다른곳에서 생성되지 못하도록 private로 막는다.
    private SingletonService(){

    }
    public void login(){
        System.out.println("싱글톤 객체 로직 호출");
    }
}
