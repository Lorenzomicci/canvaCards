package com.terraludyca.ludoteca.repository;

import com.terraludyca.ludoteca.model.Member;

import java.io.IOException;
import java.util.List;

/**
 * Repository abstraction for loading ludoteca members.
 */
public interface MemberRepository {

    /**
     * Loads all available members from the backing store.
     */
    List<Member> findAll() throws IOException;
}
