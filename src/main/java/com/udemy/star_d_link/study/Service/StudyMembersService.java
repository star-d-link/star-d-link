package com.udemy.star_d_link.study.Service;

import com.udemy.star_d_link.study.Entity.Role;
import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Entity.StudyMembers;
import com.udemy.star_d_link.study.Exception.UnauthorizedException;
import com.udemy.star_d_link.study.Repository.StudyMemberRepository;
import com.udemy.star_d_link.study.Repository.StudyRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudyMembersService {
    private final StudyMemberRepository studyMemberRepository;
    private final StudyRepository studyRepository;
    @Autowired
    public StudyMembersService(StudyMemberRepository studyMemberRepository, StudyRepository studyRepository) {
        this.studyMemberRepository = studyMemberRepository;
        this.studyRepository = studyRepository;
    }

    @Transactional
    public StudyMembers applyStudy(Long studyId, String username) {
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new NoSuchElementException("해당 스터디를 찾을 수 없습니다: "));

        boolean alreadyApplied = studyMemberRepository.existsByUsernameAndStudy(username, study);
        if (alreadyApplied) {
            throw new IllegalArgumentException("이미 스터디에 신청하셨습니다.");
        }

        Role role = study.getUsername().equals(username) ? Role.LEADER : Role.MEMBER;
        StudyMembers studyMember = new StudyMembers(username, study, role, "대기중");
        return studyMemberRepository.save(studyMember);
    }

    @Transactional(readOnly = true)
    public Map<String, List<StudyMembers>> getMemberList(Long studyId) {
        // 특정 스터디 ID로 멤버 전체 조회
        List<StudyMembers> allMembers = studyMemberRepository.findByStudy_StudyId(studyId);

        // 역할(Role)을 기준으로 관리하는 멤버와 일반 멤버 구분
        List<StudyMembers> adminMembers = allMembers.stream()
            .filter(member -> List.of(Role.LEADER, Role.SUB_LEADER).contains(member.getRole()))
            .collect(Collectors.toList());

        List<StudyMembers> joinedMembers = allMembers.stream()
            .filter(member -> !List.of(Role.LEADER, Role.SUB_LEADER).contains(member.getRole()))
            .collect(Collectors.toList());

        // Map으로 반환
        Map<String, List<StudyMembers>> result = new HashMap<>();
        result.put("admin", adminMembers);
        result.put("joined", joinedMembers);

        return result;
    }


    @Transactional
    public StudyMembers acceptMember(Long studyId, Long memberId) {
        StudyMembers member = studyMemberRepository.findById(memberId)
            .orElseThrow(() -> new NoSuchElementException("해당 멤버를 찾을 수 없습니다"));

        if (!member.getStudy().getStudyId().equals(studyId)) {
            throw new IllegalArgumentException("해당 스터디에 속하지 않는 멤버입니다.");
        }

        member = member.toBuilder()
            .status("참여중")
            .build();

        return studyMemberRepository.save(member);
    }

    @Transactional
    public void rejectMember(Long studyId, Long memberId) {
        StudyMembers member = studyMemberRepository.findById(memberId)
            .orElseThrow(() -> new NoSuchElementException("해당 멤버를 찾을 수 없습니다: " + memberId));

        if (!member.getStudy().getStudyId().equals(studyId)) {
            throw new IllegalArgumentException("해당 스터디에 속하지 않는 멤버입니다.");
        }
        studyMemberRepository.delete(member);
    }

    public StudyMembers changeMemberRole(Long studyId, String username, Role newRole, String memberName) {
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new NoSuchElementException("해당 스터디를 찾을 수 없습니다: "));

        StudyMembers currentMember = studyMemberRepository.findByUsernameAndStudy(username, study)
            .orElseThrow(() -> new UnauthorizedException("권한이 없습니다."));

        if (currentMember.getRole() != Role.LEADER && currentMember.getRole() != Role.SUB_LEADER) {
            throw new UnauthorizedException("멤버 역할을 변경할 권한이 없습니다.");
        }

        StudyMembers targetMember = studyMemberRepository.findByUsernameAndStudy(memberName, study)
            .orElseThrow(() -> new NoSuchElementException("해당 멤버를 찾을 수 없습니다."));

        targetMember = targetMember.toBuilder()
            .role(newRole)
            .build();
        return studyMemberRepository.save(targetMember);
    }

    public boolean hasPermission(Long studyId, String username) {

        Study study = studyRepository.findByStudyId(studyId)
            .orElseThrow(() -> new NoSuchElementException("해당 스터디를 찾을 수 없습니다"));

        return study.getUsername().equals(username);
    }

    public boolean hasManagementPermission(Long studyId, String username) {
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new NoSuchElementException("해당 스터디를 찾을 수 없습니다"));

        StudyMembers studyMember = studyMemberRepository.findByUsernameAndStudy(username, study)
            .orElseThrow(() -> new NoSuchElementException("해당 스터디에 사용자가 가입되어 있지 않습니다."));

        // 사용자가 리더 또는 부리더인지 확인
        return studyMember.getRole() == Role.LEADER || studyMember.getRole() == Role.SUB_LEADER;
    }

    public boolean isMemberOfStudy(Long studyId, String username) {

        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new NoSuchElementException("해당 스터디를 찾을 수 없습니다: "));

        Optional<StudyMembers> studyMember = studyMemberRepository.findByStudyAndUsernameAndStatusNot(study, username, "대기중");

        return studyMember.isPresent();
    }
}
