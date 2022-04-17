package org.ruegue.project.controller;

import org.ruegue.project.domain.CommentDto;
import org.ruegue.project.domain.CommentPageHandler;
import org.ruegue.project.domain.PageHandler;
import org.ruegue.project.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CommentController {

    @Autowired
    CommentService service;

    @ResponseBody
    @PatchMapping("/comments/{cno}")
    public ResponseEntity<String> modify(@PathVariable Integer cno, @RequestBody CommentDto dto, HttpSession session) {
        dto.setCno(cno);
        String commenter = (String) session.getAttribute("id");
        dto.setCommenter(commenter);

        try {
            if (service.modify(dto)!=1)
                throw new Exception("Modify failed");

            return new ResponseEntity("MOD_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("MOD_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @PostMapping("/comments")
    public ResponseEntity<String> write(@RequestBody CommentDto dto,Integer bno, HttpSession session) {
        String commenter = (String) session.getAttribute("id");
//        String commenter = "asdf";
        dto.setCommenter(commenter);
        dto.setBno(bno);

        try {
            if (service.write(dto)!=1)
                throw new Exception("Write failed");

            return new ResponseEntity("WRT_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("WRT_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/comments/{cno}")
    @ResponseBody
    public ResponseEntity<List<String>> remove(@PathVariable Integer cno, Integer bno, HttpSession session){
        String commenter = (String) session.getAttribute("id");
//            String commenter = "asdf";

        try {
            int rowCnt = service.remove(cno, bno, commenter);

            if (rowCnt!=1)
                throw new Exception("Delete Failed");
            return new ResponseEntity("DEL_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("DEL_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/comments")
    @ResponseBody public ResponseEntity list(Integer bno, Integer page) {

        List<CommentDto> list = null;

        try {
            CommentPageHandler commentPageHandler = new CommentPageHandler(bno , page);
            list = service.getPage(commentPageHandler);
            int totalCnt = service.getCount(commentPageHandler.getBno());

            CommentPageHandler cmt = new CommentPageHandler(totalCnt, list, commentPageHandler.getPage(), commentPageHandler.getBno());


            return new ResponseEntity(cmt, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }
}
