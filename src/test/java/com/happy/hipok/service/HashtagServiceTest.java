package com.happy.hipok.service;

import com.happy.hipok.Application;
import com.happy.hipok.domain.Hashtag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for the UserResource REST controller.
 *
 * @see UserService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class HashtagServiceTest {

    @Autowired
    private HashtagService hashtagService;

    @Test
    public void shouldExtractNoTagsFromText() {
        String text = "Hipok c'est cool";

        Set<Hashtag> hashtagsFromText = hashtagService.extractAndSaveHashtagsFromText(text);

        assertThat(hashtagsFromText).isNotNull();
        assertThat(hashtagsFromText).isEmpty();
    }

    @Test
    public void shouldExtractOneTagFromText() {
        String text = "Hipok c'est cool #trolol";

        Set<Hashtag> hashtagsFromText = hashtagService.extractAndSaveHashtagsFromText(text);

        assertThat(hashtagsFromText).isNotNull();
        assertThat(hashtagsFromText.size()).isEqualTo(1);
        assertThat(hashtagsFromText.iterator().next().getLabel()).isEqualTo("#trolol");
    }

    @Test
    public void shouldExtractTwoTagsFromText() {
        String text = "Hipok c'est #cool #trolol";

        Set<Hashtag> hashtagsFromText = hashtagService.extractAndSaveHashtagsFromText(text);

        assertThat(hashtagsFromText).isNotNull();
        assertThat(hashtagsFromText.size()).isEqualTo(2);
        assertThat(hashtagsFromText).containsExactly(new Hashtag("#cool"), new Hashtag("#trolol"));
    }

    @Test
    public void shouldExtractTwoTagsFromTextWithSameTags() {
        String text = "Hipok c'est #cool #cool #trolol";

        Set<Hashtag> hashtagsFromText = hashtagService.extractAndSaveHashtagsFromText(text);

        assertThat(hashtagsFromText).isNotNull();
        assertThat(hashtagsFromText.size()).isEqualTo(2);
        assertThat(hashtagsFromText).containsExactly(new Hashtag("#cool"), new Hashtag("#trolol"));
    }
}
