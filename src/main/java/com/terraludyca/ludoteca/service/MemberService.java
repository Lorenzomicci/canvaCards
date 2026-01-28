package com.terraludyca.ludoteca.service;

import com.terraludyca.ludoteca.model.Member;
import com.terraludyca.ludoteca.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for loading and filtering members.
 */
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * Builds the service with the configured repository.
     */
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * Returns all members retrieved from Google Sheets.
     */
    public List<Member> getMembers() throws IOException {
        return memberRepository.findAll();
    }

    /**
     * Returns members whose subscription year matches the given year.
     */
    public List<Member> getMembersByYear(int year) throws IOException {
        return getMembers().stream()
                .filter(member -> member.getSubscriptionYear() == year)
                .collect(Collectors.toList());
    }
}
