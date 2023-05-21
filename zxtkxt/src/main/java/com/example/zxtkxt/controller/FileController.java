package com.example.zxtkxt.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.example.zxtkxt.core.api.ApiRest;
import com.example.zxtkxt.core.api.controller.BaseController;
import com.example.zxtkxt.mapper.QuestionMapper;
import com.example.zxtkxt.pojo.Question;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/file")
@CrossOrigin
public class FileController extends BaseController {
    @Autowired
    private QuestionMapper questionMapper;
//    @PostMapping("/upload1")
//    public ApiRest<String> upload1(@RequestParam("file") MultipartFile file) {
//        // 保存上传的图片到指定目录中
//        File dest = new File("src/assets/images" + file.getOriginalFilename());
//        System.out.println(dest.getAbsolutePath());
//        try {
//            file.transferTo(dest);
//            return success("上传成功","admin");
//        } catch (IOException e) {
//            e.printStackTrace();
//            return failure("上传失败");
//        }
//    }
    @PostMapping("/upload")
    public ApiRest<String> upload(@RequestParam("file") MultipartFile file) {
        String uuid = IdUtil.fastSimpleUUID();
        try {
            // 获取应用程序的工作目录
            String workingDir = System.getProperty("user.dir");
            // 拼接文件保存路径
            String filePath = workingDir + "/zxtkxt_vue/public/images/" + uuid + "_" + file.getOriginalFilename();
            // 使用 Files 工具类保存文件
            Files.write(Paths.get(filePath), file.getBytes());
            return success("上传成功","/images/" + uuid + "_" +file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            return failure("上传失败");
        }
    }

    /**
     * 下载接口
     * @param flag
     * @param response
     */
    @GetMapping("")
    public void getFiles( @RequestParam String flag, HttpServletResponse response) {
        OutputStream os;  // 新建一个输出流对象
        String basePath = System.getProperty("user.dir") + "/zxtkxt_vue/public/";  // 定于文件上传的根路径
        List<String> fileNames = FileUtil.listFileNames(basePath);  // 获取所有的文件名称
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");  // 找到跟参数一致的文件
        System.out.println(fileName);
        try {
            if (StrUtil.isNotEmpty(fileName)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath + fileName);  // 通过文件的路径读取文件字节流
                System.out.println(basePath+fileName);
                System.out.println(bytes);
                os = response.getOutputStream();   // 通过输出流返回文件
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }

    @PostMapping("/uploadExcel")
    public ApiRest<String> uploadExcel(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println(file.getName());
        // 将上传的Excel文件转换为输入流
        InputStream inputStream = file.getInputStream();

        // 创建Workbook对象，表示整个Excel文件
        Workbook workbook = WorkbookFactory.create(inputStream);

        // 获取第一个Sheet页
        Sheet sheet = workbook.getSheetAt(0);

        // 遍历Sheet页中的每一行数据
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
            //封装到Question中
            Question question = new Question();
            // 读取单元格数据

                String questionLevel = row.getCell(0)==null ? null : row.getCell(0).toString();
                String courseType = row.getCell(1)==null ? null :row.getCell(1).toString();
                String questionPoint =row.getCell(2)==null ? null : row.getCell(2).toString();
                String questionType = row.getCell(3)==null ? null :row.getCell(3).toString();
                String bankName = row.getCell(4)==null ? null : row.getCell(4).toString();
                String questionSubject = row.getCell(5)==null ? null : row.getCell(5).toString();
                String optionA = row.getCell(6)==null ? null : row.getCell(6).toString();
                String optionB = row.getCell(7)==null ? null : row.getCell(7).toString();
                String optionC = row.getCell(8)==null ? null : row.getCell(8).toString();
                String optionD = row.getCell(9)==null ? null : row.getCell(9).toString();
                String questionAnswer = row.getCell(10)==null ? null : row.getCell(10).toString();
                String questionImage = row.getCell(11)==null ? null : row.getCell(11).toString();
                String questionDesc = row.getCell(12)==null ? null : row.getCell(12).toString();
                question.setQuestionLevel(questionLevel);
                question.setQuestionDesc(questionDesc);
                question.setBankName(bankName);
                question.setQuestionType(questionType);
                question.setQuestionAnswer(questionAnswer);
                question.setCourseType(courseType);
                question.setQuestionImage(questionImage);
                question.setQuestionPoint(questionPoint);
                question.setQuestionSubject(questionSubject);
                question.setOptionA(optionA);
                question.setOptionB(optionB);
                question.setOptionC(optionC);
                question.setOptionD(optionD);
            try{
                // 将数据保存到数据库中
                questionMapper.insert(question);
            }catch (Exception e){
                return failure("上传失败");
            }
        }

        // 关闭Workbook对象和输入流
        workbook.close();
        inputStream.close();

        return success("上传成功","");
    }


}










