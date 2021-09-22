package com.demo.emt.shareridecatalog.xport.rest;

import com.demo.emt.shareridecatalog.domain.model.Category;
import com.demo.emt.shareridecatalog.domain.model.Post;
import com.demo.emt.shareridecatalog.domain.model.SiteUser;
import com.demo.emt.shareridecatalog.domain.model.enumerations.CategoryName;
import com.demo.emt.shareridecatalog.domain.model.ids.PostId;
import com.demo.emt.shareridecatalog.domain.model.ids.PostInterestId;
import com.demo.emt.shareridecatalog.service.CategoryService;
import com.demo.emt.shareridecatalog.service.SiteUserService;
import com.demo.emt.shareridecatalog.service.forms.CategoryForm;
import com.demo.emt.shareridecatalog.service.forms.PostForm;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class PostsAndCategoriesController {
    //rest controlller za komuniciranje so react app vo koja imam dodavanje na postovi brisenje editiranje i stavanje lajkovi
    private final CategoryService categoryService;
    private final SiteUserService siteUserService;

    public PostsAndCategoriesController(CategoryService categoryService, SiteUserService siteUserService) {
        this.categoryService = categoryService;
        this.siteUserService = siteUserService;
    }

    @GetMapping
    private List<Post> findAllPosts() {
        return this.categoryService.listAllPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> findById(@PathVariable String id) {
        Post post = this.categoryService.findPostById(PostId.of(id));
        if (post != null) {
            return ResponseEntity.ok().body(post);
        } else {
            return ResponseEntity.notFound().build();
        }
        // map ne mi raboti
//        return this.categoryService.findPostById(id)
//                .map(post -> ResponseEntity.ok().body(post))
//                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/categories")
    public List<Category> findAll() {
        return this.categoryService.listAllCategories();
    }

    @PostMapping("/categoryadd")
    public ResponseEntity<Category> savecategory(@RequestBody CategoryForm categoryForm) {
        //map ne mi raboti zatoa vaka
        Category category = categoryService.createCategory(categoryForm);
        if (category != null) {
            return ResponseEntity.ok().body(category);
        } else {
            return ResponseEntity.badRequest().build();
        }
//        return this.categoryService.addNewPost(postForm)
//                .map(post -> ResponseEntity.ok().body(post))
//                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Post> save(@RequestBody PostForm postForm) {
        //map ne mi raboti zatoa vaka
        Post post = this.categoryService.addNewPost(postForm);
        if (post != null) {
            return ResponseEntity.ok().body(post);
        } else {
            return ResponseEntity.badRequest().build();
        }
//        return this.categoryService.addNewPost(postForm)
//                .map(post -> ResponseEntity.ok().body(post))
//                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Post> save(@PathVariable String id, @RequestBody PostForm postForm) {
        Post post = this.categoryService.updatePost(PostId.of(id), postForm);
        if (post != null) {
            return ResponseEntity.ok().body(post);
        } else {
            return ResponseEntity.badRequest().build();
        }
        //map ne mi raboti
//        return  this.categoryService.updatePost(id,postForm)
//                .map(post -> ResponseEntity.ok().body(post))
//                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}/{username}/like")
    public ResponseEntity addLike(@PathVariable String id, @PathVariable String username) {
        Post postot = this.categoryService.findPostById(PostId.of(id));
        SiteUser user = siteUserService.findByUserName(username);
        this.categoryService.addLike(postot.getLikepart().getId(), user);
        List<String> korisnicki_iminja = this.categoryService.likeKorisnici(postot.getLikepart().getId());
        for (String ime : korisnicki_iminja) {
            if (ime.equals(username)) {
                return ResponseEntity.ok().build();
            }

        }
        return ResponseEntity.badRequest().build();

    }

    @GetMapping("/{id}/{username}/deletelike")
    public ResponseEntity deleteLike(@PathVariable String id, @PathVariable String username) {
        Post postot = this.categoryService.findPostById(PostId.of(id));
        SiteUser user = siteUserService.findByUserName(username);
        this.categoryService.deleteLike(postot.getLikepart().getId(), user);
        List<String> korisnicki_iminja = this.categoryService.likeKorisnici(postot.getLikepart().getId());
        for (String ime : korisnicki_iminja) {
            if (ime.equals(username)) {
                return ResponseEntity.badRequest().build();
            }

        }
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletePostById(@PathVariable String id, @PathVariable String categoryName) {
        this.categoryService.deletePostById(PostId.of(id), CategoryName.valueOf(categoryName));
        if (this.categoryService.findPostById(PostId.of(id)) == null) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }


}
