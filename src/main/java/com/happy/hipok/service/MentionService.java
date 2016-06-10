package com.happy.hipok.service;

import com.happy.hipok.domain.*;
import com.happy.hipok.repository.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service to detect mentions in text and modify this text with surrounding markers arount mentions.
 */
@Service
public class MentionService {

    public static final String BEGIN_MARK = "{{{{";
    public static final String END_MARK = "}}}}";

    private final Logger log = LoggerFactory.getLogger(MentionService.class);

    @Inject
    private ProfileRepository profileRepository;

    /**
     * Update text to surround mention with brackets
     *
     * @param publication publication to update
     * @return updated text
     */
    public List<Mention> getMentionsAndSetProcessedDescription(Publication publication) {

        List<Mention> mentions = new ArrayList<>();

        if (publication.getDescription() == null) {
            return mentions;
        }

        String updatedText = getProcessedTextAndFillMentions(mentions, publication.getDescription());
        publication.setProcessedDescription(updatedText);

        return mentions;
    }

    /**
     * Update text to surround mention with brackets
     *
     * @param comment comment to update
     * @return updated text
     */
    public List<Mention> getMentionsAndSetProcessesText(Comment comment) {

        List<Mention> mentions = new ArrayList<>();

        if (comment.getText() == null) {
            return null;
        }

        String updatedText = getProcessedTextAndFillMentions(mentions, comment.getText());
        comment.setProcessedText(updatedText);

        return mentions;
    }

    /**
     * Get processed text and fill mentions
     *
     * @param mentions
     * @param text
     * @return
     */
    private String getProcessedTextAndFillMentions(List<Mention> mentions, String text) {
        String[] words = text.split(" ");
        for (int i = 0; i < words.length; i++) {
            if (words[i].startsWith("@")) {
                Mention mention = detectMention(i, words);
                mentions.add(mention);

                if (mention != null && mention.getBeginEndPosition().isPresent()) {
                    words[i] = BEGIN_MARK + "\"title\" : \"" + words[i];
                    int endPosition = mention.getBeginEndPosition().get().getEndPosition();
                    words[endPosition] = words[endPosition] + "\", \"id\": " + mention.getMentionnedProfile().getId() + END_MARK;
                }
            }
        }
        return String.join(" ", words);
    }

    /**
     * Request TNP to detect begin and end positions in the text.
     *
     * @param beginPosition begin position
     * @param words         words
     * @return mention
     */
    private Mention detectMention(int beginPosition, String[] words) {
        Mention mention = null;

        String titleAbbreviation = words[beginPosition].substring(1, words[beginPosition].length());

        String firstParam = getParam(beginPosition + 1, words);
        String secondParam = getParam(beginPosition + 2, words);
        String thirdParam = getParam(beginPosition + 3, words);

        Optional<Profile> profile = profileRepository.findProfileByTNP(titleAbbreviation,
            firstParam.toUpperCase(), secondParam.toUpperCase(), thirdParam.toUpperCase());

        if (profile.isPresent()) {
            int userNamesLength = getUserNamesLength(profile);
            Optional<BeginEndPosition> beginEndPosition = Optional.of(new BeginEndPosition(beginPosition, beginPosition + userNamesLength));

            mention = new Mention();
            mention.setMentionnedProfile(profile.get());
            mention.setBeginEndPosition(beginEndPosition);
        }
        return mention;
    }

    private int getUserNamesLength(Optional<Profile> profile) {
        int userNamesLength = 0;

        User user = profile.get().getUser();
        if (user != null) {
            userNamesLength = countWords(user.getFirstName()) + countWords(user.getLastName());
        }

        return userNamesLength;
    }

    private int countWords(String text) {
        int countWords = 0;

        if (text != null) {
            String[] words = text.split(" ");
            countWords = words.length;
        }

        return countWords;
    }

    private String getParam(int index, String[] words) {
        if (index >= 0 && index < words.length) {
            return words[index];
        }
        return "";
    }


}
