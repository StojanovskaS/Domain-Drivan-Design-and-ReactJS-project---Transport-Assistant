package com.demo.emt.shareridecatalog.xport.rest;

import com.demo.emt.shareridecatalog.domain.model.Post;
import com.demo.emt.shareridecatalog.service.CategoryService;
import com.demo.emt.shareridecatalog.service.SiteUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping({"/myposts"})
public class MyPostsPartController {
    //prikaz na moite postovi
    private final CategoryService categoryService;
    private final SiteUserService siteUserService;

    public MyPostsPartController(CategoryService categoryService, SiteUserService siteUserService) {
        this.categoryService = categoryService;
        this.siteUserService = siteUserService;
    }
    @GetMapping("/{username}")
    private List<Post> listPostsinMyPostPart(@PathVariable String username) {
        return this.siteUserService.listAllPostsInMyPostsPart(username);
    }


// rest controler za ova mislam nema da mi treba za sega bidejki ovaa logika ja imam koa brisam post od posts page
//    @DeleteMapping("/delete/{id}")
//    public String deleteMyPostFromCard(HttpServletRequest req,@PathVariable Long id) {
//        String username=req.getRemoteUser();
//        SiteUser user=this.siteUserService.findByUserName(username);
//        Post p=this.myPostsService.findPostById(id,username);
//        this.myPostsService.deletePostFromCard(id,username);
//        this.postService.deleteById(p.getId());
//        return "redirect:/myposts";
//    }
}
