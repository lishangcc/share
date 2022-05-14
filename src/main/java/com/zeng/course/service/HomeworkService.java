package com.zeng.course.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zeng.course.dao.CourseMapper;
import com.zeng.course.dao.HomeworkMapper;
import com.zeng.course.model.Course;
import com.zeng.course.model.File_download;
import com.zeng.course.model.Homework;
import com.zeng.course.model.Homework_upload;
import com.zeng.course.util.PageRequest;
import com.zeng.course.util.PageResult;
import com.zeng.course.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

@Service
public class HomeworkService {
	@Resource
	private HomeworkMapper homeworkMapper;


	public List<Homework> selectHomeworkByCurseId(Integer courseId) {
		return homeworkMapper.selectHomeworkByCourseId(courseId);
	}

	public int insertHomework(Map map){
		Map resmap=new HashMap();
		resmap.put("name",map.get("name"));
		resmap.put("courseId",map.get("courseId"));
		resmap.put("intro",map.get("intro"));
		return homeworkMapper.insertHomework(resmap);
	}

	public void uploadHomework(Integer homeworkId, Integer id, String pathName) {
		homeworkMapper.uploadHomework(homeworkId,id,pathName);
	}

	public Homework_upload selectHomeworkUpload(Integer homeworkId, Integer studentId) {
		return homeworkMapper.selectHomeworkUpload(homeworkId, studentId);
	}

	public PageResult selectHomeworkUploads(int pageNum,int pageSize,int studentId){
		PageHelper.startPage(pageNum, pageSize);
		List<Homework_upload> sysMenus = homeworkMapper.selectHomeworkUploadByStudentId(studentId);
		PageInfo<Homework_upload> pageInfo=new PageInfo<>(sysMenus);
		return PageUtils.getPageResult(pageInfo);
	}

	public PageResult selectHomeworkUploadsByCurseIds(int pageNum,int pageSize,List<Integer> courseIds) {
		PageHelper.startPage(pageNum, pageSize);
		List<Homework_upload> sysMenus=new ArrayList<>();
		for(Integer courseId:courseIds){
			List<Homework_upload> homeworkList = homeworkMapper.selectUploadsByCourseId(courseId);
			sysMenus.addAll(homeworkList);
		}
		PageInfo<Homework_upload> pageInfo=new PageInfo<>(sysMenus);
		return PageUtils.getPageResult(pageInfo);
	}

	public void editScore(Integer id, String score) {
		homeworkMapper.updateScore(id,score);
	}

	/**
	 * 更新课程作业
	 * @param map
	 * @return
	 */
	public boolean updateHomeWork(Map map){
		//更新数据库
		int line = homeworkMapper.updateHomework(map);
		return line==1;
	}

	/**
	 * 删除作业 注：现在并不能保证存储的相应作业的文件被删除
	 * @param map
	 * @return
	 */
	public boolean deleteHomework(Map map){
		if(deleteHomeworkUpload((String)map.get("homeworkId"))) {
			return 1 == homeworkMapper.deleteHomework(map);
		}
		return false;
	}

	/**
	 * 删除指定作业的相应资源
	 * @param homeworkId
	 * @return
	 */
	public boolean deleteHomeworkUpload(String homeworkId){
		List<Homework_upload> list=homeworkMapper.selectHomeworkUploadById(homeworkId);
		//删除作业文件
		for(Homework_upload source:list){
			try{
				File file=new File(source.getPath());
				if(file!=null&&file.exists()){
					file.delete();
					System.out.println("delete "+source.getPath()+" success");
				}
			}catch (Exception e) {
				System.out.println("delete "+source.getPath()+" failed");
			}
		}
		return true;
	}

	/**
	 * 根据教师id与homeworkId获取作业文件路径
	 * @param map
	 * @return
	 */
	public Homework_upload getFilePathByTeacherIdAndHomeworkUploadId(Map map){
		String uploadId=(String)map.get("homeworkUploadId");
		int teacherId=(Integer)map.get("teacherId");
		Homework_upload upload= homeworkMapper.selectHomeworkUploadByhomeworkUploadIdAndTeacherId(uploadId,teacherId);
		if(null!=upload){
			return upload;
		}
		return null;
	}
}
