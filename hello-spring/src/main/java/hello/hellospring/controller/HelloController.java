package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    //정적 컨텐츠
    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data","hello!!");
        return "hello"; //templates/hello.html로 이동
    }
    //템플릿엔진:템플릿을 mvc로 쪼개서 렌더링이된 html을 클라이언트에 전달해준다.
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value="name")String name,Model model){
        model.addAttribute("name",name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name")String name){
        return "hello"+name;
    }

    //api: 객체를 반환
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name")String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
        //반환값으로 객체가 오면 기본 디폴트는 json으로 만들어서 http응답으로 반환하겠다가 정책이다.
    }

    static class Hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
