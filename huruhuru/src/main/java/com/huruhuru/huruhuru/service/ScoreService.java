package com.huruhuru.huruhuru.service;

import com.huruhuru.huruhuru.repository.ScoreJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScoreService {

    private final ScoreJpaRepository scoreJpaRepository;


}
