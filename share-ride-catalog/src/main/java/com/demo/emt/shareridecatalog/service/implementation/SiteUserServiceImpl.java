package com.demo.emt.shareridecatalog.service.implementation;

import com.demo.emt.sharedkernel.domain.financial.Money;
import com.demo.emt.shareridecatalog.domain.exceptions.*;
import com.demo.emt.shareridecatalog.domain.model.MyPostsPart;
import com.demo.emt.shareridecatalog.domain.model.Post;
import com.demo.emt.shareridecatalog.domain.model.SiteUser;
import com.demo.emt.shareridecatalog.domain.model.enumerations.MyPostsCardStatus;
import com.demo.emt.shareridecatalog.domain.model.ids.PostId;
import com.demo.emt.shareridecatalog.domain.model.ids.SiteUserId;
import com.demo.emt.shareridecatalog.domain.repository.MyPostPartRepository;
import com.demo.emt.shareridecatalog.domain.repository.PostRepository;
import com.demo.emt.shareridecatalog.domain.repository.SiteUserRepository;
import com.demo.emt.shareridecatalog.service.SiteUserService;
import com.demo.emt.shareridecatalog.service.forms.NewPostForm;
import com.demo.emt.shareridecatalog.service.forms.SiteUserForm;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.module.InvalidModuleDescriptorException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SiteUserServiceImpl implements SiteUserService, UserDetailsService {
    private final SiteUserRepository siteUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostRepository postRepository;
    private final MyPostPartRepository myPostPartRepository;


    public SiteUserServiceImpl(SiteUserRepository siteUserRepository, PasswordEncoder passwordEncoder, PostRepository postRepository, MyPostPartRepository myPostPartRepository) {
        this.siteUserRepository = siteUserRepository;
        this.passwordEncoder=passwordEncoder;
        this.postRepository = postRepository;

        this.myPostPartRepository = myPostPartRepository;
    }

    @Override
    public SiteUser login(String username, String password) {
        return this.siteUserRepository.findByUsernameAndPassword(username,passwordEncoder.encode(password)).orElseThrow(InvalidUserCredentialsException::new);
    }

    @Override
    public SiteUser register(SiteUserForm form) {
        if (form.getUsername()==null || form.getUsername().isEmpty()  || form.getPassword()==null || form.getPassword().isEmpty())
            throw new InvalidUserCredentialsException();
        if (form.getFirstname()==null || form.getFirstname().isEmpty() || form.getLastname()==null || form.getLastname().isEmpty()){
            throw new InvalidUserCredentialsException();
        }
        if (!form.getPassword().equals(form.getRepeatpassword()))
            throw new PasswordsdoNotMatchException();
        if(this.siteUserRepository.findByUsername(form.getUsername()).isPresent())
            throw new UsernameAlreadyExistsException(form.getUsername());

        String newpass=passwordEncoder.encode(form.getPassword());
        // ovoj e delot vo koj se kreira nov user vo model SiteUser
        MyPostsPart myPostsPart = MyPostsPart.CreateNewMyPostPart();
        myPostPartRepository.save(myPostsPart);

        SiteUser user= SiteUser.CreateNewUser(form.getUsername(),form.getEmail(), form.getFirstname(), form.getLastname(), newpass,form.getRole(),myPostsPart,form.getBudget());
        return siteUserRepository.save(user);
    }

    @Override
    public SiteUser findByUserName(String username) {
        return this.siteUserRepository.findByUsername(username).orElseThrow(UserNotFoundExeption::new);
    }

    @Override
    public List<SiteUser> findAll() {
        return this.siteUserRepository.findAll();
    }

    @Override
    public List<Post> listAllPostsInMyPostsPart(String username) {
        SiteUser user = this.siteUserRepository.findByUsername(username).orElseThrow(UserNotFoundExeption::new);
        MyPostsPart mycard= user.getMyPostsPart();
        if(!mycard.getStatus().equals(MyPostsCardStatus.CREATED) || !mycard.getStatus().equals(MyPostsCardStatus.ACTIVE)){
            throw new MyPostCardNotFound(username);
        }
        return mycard.getMyposts();
//        return this.getActiveUserPostsCard(username).get().getMyPosts();
    }

    @Override
    @Transactional
    public MyPostsPart getActiveUserPostsPart(String username) {
        SiteUser user=this.siteUserRepository.findByUsername(username).orElseThrow(UserNotFoundExeption::new);
        return user.getMyPostsPart();
    }


    @Override
    public void addPostToMyPostsPart(Post postForm) {
        //tuka  pazi
        SiteUser user = this.findByUserName(postForm.getUser().getUsername());

        user.addPostToMyPostPart(postForm);
        siteUserRepository.saveAndFlush(user);
//        ova vajda treba da go do implementiram
//        domainEventPublisher.publish(new OrderItemCreated(orderItemForm.getProduct().getId().getId(),orderItemForm.getQuantity()));

    }

    @Override
    public void updatePostInMyPostPart(NewPostForm postForm, String username) {
        SiteUser user = this.findByUserName(username);
        user.removePostFromMyPostsPart(postForm.getPost().getId());
        // da go trgnam od post vo kategori isto??
        user.addPostToMyPostPart(postForm.getPost());
        siteUserRepository.saveAndFlush(user);


//        MyPostsPart karticka=user.getMyPostsPart();
        //        karticka.getMyposts().removeIf(i->i.getOpis().equals(postForm.getPost().getOpis()) && i.getUser().getUsername().equals(postForm.getPost().getUser().getUsername()));
//        karticka.getMyposts().add(post);
//        this.myPostsPartRepository.save(karticka);

    }

    @Override
    public void deletePostFromMyPostsPart(PostId id, String username) {

        SiteUser user = this.findByUserName(username);
        // ne sum sigurna deka se isti id vo posts i ovde
        user.removePostFromMyPostsPart(id);
        siteUserRepository.saveAndFlush(user);

    }

    @Override
    public Post findPostByIdinMyPostsPart(PostId id, String username) {
        SiteUser user = this.findByUserName(username);
        return user.getMyPostsPart().getMyposts().stream().filter(i -> i.getId().equals(id)).findFirst().orElseThrow(PostNotFound::new);
    }

    @Override
    public SiteUser orderItemCreated(String siteUserId, Money price) {
        SiteUser p = siteUserRepository.findByUsername(siteUserId).orElseThrow(UserNotFoundExeption::new);
        p.decreaseBudget(price);
        siteUserRepository.saveAndFlush(p);
        return p;
    }

    @Override
    public SiteUser orderItemRemoved(String siteUserId, Money price) {
        SiteUser p = siteUserRepository.findByUsername(siteUserId).orElseThrow(UserNotFoundExeption::new);
        p.increaseBudget(price);
        siteUserRepository.saveAndFlush(p);
        return p;
    }

    @Override
    public PostId findPostIdinMyPostsPart(NewPostForm p) {
        SiteUser user =this.findByUserName(p.getPost().getUser().getUsername());
        return user.getMyPostsPart().getMyposts().stream().filter(i->i.getOpis().equals(p.getPost().getOpis()) && i.getUser().getUsername().equals(p.getPost().getUser().getUsername())).findFirst().get().getId();    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SiteUser user=siteUserRepository.findByUsername(s).orElseThrow(InvalidModuleDescriptorException::new);
        UserDetails user1 =new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                Stream.of(new SimpleGrantedAuthority(user.getRole().toString())).collect(Collectors.toList()));
        return user1;
    }
}
