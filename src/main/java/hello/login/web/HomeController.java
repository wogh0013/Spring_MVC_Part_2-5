package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

//    @GetMapping("/")
    public String home() {
        return "home";
    }

    //required = false -> 로그인하지 않은 사용자도 홈에 접근할 수 있음
    @GetMapping("/")
    public String loginHome(@CookieValue(name = "memberId", required = false) Long memberId, Model model){

        //로그인 쿠키 없으면 기존 home으로
        if(memberId == null){
            return "home";
        }

        //로그인 성공 로직(쿠키 보유)
        Member loginMember = memberRepository.findById(memberId);
        //로그인 쿠키가 있어도, 회원이 없으면 기존 home으로
        if(loginMember == null){
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}