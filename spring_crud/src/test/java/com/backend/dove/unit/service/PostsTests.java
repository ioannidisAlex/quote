package com.backend.dove.unit.service;

import com.backend.dove.entity.Post;
import com.backend.dove.entity.User;
import com.backend.dove.repository.PostRepository;
import com.backend.dove.repository.UserRepository;
import com.backend.dove.service.PostService;
import com.backend.dove.service.UserService;
import com.backend.dove.util.PasswordGenerator;
import com.backend.dove.util.SetUtils;
import com.github.javafaker.Faker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PostsTests {

    @Autowired
    PasswordGenerator generator;

    @Autowired
    Faker faker;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostService service;

    @Test
    public void createCommentPost () {
        var poster = userRepository.save(
                new User()
                .randomise(generator, faker)
        );

        var commenter = userRepository.save(
                new User()
                .randomise(generator, faker)
        );

        try {
            var post = postRepository.save(
                    new Post()
                            .randomise(faker)
                            .setPoster(poster)
            );

            var comment = postRepository.save(
                    new Post()
                            .randomise(faker)
                            .setPoster(commenter)
                            .setParent(post)
            );

            System.out.println(post);
            System.out.println(comment);

            var res = service.get(post.getId(), Pageable.unpaged());

            System.out.println(res.size() + " comments");

            try {
                assertEquals(
                        Set.of(comment.getId()),
                        SetUtils.idsOf(post.getComments())
                );

                assertEquals(
                        1,
                        res.size()
                );
            } finally {
                postRepository.delete(post);
                postRepository.delete(comment);
            }
        } finally {
            userRepository.delete(commenter);
            userRepository.delete(poster);
        }
    }

}
