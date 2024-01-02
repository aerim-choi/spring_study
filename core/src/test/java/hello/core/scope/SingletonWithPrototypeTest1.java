package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        Assertions.assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        Assertions.assertThat(prototypeBean2.getCount()).isEqualTo(1);

    }
    @Test
    void singletonClientUsePrototype(){
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(PrototypeBean.class,ClientBean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1= clientBean1.logic();
        Assertions.assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2= clientBean2.logic();
        Assertions.assertThat(count2).isEqualTo(2);


    }
    @Scope("singleton")
    static class ClientBean{
        private final PrototypeBean prototypeBean;//생성시점에 주입되어버림(그래서 계속 같은 빈을 사용하게됨)
        @Autowired
        public ClientBean(PrototypeBean prototypeBean){
            this.prototypeBean=prototypeBean;
        }

//        @Autowired
//        ApplicationContext applicationContext; //굉장히 지저분한 코드가 된다.

        public int logic(){
//            PrototypeBean prototypeBean= applicationContext.getBean(PrototypeBean.class);
            prototypeBean.addCount();
            int count=prototypeBean.getCount();
            return count;
        }


    }
    @Scope("prototype")
    static class PrototypeBean{
        private int count = 0;
        public void addCount(){
            count++;
        }
        public int getCount(){
            return count;
        }
        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init" + this);
        }
        @PreDestroy
        public void destroy(){ //호출 안됨 prototype 스코프니까
            System.out.println("PrototypeBean.destroy");
        }
    }
}
