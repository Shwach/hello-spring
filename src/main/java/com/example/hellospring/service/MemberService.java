package com.example.hellospring.service;

import com.example.hellospring.domain.Member;
import com.example.hellospring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {
    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    public Long join(Member member){

        long start = System.currentTimeMillis();

        //중복회원 검증

       try{
        validateDuplicateMember(member, memberRepository);
        memberRepository.save(member);
        return member.getId();
    } finally {
           long finish = System.currentTimeMillis();
           long timeMs = finish - start;
           System.out.println("join" + timeMs + "ms");
       }
       }

    private static void validateDuplicateMember(Member member, MemberRepository memberRepository) {
        memberRepository.findByName(member.getName())
                .ifPresent(m1 -> {throw new IllegalStateException("이미 존재하는 회원입니다.");});
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();

    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

}
