package com.example.demo.service;

import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.entity.Address;
import com.example.demo.repository.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;



//    @PostConstruct
//    public void makeMember(){
//        Member member1 = new Member();
//        member1.setName("name1");
//        member1.setAddress(new Address("서울","사당","1111"));
//
//        Member member2 = new Member();
//        member2.setName("name2");
//        member2.setAddress(new Address("부산","동구","323"));
//
//        List<Member> memberList = new ArrayList<>();
//        memberList.add(member1);
//        memberList.add(member2);
//
//        memberRepository.saveAll(memberList);
//    }


    public Long join(Member member){
        Member newMember = memberRepository.save(member);
        return newMember.getId();
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findById(id)
                .orElseThrow(RuntimeException::new);
        member.setName(name);
    }

    public Member findOne(Long id) {
        return memberRepository.findById(id).orElse(new Member());
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
}
