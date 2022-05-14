package com.zeng.course.controller.teacher;

//import com.sun.deploy.net.HttpResponse;
import com.zeng.course.model.*;
import com.zeng.course.service.*;
import com.zeng.course.util.PageResult;
//import org.omg.PortableInterceptor.INACTIVE;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teacher")
public class TeacherCourseController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;
    @Autowired
    CourseService courseService;
    @Autowired
    SectionService sectionService;
    @Autowired
    HomeworkService homeworkService;
    @Autowired
    FileService fileService;
    @Value("${file.upload.url}")
    private String fileUploadPath;

    @RequestMapping("/course")
    public String selectCourse(Model model,
                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize,
                               HttpSession session) {
        Teacher teacher = (Teacher) session.getAttribute("teacherUser");
        PageResult pageResult = courseService.selectCourseByTeacherId(pageNum, pageSize, teacher.getId());
        model.addAttribute("pageResult", pageResult);
        return "teacher/teacher-course";
    }

    /*
      添加课程
     */
    @PostMapping("/course/insert")
    @ResponseBody
    public String insertTeacherFollow(@RequestBody Map<String, Object> map, HttpSession session, HttpServletResponse response) {
        String courseName = (String) map.get("courseName");
        String courseIntro = (String) map.get("courseIntro");
        Teacher teacher = (Teacher) session.getAttribute("teacherUser");
        Map resMap = new HashMap();
        resMap.put("name", courseName);
        resMap.put("intro", courseIntro);
        resMap.put("teacherId", teacher.getId());
        resMap.put("collegeId",teacher.getCollegeId());
        courseService.insertCourse(resMap);
        return "添加成功";
    }

    /*
      批量删除课程
     */
    @PostMapping("/course/delete")
    public void deleteCourse(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        List<String> ids = (List<String>) map.get("ids");
        courseService.deleteCourseByIds(ids);
    }

    /**
     * 跳转课程详情页面
     */
    @GetMapping("/course/goEdit")
    public String editCoursePage(@RequestParam("courseId") int courseId, Model model) {
        Course course = courseService.selectCourseById(courseId);
        List<Section> sections = sectionService.selectSectionByCourseId(courseId);
        model.addAttribute("course", course);
        model.addAttribute("sections", sections);

        List<Homework> homeworks = homeworkService.selectHomeworkByCurseId(courseId);
        model.addAttribute("homeworks",homeworks);

        return "teacher/course-edit";

    }

    @GetMapping("/homeworkUpload")
    public void getHomeworkUpload(@RequestParam("homeworkUploadId") @NonNull String homeworkUploadId, HttpSession session,HttpServletResponse httpServletResponse) throws IOException {
        System.out.println("homeworkUploadId= "+homeworkUploadId);
        Teacher teacher=(Teacher)session.getAttribute("teacherUser");
        //查找文件地址
        Map map=new HashMap();
        map.put("homeworkUploadId",homeworkUploadId);
        map.put("teacherId",teacher.getId());
        Homework_upload homework_upload=homeworkService.getFilePathByTeacherIdAndHomeworkUploadId(map);
        if(homework_upload==null){
            httpServletResponse.setStatus(401);
            return;
        }
        httpServletResponse.setStatus(200);
        File file=null;
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try{
            file=new File(homework_upload.getPath());
            if(file!=null&&file.exists()) {
                httpServletResponse.setContentType("application/force-download");
                httpServletResponse.setHeader("Content-Disposition", "attachment; filename="
                        + java.net.URLEncoder.encode(file.getName(), "UTF-8"));
                byte[] buffer = new byte[1024];
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = httpServletResponse.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1&&i!=0) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            }

        }catch (Exception e){
                e.printStackTrace();
        }finally {
                if(bis!=null)
                    bis.close();
                if(fis!=null)
                    fis.close();
        }
    }

    /**
     * 修改课程
     */
    @PostMapping("/course/edit")
    public void editCourse(@RequestBody() Map<String, Object> map, HttpServletResponse response, HttpSession session) {
        Teacher teacher = (Teacher) session.getAttribute("teacherUser");
        Integer courseId = (Integer) map.get("courseId");
        String courseName = (String) map.get("courseName");
        String courseIntro = (String) map.get("courseIntro");
        Map resMap = new HashMap();
        resMap.put("id", courseId);
        resMap.put("name", courseName);
        resMap.put("intro", courseIntro);
        resMap.put("teacherId", teacher.getId());
        courseService.updateCourse(resMap);
    }


    /**
     * 课程添加章节
     */
    @PostMapping("/section/insertSection")
    public void insertSection(@RequestBody Map<String, Object> map, HttpServletResponse response){
        sectionService.insertSection(map);
    }

    /**
     * 作业布置
     */
    @PostMapping("/homework/insertHomework")
    public void editHomework(@RequestBody Map<String, Object> map, HttpSession session, HttpServletResponse response){
        String homeworkName = (String) map.get("homeworkName");
        String homeworkIntro = (String) map.get("homeworkIntro");
        Integer courseId = (Integer) map.get("courseId");

        System.out.println(homeworkName);
        System.out.println(homeworkIntro);
        System.out.println(courseId);
//        Teacher teacher = (Course) session.getAttribute("teacherUser");
//        Integer course= (Integer) map.get("courseId");
        Map resMap = new HashMap();
        resMap.put("name", homeworkName);
        resMap.put("intro", homeworkIntro);
        resMap.put("courseId", courseId);
//        courseService.insertCourse(resMap);
        homeworkService.insertHomework(resMap);
    }

    /**
     * 作业更新
     */
    @PutMapping("/homework")
    public void updateHomework(@RequestBody Map<String, Object> map, HttpSession session, HttpServletResponse response){
        String homeworkName = (String) map.get("homeworkName");//作业名称
        String homeworkIntro = (String) map.get("homeworkIntro");//作业详情
        Integer courseId = (Integer) map.get("courseId");//课程id
        String homeworkId=(String)map.get("homeworkId");//作业id
        Teacher teacher = (Teacher) session.getAttribute("teacherUser");
        Map resMap = new HashMap();
        resMap.put("name", homeworkName);
        resMap.put("intro", homeworkIntro);
        resMap.put("courseId", courseId);
        resMap.put("teacherId",teacher.getId());
        resMap.put("homeworkId",homeworkId);
        System.out.println(resMap);
        boolean res=homeworkService.updateHomeWork(resMap);
        if(res){
            response.setStatus(200);
        }else{
            response.setStatus(401);
        }
    }
    /**
     * 作业删除
     */
    @DeleteMapping("/homework")
    public void deleteHomework(@RequestBody Map<String, Object> map, HttpSession session, HttpServletResponse response){
        String homeworkId=(String)map.get("homeworkId");//作业id
        Teacher teacher = (Teacher) session.getAttribute("teacherUser");
        Map resMap = new HashMap();
        resMap.put("teacherId",teacher.getId());
        resMap.put("homeworkId",homeworkId);
        System.out.println(resMap);
        boolean res=homeworkService.deleteHomework(resMap);
        if(res){
            response.setStatus(200);
        }else{
            response.setStatus(401);
        }
    }

    /**
     * 修改章节名称
     */
    @PostMapping("/section/editSection")
    public void editSection(@RequestBody Map<String,Object> map,HttpServletResponse response){
        Integer sectionId=(Integer)map.get("sectionId");
        String sectionName=(String)map.get("sectionName");
        sectionService.editSection(sectionId,sectionName);
    }

    /**
     * 删除章节
     */
    @PostMapping("/section/deleteSection")
    public void deleteSection(@RequestBody Map<String,Object> map,HttpServletResponse response){
        Integer sectionId= (Integer) map.get("sectionId");
        sectionService.deleteSection(sectionId);
    }

    /**
     * 删除资源
     */
    @PostMapping("/file/deleteFile")
    public void deleteFile(@RequestBody Map<String,Object> map,HttpServletResponse response){
        List<Integer> list=new LinkedList<>();
        list.add((Integer)map.get("fileId"));
        fileService.deleteFileByIds(list);
    }

    /**
     * 上传资源
     */
    @RequestMapping("/file/uploadFile")
    @ResponseBody
    public String uploadFile(@RequestParam("courseId")Integer courseId,
                             @RequestParam(value = "fId",required = false) Integer fId,
                             @RequestParam("sectionId")Integer sectionId,
                             @RequestParam("fName")String name,
                             @RequestParam("fIntro")String intro,
                             @RequestParam("fFile")MultipartFile file,
                             HttpServletRequest request,
                             HttpServletResponse response){
        //获取文件名
        String fileName = file.getOriginalFilename();
        //新建File文件，参数为上传文件的目标文件夹
        File dir = new File(fileUploadPath + "/course"+courseId);
        //如果dir的路径不存在则创建新的文件加
        if(!dir.exists()){
            dir.mkdir();
        }
        //上传文件的路径
        String pathName=fileUploadPath + "/course"+courseId+"/"+fileName;
        File dest=new File(pathName);
        try {
            //进行文件传输
            file.transferTo(dest);
            if(fId==null){
                fileService.insertFile(sectionId,name,intro,pathName);
            }else{
                fileService.uploadFile(fId,sectionId,name,intro,pathName);
            }
            return "上传成功";
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "上传失败";
    }

}
