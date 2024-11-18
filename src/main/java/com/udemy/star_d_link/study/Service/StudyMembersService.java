package com.udemy.star_d_link.study.Service;

import com.udemy.star_d_link.study.Dto.Response.StudyMemberResponseDto;
import com.udemy.star_d_link.study.Entity.Role;
import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Entity.StudyMembers;
import com.udemy.star_d_link.study.Entity.User;
import com.udemy.star_d_link.study.Exception.UnauthorizedException;
import com.udemy.star_d_link.study.Mapper.StudyMembersMapper;
import com.udemy.star_d_link.study.Repository.StudyMemberRepository;
import com.udemy.star_d_link.study.Repository.StudyRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudyMembersService {
    private final StudyMemberRepository studyMemberRepository;
    private final StudyRepository studyRepository;
    private final StudyMembersMapper studyMembersMapper;
    @Autowired
    public StudyMembersService(StudyMemberRepository studyMemberRepository, StudyRepository studyRepository, StudyMembersMapper studyMembersMapper) {
        this.studyMemberRepository = studyMemberRepository;
        this.studyRepository = studyRepository;
        this.studyMembersMapper = studyMembersMapper;
    }

    @Transactional
    public StudyMemberResponseDto applyStudy(Long studyId, User user) {
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new NoSuchElementException("해당 스터디를 찾을 수 없습니다: "));

        boolean alreadyApplied = studyMemberRepository.existsByUserAndStudy(user, study);
        if (alreadyApplied) {
            throw new IllegalArgumentException("이미 스터디에 신청하셨습니다.");
        }

        Role role = study.getUser().equals(user) ? Role.LEADER : Role.MEMBER;

        StudyMembers studyMember = studyMembersMapper.toEntity(user, study, role);
        StudyMembers saveMember = studyMemberRepository.save(studyMember);

        return studyMembersMapper.toDto(saveMember);
    }

    public Page<StudyMemberResponseDto> getMemberList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        Page<StudyMembers> studyPage = studyMemberRepository.findAll(pageable);

        return studyPage.map(studyMembersMapper::toDto);
    }

    public StudyMemberResponseDto acceptMember(Long studyId, User proposer) {
        StudyMembers member = studyMemberRepository.findByUser(proposer)
            .orElseThrow(() -> new NoSuchElementException("해당 멤버를 찾을 수 없습니다: "));

        if(!member.getStudy().getStudyId().equals(studyId)) {
            throw new IllegalArgumentException("해당 스터디에 속하지 않는 멤버입니다.");
        }

        studyMembersMapper.updateStatusToActive(member, member);

        StudyMembers saveMember = studyMemberRepository.save(member);

        return studyMembersMapper.toDto(saveMember);
    }

    public void rejectMember(Long studyId, User user) {
        StudyMembers member = studyMemberRepository.findByUser(user)
            .orElseThrow(() -> new NoSuchElementException("해당 멤버를 찾을 수 없습니다: "));

        if(!member.getStudy().getStudyId().equals(studyId)) {
            throw new IllegalArgumentException("해당 스터디에 속하지 않는 멤버입니다.");
        }

        studyMemberRepository.delete(member);
    }

    public StudyMembers changeMemberRole(Long studyId, User user, Role newRole, User member) {
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new NoSuchElementException("해당 스터디를 찾을 수 없습니다: "));

        StudyMembers currentMember = studyMemberRepository.findByUserAndStudy(user, study)
            .orElseThrow(() -> new UnauthorizedException("권한이 없습니다."));

        if (currentMember.getRole() != Role.LEADER && currentMember.getRole() != Role.SUB_LEADER) {
            throw new UnauthorizedException("멤버 역할을 변경할 권한이 없습니다.");
        }

        StudyMembers targetMember = studyMemberRepository.findByUserAndStudy(member, study)
            .orElseThrow(() -> new NoSuchElementException("해당 멤버를 찾을 수 없습니다."));

        targetMember.setRole(newRole);
        return studyMemberRepository.save(targetMember);
    }

    public boolean hasPermission(Long studyId, User user) {

        Study study = studyRepository.findByStudyId(studyId)
            .orElseThrow(() -> new NoSuchElementException("해당 스터디를 찾을 수 없습니다"));

        return study.getUser().equals(user);
    }

    public boolean isMemberOfStudy(Long studyId, User user) {

        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new NoSuchElementException("해당 스터디를 찾을 수 없습니다: "));

        Optional<StudyMembers> studyMember = studyMemberRepository.findByStudyAndUserAndStatusNot(study, user, "대기중");

        return studyMember.isPresent();
    }
}
