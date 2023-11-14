package com.huruhuru.huruhuru.service;

import com.huruhuru.huruhuru.domain.entity.MemberEntity;
import com.huruhuru.huruhuru.repository.MemberJpaRepository;
import com.huruhuru.huruhuru.repository.ScoreJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScoreService {

}
