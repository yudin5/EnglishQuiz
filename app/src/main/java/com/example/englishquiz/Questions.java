package com.example.englishquiz;

import static com.example.englishquiz.R.string.*;

public class Questions {

    private static final Question[] questionBank = new Question[]{
            new Question(q_1, q_1_op_1, q_1_op_2, q_1_explanation, q_1_answer),
            new Question(q_2, q_2_op_1, q_2_op_2, q_2_explanation, q_2_answer),
            new Question(q_3, q_3_op_1, q_3_op_2, q_3_explanation, q_3_answer),
            new Question(q_4, q_4_op_1, q_4_op_2, q_4_explanation, q_4_answer),
            new Question(q_5, q_5_op_1, q_5_op_2, q_5_explanation, q_5_answer),
            new Question(q_6, q_6_op_1, q_6_op_2, q_6_explanation, q_6_answer),
            new Question(q_7, q_7_op_1, q_7_op_2, q_7_explanation, q_7_answer),
            new Question(q_8, q_8_op_1, q_8_op_2, q_8_explanation, q_8_answer),
            new Question(q_9, q_9_op_1, q_9_op_2, q_9_explanation, q_9_answer),
            new Question(q_10, q_10_op_1, q_10_op_2, q_10_explanation, q_10_answer),
            new Question(q_11, q_11_op_1, q_11_op_2, q_11_explanation, q_11_answer),
            new Question(q_12, q_12_op_1, q_12_op_2, q_12_explanation, q_12_answer),
            new Question(q_13, q_13_op_1, q_13_op_2, q_13_explanation, q_13_answer),
            new Question(q_14, q_14_op_1, q_14_op_2, q_14_explanation, q_14_answer),
            new Question(q_15, q_15_op_1, q_15_op_2, q_15_explanation, q_15_answer)
    };

    public static Question[] getQuestionBank() {
        return questionBank;
    }

}
