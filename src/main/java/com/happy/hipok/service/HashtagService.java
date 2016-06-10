package com.happy.hipok.service;

import com.happy.hipok.domain.Hashtag;
import com.happy.hipok.repository.HashtagRepository;
import com.happy.hipok.web.rest.dto.HashtagDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class HashtagService {

    private final Logger log = LoggerFactory.getLogger(HashtagService.class);

    @Inject
    private HashtagRepository hashtagRepository;

    public Set<Hashtag> extractAndSaveHashtagsFromText(String text) {

        Set<Hashtag> savedTags = new HashSet<>();

        if (text == null) {
            return savedTags;
        }

        Set<Hashtag> tags = extractHashtagsFromText(text);

        for (Hashtag tag : tags) {
            Optional<Hashtag> alreadySavedTag = hashtagRepository.findOneByLabel(tag.getLabel());
            if (alreadySavedTag.isPresent()) {
                savedTags.add(alreadySavedTag.get());
            } else {
                Hashtag savedTag = hashtagRepository.save(tag);
                savedTags.add(savedTag);
            }
        }
        return savedTags;
    }

    /**
     * Extract all unique hashtags from a text
     *
     * @param text
     * @return
     */
    private Set<Hashtag> extractHashtagsFromText(String text) {
        return Stream.of(text.split(" "))
            .filter(word -> word.startsWith("#"))
            .map(word -> new Hashtag(word))
            .collect(Collectors.toCollection(HashSet::new));
    }
}
