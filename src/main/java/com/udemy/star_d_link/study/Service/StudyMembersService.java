package com.udemy.star_d_link.study.Service;

import com.udemy.star_d_link.study.Dto.Response.StudyMemberResponseDto;
import com.udemy.star_d_link.study.Entity.Role;
import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Entity.StudyMembers;
import com.udemy.star_d_link.study.Exception.UnauthorizedException;
import com.udemy.star_d_link.study.Mapper.StudyMembersMapper;
import com.udemy.star_d_link.study.Repository.StudyMemberRepository;
import com.udemy.star_d_link.study.Repository.StudyRepository;
import java.util.NoSuchElementException;
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

    @Autowired
    public StudyMembersService(StudyMemberRepository studyMemberRepository, StudyRepository studyRepository) {
        this.studyMemberRepository = studyMemberRepository;
        this.studyRepository = studyRepository;
    }

    @Transactional
    public StudyMemberResponseDto applyStudy(Long studyId, Long userId) {
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new NoSuchElementException("해당 스터디를 찾을 수 없습니다: "));

        boolean alreadyApplied = studyMemberRepository.existsByUserIdAndStudy(userId, study);
        if (alreadyApplied) {
            throw new IllegalArgumentException("이미 스터디에 신청하셨습니다.");
        }

        String role = study.getUserId().equals(userId) ? "팀장" : "팀원";

        StudyMembers studyMember = StudyMembersMapper.toEntity(userId, study, role);
        StudyMembers saveMember = studyMemberRepository.save(studyMember);

        return StudyMembersMapper.toResponseDto(saveMember);
    }

    public Page<StudyMemberResponseDto> getMemberList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        Page<StudyMembers> studyPage = studyMemberRepository.findAll(pageable);

        return studyPage.map(StudyMembersMapper::toResponseDto);
    }

    public StudyMemberResponseDto acceptMember(Long studyId, Long userId) {
        StudyMembers member = studyMemberRepository.findById(userId)
            .orElseThrow(() -> new NoSuchElementException("해당 멤버를 찾을 수 없습니다: "));

        if(!member.getStudy().getStudyId().equals(studyId)) {
            throw new IllegalArgumentException("해당 스터디에 속하지 않는 멤버입니다.");
        }

        StudyMembers updateMember = StudyMembersMapper.updateStatusToActive(member);

        StudyMembers saveMember = studyMemberRepository.save(updateMember);

        return StudyMembersMapper.toResponseDto(saveMember);
    }

    public void rejectMember(Long studyId, Long userId) {
        StudyMembers member = studyMemberRepository.findById(userId)
            .orElseThrow(() -> new NoSuchElementException("해당 멤버를 찾을 수 없습니다: "));

        if(!member.getStudy().getStudyId().equals(studyId)) {
            throw new IllegalArgumentException("해당 스터디에 속하지 않는 멤버입니다.");
        }

        studyMemberRepository.delete(member);
    }

    public StudyMembers changeMemberRole(Long studyId, Long userId, Role newRole, Long currentUserId) {
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new NoSuchElementException("해당 스터디를 찾을 수 없습니다: "));

        StudyMembers currentMember = studyMemberRepository.findByUserIdAndStudy(currentUserId, study)
            .orElseThrow(() -> new UnauthorizedException("권한이 없습니다."));

        if (currentMember.getRole() != Role.LEADER && currentMember.getRole() != Role.SUB_LEADER) {
            throw new UnauthorizedException("멤버 역할을 변경할 권한이 없습니다.");
        }

        StudyMembers targetMember = studyMemberRepository.findByUserIdAndStudy(userId, study)
            .orElseThrow(() -> new NoSuchElementException("해당 멤버를 찾을 수 없습니다."));

        targetMember.setRole(newRole);
        return studyMemberRepository.save(targetMember);
    }

    public boolean hasPermission(Long studyId, Long userId) {

        Study study = studyRepository.findByStudyId(studyId)
            .orElseThrow(() -> new NoSuchElementException("해당 스터디를 찾을 수 없습니다"));

        return study.getUserId().equals(userId);
    }
}
