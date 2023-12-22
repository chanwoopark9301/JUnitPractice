import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProfileTest {
    private Profile profile;
    private BooleanQuestion question;
    private Criteria criteria;

    @Before
    public void create(){
        profile= new Profile("Bul,l Hokey, Inc.");
        question = new BooleanQuestion(1, "Got Bonuses?");
        criteria = new Criteria();
    }

    @Test
    public void matchAnswersFalseWhenMustMatchCriteriaNotMet(){
        // 준비
        Answer profileAnswer = new Answer(question, Bool.FALSE);
        profile.add(profileAnswer);
        Answer criteriaAnswer = new Answer(question,Bool.TRUE);
        Criterion criterion = new Criterion(criteriaAnswer,Weight.MustMatch);
        criteria.add(criterion);

        // 실행

        boolean matches = profile.matches(criteria);

        // 단언

        assertFalse(matches);
    }
    @Test
    public void matchAnswersTrueForAnyDontCareCriteria(){
        // 준비
        Answer profileAnswer = new Answer(question, Bool.FALSE);
        profile.add(profileAnswer);
        Answer criteriaAnswer = new Answer(question,Bool.TRUE);
        Criterion criterion = new Criterion(criteriaAnswer,Weight.DontCare);
        criteria.add(criterion);

        // 실행

        boolean matches = profile.matches(criteria);

        // 단언

        assertTrue(matches);
    }
}