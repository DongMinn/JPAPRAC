package com.example.demo.api;

import com.example.demo.api.request.CreateMemberRequest;
import com.example.demo.api.request.UpdateMemberRequest;
import com.example.demo.api.response.CreateMemberResponse;
import com.example.demo.api.response.MemberDto;
import com.example.demo.api.response.Result;
import com.example.demo.api.response.UpdateMemberResponse;
import com.example.demo.repository.entity.Member;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;


    /*
    이렇게 직접 엔티티를 바디로 받는 경우에는 엔티티가 변경이 일어 날 경우 혹은
    member를 저장하는 여러가지 방법이 존재하게 될 경우 유연성을 잃게 만든다.
    하여 보통은  DTO객체를 새롭게 만들어 그 것으로 받아온 데이터를 이용해 멤버를 저장하는것이 좋다.
     */
    @PostMapping("/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    /*
    이렇게 리퀘스트 요청에 맞는 DTO를 활용하는 것이 훨씬 유연한 코드 작성이 가능하게 된다.
    스펙도 확실히 알 수 있고.
    * */
    @PostMapping("/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest member) {
        Member tmpMember = new Member();
        tmpMember.setName(member.getName());
        Long id = memberService.join(tmpMember);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/v1/members/{id}")
    public UpdateMemberResponse modifyMemberV1(@PathVariable("id") Long id
            , @RequestBody @Valid UpdateMemberRequest updateMemberRequest) {

        memberService.update(id, updateMemberRequest.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());

    }

    /*
    역시 마찬가지로 이렇게 엔티티를 직접 리턴하게 될경우
    외부에 불필요한 데이터까지 다 노출되게 된다.
    역시 마찬가지로 DTO 로 만들어서 리턴하는 편이 더 유연한 개발이 가능하게 만든다.
     */
    @GetMapping("/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @GetMapping("/v2/members")
    public Result memberV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect =  findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());
        return new Result((long) collect.size(),collect);
    }


}
