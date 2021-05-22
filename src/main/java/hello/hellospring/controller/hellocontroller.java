package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class hellocontroller {

    @GetMapping("hello")
    public String hello(Model model){ //모델을 넘겨주는거
        model.addAttribute("data","Hello!!");
        //모델에 attribute추가
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model){
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name){
        return "hello" + name; //"hello spring"
    }
    //데이터를 그대로 내려준다... 근데 이런건 많이 쓰지 않아

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;//객체를 넘긴거야 name으로 설정하고. 그리고 이게 jason 방식으로 key : name, name: input_str로 되는거야
        //결국 그냥 객체를 return한다는 것은 json으로 http body에 내려준다는 느낌
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
