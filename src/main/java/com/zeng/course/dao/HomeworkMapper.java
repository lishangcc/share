package com.zeng.course.dao;


import com.zeng.course.model.Course;
import com.zeng.course.model.File_download;
import com.zeng.course.model.Homework;
import com.zeng.course.model.Homework_upload;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface HomeworkMapper {

    List<Homework> selectHomeworkByCourseId(Integer CourseId);

    int insertHomework(Map map);

    /**
     * 更新作业
     * @param map
     * @return
     */
    int updateHomework(Map map);

    /**
     * 删除作业
     * @param map
     * @return
     */
    int deleteHomework(Map map);



    List<Homework_upload> selectHomeworkUploadById(String homeworkId);

    void uploadHomework(@Param("homeworkId") Integer homeworkId,@Param("studentId") Integer studentId,@Param("pathName") String pathName);

    Homework_upload selectHomeworkUpload(@Param("homeworkId") Integer homeworkId, @Param("studentId") Integer studentId);

    Homework_upload selectHomeworkUploadByhomeworkUploadIdAndTeacherId(@Param("homeworkUploadId") String homeworkUploadId,@Param("teacherId") Integer teacherId);

    List<Homework_upload> selectHomeworkUploadByStudentId(int studentId);

    List<Homework_upload> selectUploadsByCourseId(Integer courseId);

    void updateScore(@Param("id") Integer id, @Param("score") String score);
}