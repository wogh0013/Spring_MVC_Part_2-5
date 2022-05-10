package hello.login.domain.login;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    /**
     * @return null -> 로그인 실패
     */

    //회원을 조회 -> 파라미터로 넘어온 password와 비교 -> 같으면 회원 반환, 다르면 null 반환
    public Member login(String loginId, String password) {
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);

//        Optional<Member> findMemberOptional = memberRepository.findByLoginId(loginId);
//        Member member = findMemberOptional.get(); //Optional의 get 기능 -> 객체 반환
//        if (member.getPassword().equals(password)) {
//            return member;
//        } else {
//            return null;
//        }

//        Optional<Member> byLoginId = memberRepository.findByLoginId(loginId);
//        byLoginId.filter(m -> m.getPassword().equals(password))
//                .orElse(null);
    }
}
