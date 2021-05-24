package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {
    private MemberService memberService;

//    @Autowired //setter주입
//    public void setMemberService(MemberService memberService) {
//        this.memberService = memberService;
//    }

    @Autowired//결국 생성자 주입이 가장 좋다.. 동적으로 변경되는 경우가 거의 없으니까
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }//지금은 어떤 데이터베이스를 쓸 지 몰라서 Memory를 쓰고 있지만, 나중에 결정되면 한번에 쫙 바꿔주면 됨.
}
