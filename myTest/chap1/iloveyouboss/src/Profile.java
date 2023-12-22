import java.util.HashMap;
import java.util.Map;

public class Profile {
    private Map<String, Answer> answers = new HashMap<>();
    private int score;
    private String name;

    public Profile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void add(Answer answer) {
        answers.put(answer.getQuestionText(), answer);
    }

    public boolean matches(Criteria criteria) {
        score = 0;

        boolean kill = false;           // true 가 되면 탈락
        boolean anyMatches = false;     // ture 가 되면 합격
        for (Criterion criterion : criteria) {  // 여러 기준 리스트를 반복 조회
            Answer answer = answers.get(        // 현재 탐색 중인 기준에 대한 답변을 조회
                    criterion.getAnswer().getQuestionText());
            boolean match =   // 합격 여부
                    criterion.getWeight() == Weight.DontCare || // 기준에 대한 가중치를 조회 -> 가중치가 Dontcare일 경우에는 match는 무조건 true
                            answer.match(criterion.getAnswer());// 그렇지 않다면 답변에 따라 true 여부를 판별
            if (!match && criterion.getWeight() == Weight.MustMatch) { //match가 false이며 동시에 기준의 가중치가 musthmatch일 경우 kill은 true
                kill = true;
            }
            if (match) {
                score += criterion.getWeight().getValue(); // match가 true이면 기준의 가중치에 따른 점수를 score에 합산
            }
            anyMatches |= match; // 현재 match 중 어떤 하나라도 true라면 anyMatch를 true로 설정
        }
        if (kill)       // 만약 kill이 true일 경우 false를 리턴(불합격)
            return false;
        return anyMatches; // 그렇지 않다면 anyMatches의 결과를 리턴(합격 여부를 리턴)
    }

    public int score() {
        return score;
    }
}
