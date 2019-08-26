package com.hrd.somchbab.service.classenroll_service;

import com.hrd.somchbab.repository.model.Classenroll;

public interface ClassenrollService {
    Integer findIdByUserIdAndClassroomId(int userId, int classroomId);

    void enrollStudent(Classenroll classenroll);
}
