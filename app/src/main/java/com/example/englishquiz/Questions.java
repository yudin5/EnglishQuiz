package com.example.englishquiz;

import static com.example.englishquiz.R.string.q_1;
import static com.example.englishquiz.R.string.q_1_explanation;
import static com.example.englishquiz.R.string.q_1_op_1;
import static com.example.englishquiz.R.string.q_1_op_2;
import static com.example.englishquiz.R.string.q_2;
import static com.example.englishquiz.R.string.q_2_explanation;
import static com.example.englishquiz.R.string.q_2_op_1;
import static com.example.englishquiz.R.string.q_2_op_2;
import static com.example.englishquiz.R.string.q_3;
import static com.example.englishquiz.R.string.q_3_explanation;
import static com.example.englishquiz.R.string.q_3_op_1;
import static com.example.englishquiz.R.string.q_3_op_2;

public class Questions {

    public static Question[] getQuestionBank() {
        return questionBank;
    }

    private static final Question[] questionBank = new Question[]{
            new Question(q_1, q_1_op_1, q_1_op_2, q_1_explanation, q_1_op_1),
            new Question(q_2, q_2_op_1, q_2_op_2, q_2_explanation, q_2_op_1),
            new Question(q_3, q_3_op_1, q_3_op_2, q_3_explanation, q_3_op_2)
    };

}
