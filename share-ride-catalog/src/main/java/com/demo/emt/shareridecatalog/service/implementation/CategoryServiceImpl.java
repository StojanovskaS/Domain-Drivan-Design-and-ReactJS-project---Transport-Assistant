package com.demo.emt.shareridecatalog.service.implementation;

import com.demo.emt.shareridecatalog.domain.exceptions.*;
import com.demo.emt.shareridecatalog.domain.model.Category;
import com.demo.emt.shareridecatalog.domain.model.Post;
import com.demo.emt.shareridecatalog.domain.model.PostInterest;
import com.demo.emt.shareridecatalog.domain.model.SiteUser;
import com.demo.emt.shareridecatalog.domain.model.enumerations.CategoryName;
import com.demo.emt.shareridecatalog.domain.model.ids.PostId;
import com.demo.emt.shareridecatalog.domain.model.ids.PostInterestId;
import com.demo.emt.shareridecatalog.domain.repository.CategoryReposiotry;
import com.demo.emt.shareridecatalog.domain.repository.PostInterestRepository;
import com.demo.emt.shareridecatalog.domain.repository.PostRepository;
import com.demo.emt.shareridecatalog.domain.valueobjects.City;
import com.demo.emt.shareridecatalog.service.CategoryService;
import com.demo.emt.shareridecatalog.service.SiteUserService;
import com.demo.emt.shareridecatalog.service.forms.CategoryForm;
import com.demo.emt.shareridecatalog.service.forms.PostForm;
import org.springframework.stereotype.Service;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryReposiotry categoryReposiotry;
    private final Validator validator;
    private final PostRepository postRepository;
    private final SiteUserService siteUserService;
    private final PostInterestRepository postInterestRepository;


    public CategoryServiceImpl(CategoryReposiotry categoryReposiotry, Validator validator, PostRepository postRepository, SiteUserService service, PostInterestRepository postInterestRepository) {
        this.categoryReposiotry = categoryReposiotry;
        this.validator = validator;
        this.postRepository = postRepository;
        this.siteUserService = service;
        this.postInterestRepository = postInterestRepository;
    }

    @Override
    public Category findByCategoryNameAndGrad(CategoryForm categoryForm) {
        return categoryReposiotry.findByCategoryNameLikeAndGradLike(categoryForm.getCategoryName(),categoryForm.getGrad()).orElseGet(() -> {
            Category cat= this.createCategory(categoryForm);
            return cat;
            //ako nema da se kreira nova kategorija ako ja nema kreirano prethodno
        });
    }

    @Override
    public Category findByGrad(City grad) {
        return categoryReposiotry.findByGradLike(grad).orElseThrow(InvalidCityException::new);

    }

    @Override
    public Category findByCategoryName(CategoryName categoryName) {
        return this.categoryReposiotry.findByCategoryNameLike(categoryName).orElseThrow(InvalidCategoryNameException::new);
    }
    @Override
    public Category createCategory(CategoryForm categoryForm) {
        Objects.requireNonNull(categoryForm,"category must not be null.");
        var constraintViolations = validator.validate(categoryForm);
        if (constraintViolations.size()>0) {
            throw new ConstraintViolationException("The category form is not valid", constraintViolations);
        }
        Category category = Category.CreateNewCategory(categoryForm.getCategoryName(),categoryForm.getGrad());
        return this.categoryReposiotry.save(category);
    }

    // del za posts

    @Override
    public List<Post> listAllPosts() {
        return this.postRepository.findAll();
    }

    @Override
    public List<Category> listAllCategories() {
        return this.categoryReposiotry.findAll();
    }

    @Override
    public List<Post> findAllPostsByCategoryName(CategoryName categoryName) {
        Category category = this.findByCategoryName(categoryName);
// ne sum sigurna ovde ama aj
        return category.getPostsList().stream().collect(Collectors.toList());
    }

    @Override
    public List<Post> findAllPostsByCategoryNameAndGrad(CategoryForm categoryForm) {
        Category category = this.findByCategoryNameAndGrad(categoryForm);
// ne sum sigurna ovde ama aj
        return category.getPostsList().stream().collect(Collectors.toList());
    }

    @Override
    public List<Post> findAllPostsByGrad(City grad) {
        Category category = this.findByGrad(grad);
// ne sum sigurna ovde ama aj
        return category.getPostsList().stream().collect(Collectors.toList());
    }

    @Override
    public Post addNewPost(PostForm newPostForm) {
        Category category= categoryReposiotry.findById(newPostForm.getCategory()).orElseThrow(CategoryNotFound::new);
        SiteUser user=siteUserService.findByUserName(newPostForm.getUser());
        Post post = category.addPost(user,newPostForm.getOdGrad(),newPostForm.getOpis(),newPostForm.getCena(),newPostForm.getDen(),category,new PostInterest());
        siteUserService.addPostToMyPostsPart(post);
        categoryReposiotry.saveAndFlush(category);
        return post;

    }

    @Override
    public Post updatePost(PostId postId, PostForm postForm) {
        Category category= categoryReposiotry.findById(postForm.getCategory()).orElseThrow(CategoryNotFound::new);
        category.removePost(postId);
        // da go trgnam od post vo kategori isto??
        SiteUser user=siteUserService.findByUserName(postForm.getUser());
        PostInterest postInterest=  user.getPosts().stream().filter(i->i.getId().equals(postId)).findFirst().orElseThrow(PostNotFound::new).getLikepart();
        Post post = category.addPost(user,postForm.getOdGrad(),postForm.getOpis(),postForm.getCena(),postForm.getDen(),category,postInterest);
        categoryReposiotry.saveAndFlush(category);
        return post;
    }

    @Override
    public void deletePostById(PostId id,CategoryName categoryName) {
        Category category= this.findByCategoryName(categoryName);
        // ne sum sigurna deka se isti id vo posts i ovde
        Post post = this.findPostById(id);
        siteUserService.deletePostFromMyPostsPart(id,post.getUser().getUsername());
        category.removePost(id);
        categoryReposiotry.saveAndFlush(category);

    }

    @Override
    public List<Post> findAllPostsByUser(String username) {
        SiteUser user = siteUserService.findByUserName(username);
        return postRepository.findAllByUser(user);
    }

    @Override
    public Post findPostByUserAndOpis(SiteUser user, String opis) {
        return this.postRepository.findByUserAndOpisLike(user,opis);
    }

    @Override
    public Post findPostById(PostId id) {
        return this.postRepository.findById(id).orElseThrow(PostNotFound::new);
    }

    @Override
    public List<Post> findAllPostsByOdGradAndDoGrad(City odgrad, City dograd, String kategorijaSearch) {
//        String odgrad1="%"+odgrad+"%";
//        String dograd1="%"+dograd+"%";
//        String kateg1="%"+kategorijaSearch+"%";
        Category category=this.findByCategoryNameAndGrad(new CategoryForm(kategorijaSearch,dograd));
        List<Post>posts=new ArrayList<>();

        List<Post>del=postRepository.findAllByOdGradLikeAndCategoryLike(odgrad,category);
        for(int j=0;j<del.size();j++){
            posts.add(del.get(j));
        }
        return posts;
    }

    @Override
    public void addLike(PostInterestId postInterestId, SiteUser user) {
        PostInterest likePartofPost=this.postInterestRepository.findById(postInterestId).orElseThrow(InvalidPostInterstIDfromPostException::new);
        SiteUser siteUser=null;
        siteUser=likePartofPost.getUsersLikes().stream().filter(i->i.getUsername().equals(user.getUsername())).findFirst().orElse(null);
        if ( siteUser != null){
            //vo listataznaci go ima
            return;
        }
        likePartofPost.getUsersLikes().add(user);
        Integer br=likePartofPost.getLikesNo();
        br+=1;
        likePartofPost.setLikesNo(br);
        postInterestRepository.save(likePartofPost);

    }

    @Override
    public void deleteLike(PostInterestId interestedpartid, SiteUser user) {
        PostInterest likePartofPost=this.postInterestRepository.findById(interestedpartid).orElseThrow(InvalidPostInterstIDfromPostException::new);
        SiteUser siteUser=null;
        siteUser=likePartofPost.getUsersLikes().stream().filter(i->i.getUsername().equals(user.getUsername())).findFirst().orElse(null);
        if ( siteUser == null){
            //vo listataznaci go nema nemoze da se izbrisa ni da se namali brojacot
            return;
        }
        likePartofPost.getUsersLikes().removeIf(i->i.getUsername().equals(user.getUsername()));
        Integer br=likePartofPost.getLikesNo();
        br-=1;
        likePartofPost.setLikesNo(br);
        postInterestRepository.save(likePartofPost);
    }

    @Override
    public List<String> likeKorisnici(PostInterestId interestedpartid) {
        PostInterest likePartofPost=this.postInterestRepository.findById(interestedpartid).orElseThrow(InvalidPostInterstIDfromPostException::new);
        List<SiteUser> korisnici=likePartofPost.getUsersLikes();
        List<String> imanja=new ArrayList<>();
        for (SiteUser user : korisnici){
            imanja.add(user.getUsername());
            //lista od username da mi vrakja

        }
        return imanja;
    }


}
