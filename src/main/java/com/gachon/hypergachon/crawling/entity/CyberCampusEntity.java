package com.gachon.hypergachon.crawling.entity;

import com.gachon.hypergachon.crawling.dto.CyberCampusDto;

public class CyberCampusEntity extends CyberCampusDto {

    public CyberCampusEntity (String courseId, String link, String courseName) {
        this.setCourseId(courseId);
        this.setLink(link);
        this.setCourseName(courseName);
    }

    public class CyberCampusAssignment {

    }

    public class CyberCampusMOOC {

    }

    public class CyberCampusQuiz {

    }

    public class CyberCampusAnnouncement {

    }
}
