package com.markruler.datajpa.repository;

import com.markruler.datajpa.entity.Member;

import javax.persistence.EntityManager;
import java.util.List;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final EntityManager em;

    public MemberRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Member> findMemberCustom() {
        return em
                .createQuery("select m from Member m")
                .getResultList();
    }
}
