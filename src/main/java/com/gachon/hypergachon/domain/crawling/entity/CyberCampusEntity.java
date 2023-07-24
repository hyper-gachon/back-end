package com.gachon.hypergachon.domain.crawling.entity;

import com.gachon.hypergachon.domain.crawling.dto.CyberCampusDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CyberCampusEntity extends CyberCampusDto {
    private List<CyberCampusAssignment> assignments;
    private List<CyberCampusMOOC> moocs;
    private List<CyberCampusQuiz> quizzes;
    private List<CyberCampusAnnouncement> announcements;

    public CyberCampusEntity (String courseId, String link, String courseName) {
        this.setCourseId(courseId);
        this.setLink(link);
        this.setCourseName(courseName);
        assignments = new ArrayList<>();
        moocs = new ArrayList<>();
        quizzes = new ArrayList<>();
        announcements = new ArrayList<>();
    }

    /*
    과제: 과제이름, 시작일, 종료일, 제출여부, [채점여부]
    mooc강의: 강의이름, 시작일, 종료일, 시청여부
    퀴즈: 퀴즈이름, 시작일, 종료일, 완료여부
    공지사항: 제목, 작성자, 본문, 작성시각, 조회수
    */
    public static class CyberCampusAssignment {
        String name;
        String start, end;
        Boolean isSubmit;

        public CyberCampusAssignment(String name, String start, String end, Boolean isSubmit) {
            this.name = name;
            this.start = start;
            this.end = end;
            this.isSubmit = isSubmit;
        }
    }

    public static class CyberCampusMOOC {
        String name;
        String start, end;
        Boolean isWatched;

        public CyberCampusMOOC(String name, String start, String end, Boolean isWatched) {
            this.name = name;
            this.start = start;
            this.end = end;
            this.isWatched = isWatched;
        }
    }

    public static class CyberCampusQuiz {
        String name;
        String end;
        Boolean isFinish;

        public CyberCampusQuiz(String name, String end, Boolean isFinish) {
            this.name = name;
            this.end = end;
            this.isFinish = isFinish;
        }
    }

    public static class CyberCampusAnnouncement {
        String link;
        String title;
        String writer;
        String uploadTime;
        int hit;

        public CyberCampusAnnouncement(String link, String title, String writer, String uploadTime, int hit) {
            this.link = link;
            this.title = title;
            this.writer = writer;
            this.uploadTime = uploadTime;
            this.hit = hit;
        }
    }
}
